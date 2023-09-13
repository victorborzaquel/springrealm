package com.victorborzaquel.springrealm.modules.battles.usecases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FindCurrentBattleUseCaseTest {
  @Mock
  private PlayerRepository playerRepository;
  @Mock
  private BattleRepository battleRepository;
  @InjectMocks
  private FindCurrentBattleUseCase findCurrentBattleUseCase;

  private String playerUsername = "victor";

  private PlayerEntity player;

  @BeforeEach
  void setUp() {
    player = PlayerEntity.builder().build();
  }

  @Test
  void testPlayerNotFound() {
    when(playerRepository.findByUsernameIgnoreCase(playerUsername)).thenReturn(Optional.empty());

    assertThrows(PlayerNotFoundException.class, () -> findCurrentBattleUseCase.execute(playerUsername),
        "Player not found.");
  }

  @Test
  void testBattleNotFound() {
    when(playerRepository.findByUsernameIgnoreCase(playerUsername)).thenReturn(Optional.of(player));
    when(battleRepository.findByPlayerAndEndedAtNull(player)).thenReturn(Optional.empty());

    assertThrows(PlayerNotAlreadyInBattleException.class, () -> findCurrentBattleUseCase.execute(playerUsername),
        "Player not already in battle.");
  }
}
