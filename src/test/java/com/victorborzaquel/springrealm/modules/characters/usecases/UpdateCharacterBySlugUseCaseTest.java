package com.victorborzaquel.springrealm.modules.characters.usecases;

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
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.UpdateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdateCharacterBySlugUseCaseTest {

  @Mock
  private CharacterRepository characterRepository;

  @InjectMocks
  private UpdateCharacterBySlugUseCase updateCharacterBySlugUseCase;

  private final UUID id = UUID.fromString("84f40250-e73f-4ee1-b903-25bcdbb6cddc");
  private final String slug = "guerreiro";

  private UpdateCharacterDto dto;
  private CharacterEntity entity;

  @BeforeEach
  void setUp() {
    dto = UpdateCharacterDto.builder()
        .name("Guerreiro")
        .life(20)
        .strength(7)
        .defense(5)
        .agility(6)
        .slug(slug)
        .quantityDices(1)
        .quantityFaces(12)
        .build();

    entity = CharacterEntity.builder()
        .id(id)
        .name("Guerreiro")
        .life(20)
        .type(CharacterType.HERO)
        .strength(7)
        .defense(5)
        .agility(6)
        .quantityDices(1)
        .quantityFaces(12)
        .build();
  }

  @Test
  void testUpdateCharacter() {
    when(characterRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.of(entity));

    ResponseCharacterDto response = updateCharacterBySlugUseCase.execute(slug, dto);

    String dice = dto.getQuantityDices() + "d" + dto.getQuantityFaces();

    assertEquals(dto.getName(), response.getName());
    assertEquals(dto.getLife(), response.getLife());
    assertEquals(CharacterType.HERO, response.getType());
    assertEquals(dto.getStrength(), response.getStrength());
    assertEquals(dto.getDefense(), response.getDefense());
    assertEquals(dto.getAgility(), response.getAgility());
    assertEquals(dice, response.getDice());

    verify(characterRepository, times(1)).save(any());
  }

  @Test
  void testCharacterNotFound() {
    when(characterRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.empty());

    assertThrows(CharacterNotFoundException.class, () -> updateCharacterBySlugUseCase.execute(slug, dto), "Character not found");

    verify(characterRepository, times(0)).save(any());
  }

  @Test
  void testExistsNameCharacter() {
    when(characterRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.of(entity));
    when(characterRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), id)).thenReturn(true);

    assertThrows(CharacterAlreadyExistsException.class, () -> updateCharacterBySlugUseCase.execute(slug, dto));

    verify(characterRepository, times(0)).save(any());
  }

  @Test
  void testExistsSlugCharacter() {
    when(characterRepository.findBySlugIgnoreCase(slug)).thenReturn(Optional.of(entity));
    when(characterRepository.existsBySlugIgnoreCaseAndIdNot(dto.getSlug(), id)).thenReturn(true);

    assertThrows(CharacterAlreadyExistsException.class, () -> updateCharacterBySlugUseCase.execute(slug, dto));

    verify(characterRepository, times(0)).save(any());
  }
}