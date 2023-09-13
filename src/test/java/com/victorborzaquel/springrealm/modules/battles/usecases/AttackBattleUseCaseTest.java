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
import com.victorborzaquel.springrealm.modules.battles.dto.AttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.NotAtThatStageException;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;

@ExtendWith(MockitoExtension.class)
public class AttackBattleUseCaseTest {
  @Mock
  private BattleRepository battleRepository;
  @Mock
  private TurnRepository turnRepository;
  @Mock
  private DiceProvider diceProvider;
  @InjectMocks
  private AttackBattleUseCase attackBattleUseCase;

  private AttackBattleDto dto;
  private BattleEntity battle;

  @BeforeEach
  void setUp() {
    BattleCharacterEntity playerBattleCharacter = BattleCharacterEntity.builder()
        .strength(10)
        .pv(100)
        .agility(10)
        .build();

    BattleCharacterEntity enemyBattleCharacter = BattleCharacterEntity.builder()
        .defense(10)
        .pv(100)
        .agility(10)
        .build();

    battle = BattleEntity.builder()
        .playerBattleCharacter(playerBattleCharacter)
        .enemyBattleCharacter(enemyBattleCharacter).status(BattleStatus.PLAYER_TURN).build();
    dto = AttackBattleDto.builder().playerUsername("victor").build();
  }

  @Test
  void testPlayerAlreadyInBattle() {
    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.empty());

    assertThrows(PlayerNotAlreadyInBattleException.class, () -> attackBattleUseCase.execute(dto),
        "Player not already in battle");
  }

  @Test
  void testNotPlayerTurn() {
    BattleEntity battle = BattleEntity.builder().status(BattleStatus.FINISHED).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));

    assertThrows(NotAtThatStageException.class, () -> attackBattleUseCase.execute(dto),
        "Not at that stage. current stage is: FINISHED");
  }

  @Test
  void testEnemyDefenseAttack() {
    RollDiceDto attackRollDice = RollDiceDto.builder().result(5).build();
    RollDiceDto defenseRollDice = RollDiceDto.builder().result(10).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));
    when(diceProvider.rollTurnDice()).thenReturn(attackRollDice, defenseRollDice);

    ResponseAttackBattleDto response = attackBattleUseCase.execute(dto);

    assertEquals(0, response.getDamage());
    assertEquals(25, response.getAttackPower());
    assertEquals(30, response.getDefensePower());
    assertEquals(100, response.getEnemy().getPv());
    assertEquals(100, response.getPlayer().getPv());

    assertEquals(1, battle.getQuantityTurns());
    assertEquals(BattleStatus.ENEMY_TURN, battle.getStatus());
    assertEquals(100, battle.getEnemyBattleCharacter().getPv());
    assertEquals(100, battle.getPlayerBattleCharacter().getPv());

    verify(battleRepository, times(1)).save(any());
    verify(turnRepository, times(1)).save(any());
  }

  @Test
  void testPlayerAttack() {
    RollDiceDto attackRollDice = RollDiceDto.builder().result(10).build();
    RollDiceDto defenseRollDice = RollDiceDto.builder().result(5).build();
    RollDiceDto damageRollDice = RollDiceDto.builder().result(10).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));
    when(diceProvider.rollTurnDice()).thenReturn(attackRollDice, defenseRollDice);
    when(diceProvider.rollDamageDice(any())).thenReturn(damageRollDice);

    ResponseAttackBattleDto response = attackBattleUseCase.execute(dto);

    assertEquals(10, response.getDamage());
    assertEquals(30, response.getAttackPower());
    assertEquals(25, response.getDefensePower());
    assertEquals(90, response.getEnemy().getPv());
    assertEquals(100, response.getPlayer().getPv());

    assertEquals(1, battle.getQuantityTurns());
    assertEquals(BattleStatus.ENEMY_TURN, battle.getStatus());
    assertEquals(90, battle.getEnemyBattleCharacter().getPv());
    assertEquals(100, battle.getPlayerBattleCharacter().getPv());

    verify(battleRepository, times(1)).save(any());
    verify(turnRepository, times(1)).save(any());
  }

  @Test
  void testEnemyIsDead() {
    RollDiceDto attackRollDice = RollDiceDto.builder().result(10).build();
    RollDiceDto defenseRollDice = RollDiceDto.builder().result(5).build();
    RollDiceDto damageRollDice = RollDiceDto.builder().result(200).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())).thenReturn(Optional.of(battle));
    when(diceProvider.rollTurnDice()).thenReturn(attackRollDice, defenseRollDice);
    when(diceProvider.rollDamageDice(any())).thenReturn(damageRollDice);

    ResponseAttackBattleDto response = attackBattleUseCase.execute(dto);

    assertEquals(200, response.getDamage());
    assertEquals(30, response.getAttackPower());
    assertEquals(25, response.getDefensePower());
    assertEquals(0, response.getEnemy().getPv());
    assertEquals(100, response.getPlayer().getPv());

    assertEquals(1, battle.getQuantityTurns());
    assertEquals(0, battle.getEnemyBattleCharacter().getPv());
    assertEquals(100, battle.getPlayerBattleCharacter().getPv());

    verify(battleRepository, times(1)).save(any());
    verify(turnRepository, times(1)).save(any());
  }
}
