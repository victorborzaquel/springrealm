package com.victorborzaquel.springrealm.modules.enemies;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;

public class EnemyMapper {
  public static EnemyEntity toEntity(UUID id, UpdateEnemyDto dto, CharacterEntity character) {
    return EnemyEntity.builder()
        .character(character)
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .slug(dto.getSlug())
        .id(id)
        .build();
  }

  public static EnemyEntity toEntity(CreateEnemyDto dto, CharacterEntity character) {
    return EnemyEntity.builder()
        .character(character)
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .slug(dto.getSlug())
        .id(UUID.randomUUID())
        .build();
  }

  public static ResponseEnemyDto toDto(EnemyEntity enemy) {
    return ResponseEnemyDto.builder()
        .character(toDtoResponseCharacter(enemy.getCharacter()))
        .firstName(enemy.getFirstName())
        .lastName(enemy.getLastName())
        .fullName(enemy.getName())
        .slug(enemy.getSlug())
        .build();
  }

  public static List<ResponseEnemyDto> toDto(List<EnemyEntity> enemies) {
    return enemies.stream().map(EnemyMapper::toDto).toList();
  }

  public static Page<ResponseEnemyDto> toDto(Page<EnemyEntity> enemies) {
    return enemies.map(EnemyMapper::toDto);
  }

  private static ResponseCharacterDto toDtoResponseCharacter(CharacterEntity character) {
    return ResponseCharacterDto.builder()
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
}
