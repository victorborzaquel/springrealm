package com.victorborzaquel.springrealm.modules.battles.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.BattleStatus;
import com.victorborzaquel.springrealm.modules.battles.dto.DefenseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.NotAtThatStageException;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.DiceDto;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;

@ExtendWith(MockitoExtension.class)
public class DefenseBattleUseCaseTest {
  @Mock
  private BattleRepository battleRepository;
  @Mock
  private TurnRepository turnRepository;
  @Mock
  private DiceProvider diceProvider;
  @InjectMocks
  private DefenseBattleUseCase defenseBattleUseCase;

  private DefenseBattleDto dto;
  private BattleEntity battle;

  @BeforeEach
  void setUp() {
    BattleCharacterEntity enemyBattleCharacter = BattleCharacterEntity.builder()
        .strength(10)
        .pv(100)
        .agility(10)
        .build();

    BattleCharacterEntity playerBattleCharacter = BattleCharacterEntity.builder()
        .defense(10)
        .pv(100)
        .agility(10)
        .build();

    battle = BattleEntity.builder()
        .playerBattleCharacter(playerBattleCharacter)
        .enemyBattleCharacter(enemyBattleCharacter).status(BattleStatus.ENEMY_TURN).build();
    dto = DefenseBattleDto.builder().playerUsername("victor").build();
  }

  @Test
  void testPlayerAlreadyInBattle() {
    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.empty());

    assertThrows(PlayerNotAlreadyInBattleException.class, () -> defenseBattleUseCase.execute(dto),
        "Player not already in battle");
  }

  @Test
  void testNotEnemyTurn() {
    BattleEntity battle = BattleEntity.builder().status(BattleStatus.FINISHED).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));

    assertThrows(NotAtThatStageException.class, () -> defenseBattleUseCase.execute(dto),
        "Not at that stage. current stage is: FINISHED");
  }

  @Test
  void testPlayerDefenseAttack() {
    DiceDto attackRollDice = DiceDto.builder().result(5).build();
    DiceDto defenseRollDice = DiceDto.builder().result(10).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));
    when(diceProvider.rollTurnDice()).thenReturn(attackRollDice, defenseRollDice);

    ResponseAttackBattleDto response = defenseBattleUseCase.execute(dto);

    assertEquals(0, response.getDamage());
    assertEquals(25, response.getAttackPower());
    assertEquals(30, response.getDefensePower());
    assertEquals(100, response.getPlayer().getPv());
    assertEquals(100, response.getEnemy().getPv());

    assertEquals(1, battle.getQuantityTurns());
    assertEquals(BattleStatus.PLAYER_TURN, battle.getStatus());
    assertEquals(100, battle.getPlayerBattleCharacter().getPv());
    assertEquals(100, battle.getEnemyBattleCharacter().getPv());

    verify(battleRepository, times(1)).save(any());
    verify(turnRepository, times(1)).save(any());
  }

  @Test
  void testEnemyAttack() {
    DiceDto attackRollDice = DiceDto.builder().result(10).build();
    DiceDto defenseRollDice = DiceDto.builder().result(5).build();
    DiceDto damageRollDice = DiceDto.builder().result(10).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));
    when(diceProvider.rollTurnDice()).thenReturn(attackRollDice, defenseRollDice);
    when(diceProvider.rollDamageDice(any())).thenReturn(damageRollDice);

    ResponseAttackBattleDto response = defenseBattleUseCase.execute(dto);

    assertEquals(10, response.getDamage());
    assertEquals(30, response.getAttackPower());
    assertEquals(25, response.getDefensePower());
    assertEquals(100, response.getEnemy().getPv());
    assertEquals(90, response.getPlayer().getPv());

    assertEquals(1, battle.getQuantityTurns());
    assertEquals(BattleStatus.PLAYER_TURN, battle.getStatus());
    assertEquals(100, battle.getEnemyBattleCharacter().getPv());
    assertEquals(90, battle.getPlayerBattleCharacter().getPv());

    verify(battleRepository, times(1)).save(any());
    verify(turnRepository, times(1)).save(any());
  }
}
