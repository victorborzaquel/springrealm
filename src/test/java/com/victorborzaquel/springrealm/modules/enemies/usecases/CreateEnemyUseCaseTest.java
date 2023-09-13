package com.victorborzaquel.springrealm.modules.enemies.usecases;

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

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyCharacterIsNotMonsterException;

@ExtendWith(MockitoExtension.class)
class CreateEnemyUseCaseTest {
  @Mock
  private EnemyRepository enemyRepository;
  @Mock
  private CharacterRepository characterRepository;
  @InjectMocks
  private CreateEnemyUseCase createEnemyUseCase;

  private final String slug = "monster";

  private CreateEnemyDto dto;
  private CharacterEntity character;

  @BeforeEach
  void setUp() {
    dto = CreateEnemyDto.builder()
        .firstName("victor")
        .lastName("borzaquel")
        .characterSlug("guerreiro")
        .slug(slug)
        .build();

    character = CharacterEntity.builder()
        .slug("guerreiro")
        .type(CharacterType.MONSTER)
        .build();
  }

  @Test
  void testCreateEnemy() {
    when(characterRepository.findBySlugIgnoreCase(any())).thenReturn(Optional.of(character));

    ResponseEnemyDto response = createEnemyUseCase.execute(dto);

    assertEquals(dto.getFirstName(), response.getFirstName());
    assertEquals(dto.getSlug(), response.getSlug());

    verify(enemyRepository, times(1)).save(any());
  }

  @Test
  void testCharacterNotFound() {
    when(characterRepository.findBySlugIgnoreCase(any())).thenReturn(Optional.empty());

    assertThrows(CharacterNotFoundException.class, () -> createEnemyUseCase.execute(dto), "Character not found");

    verify(enemyRepository, times(0)).save(any());
  }

  @Test
  void testExistsEnemyUsername() {
    when(enemyRepository.existsBySlug(any())).thenReturn(true);

    assertThrows(EnemyAlreadyExistsException.class, () -> createEnemyUseCase.execute(dto), "Enemy already exists");

    verify(enemyRepository, times(0)).save(any());
  }

  @Test
  void testCharacterNotMonster() {
    character.setType(CharacterType.HERO);
    
    when(characterRepository.findBySlugIgnoreCase(any())).thenReturn(Optional.of(character));

    assertThrows(EnemyCharacterIsNotMonsterException.class, () -> createEnemyUseCase.execute(dto), "Enemy character is not a monster");

    verify(enemyRepository, times(0)).save(any());
  }
}
