package com.victorborzaquel.springrealm.modules.characters;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.UpdateCharacterDto;

public class CharacterMapper {
  private CharacterMapper() {
  }

  public static ResponseCharacterDto toDto(CharacterEntity character) {
    return ResponseCharacterDto.builder()
        .id(character.getId())
        .agility(character.getAgility())
        .defense(character.getDefense())
        .dice(character.getDice())
        .life(character.getLife())
        .name(character.getName())
        .slug(character.getSlug())
        .strength(character.getStrength())
        .type(character.getType())
        .build();
  }

  public static List<ResponseCharacterDto> toDto(List<CharacterEntity> characters) {
    return characters.stream().map(CharacterMapper::toDto).toList();
  }

  public static Page<ResponseCharacterDto> toDto(Page<CharacterEntity> characters) {
    return characters.map(CharacterMapper::toDto);
  }

  public static CharacterEntity toEntity(CreateCharacterDto dto) {
    return CharacterEntity.builder()
        .agility(dto.getAgility())
        .defense(dto.getDefense())
        .id(UUID.randomUUID())
        .life(dto.getLife())
        .name(dto.getName())
        .quantityDices(dto.getQuantityDices())
        .strength(dto.getStrength())
        .slug(dto.getSlug())
        .quantityFaces(dto.getQuantityFaces())
        .type(dto.getType())
        .build();
  }

  public static CharacterEntity toEntity(UUID id, UpdateCharacterDto dto, CharacterType type) {
    return CharacterEntity.builder()
        .agility(dto.getAgility())
        .defense(dto.getDefense())
        .id(id)
        .life(dto.getLife())
        .name(dto.getName())
        .quantityDices(dto.getQuantityDices())
        .strength(dto.getStrength())
        .slug(dto.getSlug())
        .quantityFaces(dto.getQuantityFaces())
        .type(type)
        .build();
  }
}
