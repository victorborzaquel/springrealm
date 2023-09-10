package com.victorborzaquel.springrealm.modules.battles.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacter;
import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.AttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;
import com.victorborzaquel.springrealm.modules.turns.Turn;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;
import com.victorborzaquel.springrealm.utils.dices.DiceUtil;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttackBattleUseCase {

  private final BattleRepository battleRepository;
  private final PlayerRepository playerRepository;
  private final TurnRepository turnRepository;

  public ResponseBattleDto execute(AttackBattleDto dto) {
    Player player = playerRepository.findByUsernameIgnoreCase(dto.getPlayerUsername())
        .orElseThrow(PlayerNotFoundException::new);

    Battle battle = battleRepository.findByPlayerAndInProgressTrue(player)
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    startTurn(battle);

    battleRepository.save(battle);

    List<Turn> turns = turnRepository.findAllByBattle(battle);

    return BattleMapper.INSTANCE.toDto(battle, turns);
  }

  private void startTurn(Battle battle) {
    BattleCharacter player = battle.getPlayerBattleCharacter();
    BattleCharacter enemy = battle.getEnemyBattleCharacter();

    if (battle.getIsPlayerInitiative()) {
      createTurns(battle, player, enemy);
    } else {
      createTurns(battle, enemy, player);
    }

  }

  private void createTurns(Battle battle, BattleCharacter characterAttack, BattleCharacter characterDefense) {
    turn(battle, characterAttack, characterDefense);

    if (battle.getInProgress()) {
      turn(battle, characterDefense, characterAttack);
    }
  }

  private void turn(Battle battle, BattleCharacter characterAttack, BattleCharacter characterDefense) {
    RollDicesDto attackPowerDices = DiceUtil.rollTurnDice();
    Integer attack = calculeAttack(characterAttack, attackPowerDices);

    RollDicesDto defensePowerDices = DiceUtil.rollTurnDice();
    Integer defense = calculeDefense(characterDefense, defensePowerDices);

    RollDicesDto damageDices = DiceUtil.rollDamageDice(characterAttack);

    Integer damage = (attack > defense) ? damageDices.getResult() : 0;

    characterDefense.damage(damage);

    if (characterDefense.isDead()) {
      battle.endBattle();
    }

    createTurn(battle, attackPowerDices, defensePowerDices, damage);
  }

  private void createTurn(
      Battle battle,
      RollDicesDto attackPowerDices,
      RollDicesDto defensePowerDices,
      Integer damage) {

    Integer turnNumber = turnRepository.countByBattle(battle) + 1;
    Boolean isPlayerTurn = battle.getIsPlayerInitiative() ? turnNumber % 2 == 1 : turnNumber % 2 == 0;
    BattleCharacter character = isPlayerTurn ? battle.getPlayerBattleCharacter() : battle.getEnemyBattleCharacter();
    BattleCharacter enemy = isPlayerTurn ? battle.getEnemyBattleCharacter() : battle.getPlayerBattleCharacter();

    Turn turn = Turn.builder()
        .battle(battle)
        .number(turnNumber)
        .isPlayerTurn(isPlayerTurn)
        .playerPV(character.getLife())
        .enemyPV(enemy.getLife())
        .attackDice(attackPowerDices.getResult())
        .defenseDice(defensePowerDices.getResult())
        .damage(damage)
        .build();

    turnRepository.save(turn);
  }

  private Integer calculeDefense(BattleCharacter character, RollDicesDto rollDices) {
    Integer defense = character.getDefense() + character.getAgility() + rollDices.getResult();

    return defense;
  }

  private Integer calculeAttack(BattleCharacter character, RollDicesDto rollDices) {
    Integer attack = character.getStrength() + character.getAgility() + rollDices.getResult();

    return attack;
  }

}
