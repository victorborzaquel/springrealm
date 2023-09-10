package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacter;
import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.AttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackTurnDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseTurnDice;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseTurnDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;
import com.victorborzaquel.springrealm.modules.turns.Turn;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;
import com.victorborzaquel.springrealm.utils.DiceUtil;
import com.victorborzaquel.springrealm.utils.dto.RollDiceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttackBattleUseCase {

  private final BattleRepository battleRepository;
  private final PlayerRepository playerRepository;
  private final TurnRepository turnRepository;

  public ResponseAttackBattleDto execute(AttackBattleDto dto) {
    Player player = playerRepository.findByUsernameIgnoreCase(dto.getPlayerUsername())
        .orElseThrow(PlayerNotFoundException::new);

    Battle battle = battleRepository.findByPlayerAndInProgressTrue(player)
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    ResponseAttackTurnDto responseAttackTurnDto = executeBattle(battle);

    return BattleMapper.INSTANCE.toDto(battle, responseAttackTurnDto);
  }

  private ResponseAttackTurnDto executeBattle(Battle battle) {
    BattleCharacter player = battle.getPlayerBattleCharacter();
    BattleCharacter enemy = battle.getEnemyBattleCharacter();
    Boolean isPlayerInitiative = battle.getIsPlayerInitiative();

    BattleCharacter characterAttack = isPlayerInitiative ? player : enemy;
    BattleCharacter characterDefense = isPlayerInitiative ? enemy : player;

    ResponseTurnDto initiativeTurn = turn(battle, characterAttack, characterDefense);
    ResponseTurnDto secondaryTurn = null;

    if (battle.getInProgress()) {
      secondaryTurn = turn(battle, characterDefense, characterAttack);
    }

    battleRepository.save(battle);

    ResponseTurnDto playerTurn = isPlayerInitiative ? initiativeTurn : secondaryTurn;
    ResponseTurnDto enemyTurn = isPlayerInitiative ? secondaryTurn : initiativeTurn;

    return ResponseAttackTurnDto.builder()
        .player(playerTurn)
        .enemy(enemyTurn)
        .build();
  }

  private ResponseTurnDto turn(Battle battle, BattleCharacter characterAttack, BattleCharacter characterDefense) {
    RollDiceDto attackPowerDices = DiceUtil.rollTurnDice();
    Integer attack = calculeAttack(characterAttack, attackPowerDices);

    RollDiceDto defensePowerDices = DiceUtil.rollTurnDice();
    Integer defense = calculeDefense(characterDefense, defensePowerDices);

    Integer damage = 0;
    RollDiceDto damageDices = null;

    if (attack > defense) {
      damageDices = DiceUtil.rollDamageDice(characterAttack);

      damage = damageDices.getResult();

      characterDefense.damage(damage);
    }

    if (characterDefense.isDead()) {
      battle.endBattle();
    }

    saveTurn(battle, attack, defense, damage);

    ResponseTurnDice returnAttack = ResponseTurnDice.builder()
        .total(attack)
        .rollDice(attackPowerDices)
        .build();

    ResponseTurnDice returnDefense = ResponseTurnDice.builder()
        .total(defense)
        .rollDice(defensePowerDices)
        .build();

    ResponseTurnDice returnDamage = ResponseTurnDice.builder()
        .total(damage)
        .rollDice(damageDices)
        .build();

    return ResponseTurnDto.builder()
        .attackPower(returnAttack)
        .adversaryDefensePower(returnDefense)
        .damageCaused(returnDamage)
        .build();
  }

  private void saveTurn(
      Battle battle,
      Integer attackPower,
      Integer defensePower,
      Integer damage) {

    Integer turnNumber = turnRepository.countByBattle(battle) + 1;
    Boolean isPlayerTurn = battle.getIsPlayerInitiative() ? turnNumber % 2 == 1 : turnNumber % 2 == 0;
    BattleCharacter character = isPlayerTurn ? battle.getPlayerBattleCharacter() : battle.getEnemyBattleCharacter();
    BattleCharacter enemy = isPlayerTurn ? battle.getEnemyBattleCharacter() : battle.getPlayerBattleCharacter();

    Turn turn = Turn.builder()
        .battle(battle)
        .number(turnNumber)
        .isPlayerTurn(isPlayerTurn)
        .playerPV(character.getPv())
        .enemyPV(enemy.getPv())
        .attackPower(attackPower)
        .defensePower(defensePower)
        .damage(damage)
        .build();

    turnRepository.save(turn);
  }

  private Integer calculeDefense(BattleCharacter character, RollDiceDto rollDice) {
    Integer defense = character.getDefense() + character.getAgility() + rollDice.getResult();

    return defense;
  }

  private Integer calculeAttack(BattleCharacter character, RollDiceDto rollDice) {
    Integer attack = character.getStrength() + character.getAgility() + rollDice.getResult();

    return attack;
  }

}
