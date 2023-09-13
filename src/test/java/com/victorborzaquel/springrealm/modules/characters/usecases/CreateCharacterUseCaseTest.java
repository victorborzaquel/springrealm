package com.victorborzaquel.springrealm.modules.characters.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class CreateCharacterUseCaseTest {

  @Mock
  private CharacterRepository characterRepository;

  @InjectMocks
  private CreateCharacterUseCase createCharacterUseCase;

  private final UUID id = UUID.fromString("84f40250-e73f-4ee1-b903-25bcdbb6cddc");

  private CreateCharacterDto dto;
  private CharacterEntity entity;

  @BeforeEach
  void setUp() {
    dto = CreateCharacterDto.builder()
        .name("Guerreiro")
        .life(20)
        .type(CharacterType.HERO)
        .strength(7)
        .defense(5)
        .agility(6)
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
  void testCreateCharacter() {
    when(characterRepository.save(any())).thenReturn(entity);

    ResponseCharacterDto response = createCharacterUseCase.execute(dto);

    verify(characterRepository, times(1)).save(any());

    String dice = dto.getQuantityDices() + "d" + dto.getQuantityFaces();

    assertEquals(dto.getName(), response.getName());
    assertEquals(dto.getLife(), response.getLife());
    assertEquals(dto.getType(), response.getType());
    assertEquals(dto.getStrength(), response.getStrength());
    assertEquals(dto.getDefense(), response.getDefense());
    assertEquals(dto.getAgility(), response.getAgility());
    assertEquals(dice, response.getDice());
  }

  @Test
  void testExistsCharacterName() {
    when(characterRepository.existsByName(any())).thenReturn(true);

    assertThrows(CharacterAlreadyExistsException.class, () -> createCharacterUseCase.execute(dto), "Character already exists");

    verify(characterRepository, times(0)).save(any());
  }

  @Test
  void testExistsCharacterSlug() {
    when(characterRepository.existsBySlug(any())).thenReturn(true);

    assertThrows(CharacterAlreadyExistsException.class, () -> createCharacterUseCase.execute(dto), "Character already exists");

    verify(characterRepository, times(0)).save(any());
  }
}