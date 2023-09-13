package com.victorborzaquel.springrealm.modules.enemies.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyCharacterIsNotMonsterException;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdateEnemyBySlugUseCaseTest {
  @Mock
  private EnemyRepository enemyRepository;
  @Mock
  private CharacterRepository characterRepository;
  @InjectMocks
  private UpdateEnemyBySlugUseCase updateEnemyBySlugUseCase;

  private final UUID id = UUID.fromString("84f40250-e73f-4ee1-b903-25bcdbb6cddc");
  private final String slug = "enemy";

  private UpdateEnemyDto dto;
  private EnemyEntity entity;
  private CharacterEntity character;

  @BeforeEach
  void setUp() {
    dto = UpdateEnemyDto.builder()
        .firstName("victor")
        .slug(slug)
        .characterSlug("monster")
        .build();

    character = CharacterEntity.builder().slug("monster").type(CharacterType.MONSTER).build();

    entity = EnemyEntity.builder()
        .id(id)
        .firstName("victor")
        .slug(slug)
        .character(character)
        .build();
  }

  @Test
  void testUpdateEnemy() {
    when(enemyRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.of(entity));
    when(characterRepository.findBySlugIgnoreCase(dto.getCharacterSlug())).thenReturn(Optional.of(character));

    ResponseEnemyDto response = updateEnemyBySlugUseCase.execute(slug, dto);

    assertEquals(dto.getFirstName(), response.getFirstName());
    assertEquals(dto.getSlug(), response.getSlug());
    assertEquals(dto.getCharacterSlug(), response.getCharacter().getSlug());

    verify(enemyRepository, times(1)).save(any());
  }

  @Test
  void testEnemyNotFound() {
    when(enemyRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.empty());

    assertThrows(EnemyNotFoundException.class, () -> updateEnemyBySlugUseCase.execute(slug, dto), "Enemy not found");

    verify(enemyRepository, times(0)).save(any());
  }

  @Test
  void testExistsEnemyUsername() {
    when(enemyRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.of(entity));
    when(enemyRepository.existsBySlugAndIdNot(dto.getSlug(), id)).thenReturn(true);

    assertThrows(EnemyAlreadyExistsException.class, () -> updateEnemyBySlugUseCase.execute(slug, dto));

    verify(enemyRepository, times(0)).save(any());
  }

  @Test
  void testCharacterNotMonster() {
    character.setType(CharacterType.HERO);

    when(enemyRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.of(entity));
    when(characterRepository.findBySlugIgnoreCase(any())).thenReturn(Optional.of(character));

    assertThrows(EnemyCharacterIsNotMonsterException.class, () -> updateEnemyBySlugUseCase.execute(slug, dto),
        "Enemy character is not a monster");

    verify(enemyRepository, times(0)).save(any());
  }
}