package com.victorborzaquel.springrealm.modules.enemies;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;

@Mapper
public interface EnemyMapper {
  EnemyMapper INSTANCE = Mappers.getMapper(EnemyMapper.class);

  ResponseEnemyDto toDto(Enemy enemy);

  List<ResponseEnemyDto> toDto(List<Enemy> enemies);

  default Page<ResponseEnemyDto> toDto(Page<Enemy> enemyPage) {
    return new PageImpl<>(
        toDto(enemyPage.getContent()),
        enemyPage.getPageable(),
        enemyPage.getTotalElements());
  }

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "battles", ignore = true)
  @Mapping(target = "slug", source = "dto.slug")
  @Mapping(target = "character", source = "character")
  @Mapping(target = "name", ignore = true)
  Enemy toEntity(CreateEnemyDto dto, Character character);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "battles", ignore = true)
  @Mapping(target = "slug", source = "dto.slug")
  @Mapping(target = "character", source = "character")
  Enemy toEntity(UUID id, UpdateEnemyDto dto, Character character);
}
