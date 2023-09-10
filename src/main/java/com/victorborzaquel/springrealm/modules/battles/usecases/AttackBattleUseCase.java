package com.victorborzaquel.springrealm.modules.battles.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.AttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;
import com.victorborzaquel.springrealm.modules.turns.Turn;
import com.victorborzaquel.springrealm.utils.dices.DiceUtil;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttackBattleUseCase {

  private final BattleRepository battleRepository;
  private final PlayerRepository playerRepository;

  public ResponseBattleDto execute(AttackBattleDto dto) {
    Player player = playerRepository.findByUsernameIgnoreCase(dto.getPlayerUsername())
        .orElseThrow(PlayerNotFoundException::new);

    Battle battle = battleRepository.findByPlayerAndInProgressTrue(player)
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    Enemy enemy = battle.getEnemy();

    Character playerCharacter = player.getCharacter();
    Character enemyCharacter = enemy.getCharacter();

    RollDicesDto playerPowerDices = DiceUtil.rollTurnDice();
    RollDicesDto enemyPowerDices = DiceUtil.rollTurnDice();

    if (battle.getIsPlayerInitiative()) {
      Optional<RollDicesDto> playerDices = damage(battle, playerCharacter, enemyCharacter);

      if (battle.getInProgress()) {
        Optional<RollDicesDto> enemyDices = damage(battle, enemyCharacter, playerCharacter);
      }
    } else {
      Optional<RollDicesDto> enemyDices = damage(battle, enemyCharacter, playerCharacter);

      if (battle.getInProgress()) {
        Optional<RollDicesDto> playerDices = damage(battle, playerCharacter, enemyCharacter);
      }
    }

    battleRepository.save(battle);

    return BattleMapper.INSTANCE.toDto(battle);
  }

  private Optional<RollDicesDto> damage(Battle battle, Character attackCharacter, Character defenseCharacter) {
    Optional<RollDicesDto> rollDices = attack(attackCharacter, defenseCharacter);

    if (rollDices.isPresent()) {
      battle.playerAttack(rollDices.get().getResult());

      if (battle.getEnemyPV() <= 0) {
        battle.endBattle();
      }
    }

    return rollDices;
  }

  private Optional<RollDicesDto> attack(Character attackCharacter, Character defenseCharacter) {
    Integer defense = calculeDefense(defenseCharacter);
    Integer attack = calculeAttack(attackCharacter);

    RollDicesDto rollDices = (attack < defense) ? null : DiceUtil.rollDamageDice(attackCharacter);

    return Optional.ofNullable(rollDices);
  }

  private Integer calculeDefense(Character character) {
    Integer defensePower = DiceUtil.rollTurnDice().getResult();
    Integer defense = character.getDefense() + character.getAgility() + defensePower;

    return defense;
  }

  private Integer calculeAttack(Character character) {
    Integer attackPower = DiceUtil.rollTurnDice().getResult();
    Integer attack = character.getStrength() + character.getAgility() + attackPower;

    return attack;
  }

  private Turn createTurn() {

  }
}
