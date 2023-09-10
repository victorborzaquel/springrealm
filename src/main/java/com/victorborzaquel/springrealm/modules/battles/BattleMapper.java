package com.victorborzaquel.springrealm.modules.battles;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacter;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleEntity;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleEntity;
import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.turns.Turn;
import com.victorborzaquel.springrealm.modules.turns.dto.ResponseTurnDto;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

@Mapper
public interface BattleMapper {
  BattleMapper INSTANCE = Mappers.getMapper(BattleMapper.class);

  @Mapping(target = "slug", source = "player.username")
  ResponseBattleEntity toEntity(Player player);

  ResponseBattleEntity toEntity(Enemy enemy);

  @Mapping(target = "slug", source = "player.username")
  @Mapping(target = "name", source = "battleCharacter.name")
  @Mapping(target = "character", source = "battleCharacter")
  ResponseBattleEntity toEntity(Player player, BattleCharacter battleCharacter);

  @Mapping(target = "name", source = "battleCharacter.name")
  @Mapping(target = "character", source = "battleCharacter")
  ResponseBattleEntity toEntity(Enemy enemy, BattleCharacter battleCharacter);

  @Mapping(target = "player", expression = "java(toEntity(battle.getPlayer(), battle.getPlayerBattleCharacter()))")
  @Mapping(target = "enemy", expression = "java(toEntity(battle.getEnemy(), battle.getEnemyBattleCharacter()))")
  @Mapping(target = "turns", source = "turns")
  ResponseBattleDto toDto(Battle battle, List<Turn> turns);

  @Mapping(target = "slug", source = "player.username")
  ResponseStartBattleEntity toEntity(Player player, RollDicesDto rollDices);

  ResponseStartBattleEntity toEntity(Enemy player, RollDicesDto rollDices);

  @Mapping(target = "player", expression = "java(toEntity(battle.getPlayer(), playerRollDices))")
  @Mapping(target = "enemy", expression = "java(toEntity(battle.getEnemy(), enemyRollDices))")
  ResponseStartBattleDto toDto(Battle battle, RollDicesDto playerRollDices, RollDicesDto enemyRollDices);

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "turns", ignore = true)
  @Mapping(target = "inProgress", ignore = true)
  @Mapping(target = "playerBattleCharacter", source = "player.character")
  @Mapping(target = "enemyBattleCharacter", source = "enemy.character")
  @Mapping(target = "enemy", source = "enemy")
  @Mapping(target = "player", source = "player")
  Battle toEntity(Enemy enemy, Player player, Boolean isPlayerInitiative);

  @Mapping(target = "enemyBattles", ignore = true)
  @Mapping(target = "playerBattles", ignore = true)
  BattleCharacter toEntity(Character character);

  ResponseTurnDto toDto(Turn turn);
}
