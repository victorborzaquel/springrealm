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

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.BattleStatus;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseRollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.dto.RollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.NotAtThatStageException;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

@ExtendWith(MockitoExtension.class)
public class RollInitialDiceUseCaseTest {

  @Mock
  private DiceProvider diceProvider;
  @Mock
  private BattleRepository battleRepository;
  @InjectMocks
  private RollInitialDiceUseCase rollInitialDiceUseCase;

  private RollInitialDiceDto dto;
  private BattleEntity battle;

  private String playerUsername = "victor";

  @BeforeEach
  void setUp() {
    dto = RollInitialDiceDto.builder().playerUsername(playerUsername).build();
    battle = BattleEntity.builder().build();
  }

  @Test
  void testBattleNotInThisStage() {
    BattleEntity battle = BattleEntity.builder().status(BattleStatus.PLAYER_TURN).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(Optional.ofNullable(battle));

    assertThrows(NotAtThatStageException.class, () -> rollInitialDiceUseCase.execute(dto),
        "Not at that stage. current stage is: PLAYER_TURN.");

    verify(battleRepository, times(0)).save(any());
  }

  @Test
  void testPlayerAlreadyInBattle() {
    when(battleRepository.findByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(Optional.ofNullable(null));
  
    assertThrows(PlayerNotAlreadyInBattleException.class, () -> rollInitialDiceUseCase.execute(dto), "Player not already in battle");
  
    verify(battleRepository, times(0)).save(any());
  }

  @Test
  void testDicesDraw() {
    RollDiceDto playerDice = RollDiceDto.builder().result(10).build();
    RollDiceDto enemyDice = RollDiceDto.builder().result(10).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(Optional.ofNullable(battle));
    when(diceProvider.rollInitiativeDice()).thenReturn(playerDice, enemyDice);

    ResponseRollInitialDiceDto response = rollInitialDiceUseCase.execute(dto);

    verify(battleRepository, times(0)).save(any());

    assertEquals(battle.getIsPlayerInitiative(), null, "Battle Is player initiative");
    assertEquals(battle.getStatus(), BattleStatus.INITIAL_ROLL, "Battle status");

    assertEquals(response.getIsPlayerInitiative(), null, "Response Is player initiative");
    assertEquals(response.getIsDraw(), true, "Response Battle status");
    assertEquals(response.getEnemyDice(), enemyDice, "Response Enemy dice");
    assertEquals(response.getPlayerDice(), playerDice, "Response Player dice");
  }

  @Test
  void testPlayerWins() {
    RollDiceDto playerDice = RollDiceDto.builder().result(10).build();
    RollDiceDto enemyDice = RollDiceDto.builder().result(5).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(Optional.ofNullable(battle));
    when(diceProvider.rollInitiativeDice()).thenReturn(playerDice, enemyDice);

    ResponseRollInitialDiceDto response = rollInitialDiceUseCase.execute(dto);

    verify(battleRepository, times(1)).save(any());

    assertEquals(battle.getIsPlayerInitiative(), true, "Battle Is player initiative");
    assertEquals(battle.getStatus(), BattleStatus.PLAYER_TURN, "Battle status");

    assertEquals(response.getIsPlayerInitiative(), true, "Response Is player initiative");
    assertEquals(response.getIsDraw(), false, "Response Battle status");
    assertEquals(response.getEnemyDice(), enemyDice, "Response Enemy dice");
    assertEquals(response.getPlayerDice(), playerDice, "Response Player dice");
  }

  @Test
  void testEnemyWins() {
    RollDiceDto playerDice = RollDiceDto.builder().result(5).build();
    RollDiceDto enemyDice = RollDiceDto.builder().result(10).build();

    when(battleRepository.findByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(Optional.ofNullable(battle));
    when(diceProvider.rollInitiativeDice()).thenReturn(playerDice, enemyDice);

    ResponseRollInitialDiceDto response = rollInitialDiceUseCase.execute(dto);

    verify(battleRepository, times(1)).save(any());

    assertEquals(battle.getIsPlayerInitiative(), false, "Battle Is player initiative");
    assertEquals(battle.getStatus(), BattleStatus.ENEMY_TURN, "Battle status");

    assertEquals(response.getIsPlayerInitiative(), false, "Response Is player initiative");
    assertEquals(response.getIsDraw(), false, "Response Battle status");
    assertEquals(response.getEnemyDice(), enemyDice, "Response Enemy dice");
    assertEquals(response.getPlayerDice(), playerDice, "Response Player dice");
  }
}
