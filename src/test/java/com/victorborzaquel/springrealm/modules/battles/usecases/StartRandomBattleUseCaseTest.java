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
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.StartRandomBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

@ExtendWith(MockitoExtension.class)
class StartRandomBattleUseCaseTest {

  @Mock
  private BattleRepository battleRepository;
  @Mock
  private EnemyRepository enemyRepository;
  @Mock
  private PlayerRepository playerRepository;
  @InjectMocks
  private StartRandomBattleUseCase startRandomBattleUseCase;

  private CharacterEntity enemyCharacter;
  private EnemyEntity enemy;
  private CharacterEntity playerCharacter;
  private PlayerEntity player;
  private StartRandomBattleDto dto;
  private BattleEntity battle;

  private String playerUsername = "victor";
  private String enemySlug = "goblin";

  @BeforeEach
  void setUp() {
    dto = StartRandomBattleDto.builder().playerUsername(playerUsername).build();

    enemyCharacter = CharacterEntity.builder().build();

    enemy = EnemyEntity.builder()
        .firstName("Goblin")
        .character(enemyCharacter)
        .slug(enemySlug)
        .build();

    playerCharacter = CharacterEntity.builder().build();

    player = PlayerEntity.builder()
        .firstName("Victor")
        .character(playerCharacter)
        .username(playerUsername)
        .build();

    battle = BattleEntity.builder().enemy(enemy).player(player).build();
  }

  @Test
  void testPlayerAlreadyInBattle() {
    when(enemyRepository.findRandom()).thenReturn(Optional.ofNullable(enemy));
    when(playerRepository.findByUsernameIgnoreCase(playerUsername)).thenReturn(Optional.ofNullable(player));
    when(battleRepository.existsByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(true);

    assertThrows(PlayerAlreadyInBattleException.class, () -> startRandomBattleUseCase.execute(dto), "Player already in battle");    
  
    verify(battleRepository, times(0)).save(any());
  }

  @Test
  void testEnemyNotFound() {
    when(enemyRepository.findRandom()).thenReturn(Optional.ofNullable(null));

    assertThrows(EnemyNotFoundException.class, () -> startRandomBattleUseCase.execute(dto), "Enemy not found");    
  
    verify(battleRepository, times(0)).save(any());
  }

  @Test
  void testPlayerNotFound() {
    when(enemyRepository.findRandom()).thenReturn(Optional.ofNullable(enemy));
    when(playerRepository.findByUsernameIgnoreCase(playerUsername)).thenReturn(Optional.ofNullable(null));

    assertThrows(PlayerNotFoundException.class, () -> startRandomBattleUseCase.execute(dto), "Player not found");    
  
    verify(battleRepository, times(0)).save(any());
  }

  @Test
  void testStartBattle() {
    when(enemyRepository.findRandom()).thenReturn(Optional.ofNullable(enemy));
    when(playerRepository.findByUsernameIgnoreCase(playerUsername)).thenReturn(Optional.ofNullable(player));
    when(battleRepository.existsByPlayerUsernameAndEndedAtNull(playerUsername)).thenReturn(false);

    when(battleRepository.save(any())).thenReturn(battle);
        
    ResponseStartBattleDto response = startRandomBattleUseCase.execute(dto);

    assertEquals(playerUsername, response.getPlayer().getUsername(), "Player username");
    assertEquals(enemySlug, response.getEnemy().getSlug(), "Enemy first name");

    verify(battleRepository, times(1)).save(any());
  }
}