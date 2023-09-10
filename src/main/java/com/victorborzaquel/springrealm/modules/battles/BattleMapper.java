package com.victorborzaquel.springrealm.modules.battles;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

@Mapper
public interface BattleMapper {
  BattleMapper INSTANCE = Mappers.getMapper(BattleMapper.class);

  ResponseBattleDto toDto(Battle battle);

  ResponseStartBattleDto toDto(Battle battle, RollDicesDto playerRollDices, RollDicesDto enemyRollDices);

  List<ResponseBattleDto> toDto(List<Battle> battles);

  default Page<ResponseBattleDto> toDto(Page<Battle> battlePage) {
    return new PageImpl<>(
        toDto(battlePage.getContent()),
        battlePage.getPageable(),
        battlePage.getTotalElements());
  }

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "turns", ignore = true)
  @Mapping(target = "inProgress", ignore = true)
  @Mapping(target = "playerPV", source = "player.character.life")
  @Mapping(target = "enemyPV", source = "enemy.character.life")
  @Mapping(target = "enemy", source = "enemy")
  @Mapping(target = "player", source = "player")
  Battle toEntity(Enemy enemy, Player player, Boolean isPlayerInitiative);

}
