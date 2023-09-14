package com.victorborzaquel.springrealm.modules.enemies;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;

public class EnemyMapper {
  private EnemyMapper() {
  }

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
        .id(enemy.getId())
        .character(CharacterMapper.toDto(enemy.getCharacter()))
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
}
