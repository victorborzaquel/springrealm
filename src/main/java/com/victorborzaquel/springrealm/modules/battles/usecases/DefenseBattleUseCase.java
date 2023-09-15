package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.DefenseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.NotAtThatStageException;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.dices.DiceMapper;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.DiceDto;
import com.victorborzaquel.springrealm.modules.turns.TurnEntity;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefenseBattleUseCase {
  private final BattleRepository battleRepository;
  private final TurnRepository turnRepository;

  private final DiceProvider diceProvider;

  @Transactional
  @CacheEvict(key = "#dto.playerUsername", value = "currentBattle")
  public ResponseAttackBattleDto execute(DefenseBattleDto dto) {
    BattleEntity battle = battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    if (!battle.isEnemyTurn()) {
      throw new NotAtThatStageException(battle);
    }

    battle.addTurn();

    BattleCharacterEntity player = battle.getPlayerBattleCharacter();
    BattleCharacterEntity enemy = battle.getEnemyBattleCharacter();

    DiceDto attackPowerDices = diceProvider.rollTurnDice();
    Integer attack = enemy.calculeAttack(attackPowerDices.getResult());

    DiceDto defensePowerDices = diceProvider.rollTurnDice();
    Integer defense = player.calculeDefense(defensePowerDices.getResult());

    Integer damage = 0;
    DiceDto damageDices = null;
    if (attack > defense) {
      damageDices = diceProvider.rollDamageDice(enemy);
      damage = damageDices.getResult();

      player.damage(damage);
    }

    if (player.getIsDead()) {
      battle.endBattle();
    }

    TurnEntity turn = TurnEntity.builder()
        .battle(battle)
        .number(battle.getQuantityTurns())
        .isPlayerTurn(false)
        .playerPV(player.getPv())
        .enemyPV(enemy.getPv())
        .attackPower(attack)
        .defensePower(defense)
        .damage(damage)
        .attackDice(DiceMapper.toEntity(attackPowerDices))
        .defenseDice(DiceMapper.toEntity(defensePowerDices))
        .damageDice(DiceMapper.toEntity(damageDices))
        .build();

    battle.setPlayerTurn();

    battleRepository.save(battle);
    turnRepository.save(turn);

    return BattleMapper.toResponseAttackBattleDto(turn);
  }
}
