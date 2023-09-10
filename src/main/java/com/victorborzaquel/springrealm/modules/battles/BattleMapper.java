package com.victorborzaquel.springrealm.modules.battles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacter;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleEnemy;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattlePlayer;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackTurnDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleEnemy;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattlePlayer;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseTurnDto;
import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.utils.dto.RollDiceDto;

@Mapper
public interface BattleMapper {
  BattleMapper INSTANCE = Mappers.getMapper(BattleMapper.class);

  // @Mapping(target = "slug", source = "player.username")
  // ResponseAttackBattlePlayer toEntity(Player player);

  // ResponseAttackBattleEnemy toEntity(Enemy enemy);

  @Mapping(target = "name", source = "battleCharacter.name")
  @Mapping(target = "character", source = "battleCharacter")
  ResponseAttackBattlePlayer toResponseBattleEntity(Player player, BattleCharacter battleCharacter,
      ResponseTurnDto turn);

  @Mapping(target = "name", source = "battleCharacter.name")
  @Mapping(target = "character", source = "battleCharacter")
  ResponseAttackBattleEnemy toResponseBattleEntity(Enemy enemy, BattleCharacter battleCharacter, ResponseTurnDto turn);

  @Mapping(target = "player", expression = "java(toResponseBattleEntity(battle.getPlayer(), battle.getPlayerBattleCharacter(), turn.getPlayer()))")
  @Mapping(target = "enemy", expression = "java(toResponseBattleEntity(battle.getEnemy(), battle.getEnemyBattleCharacter(), turn.getEnemy()))")
  ResponseAttackBattleDto toDto(Battle battle, ResponseAttackTurnDto turn);

  @Mapping(target = "name", source = "player.name")
  @Mapping(target = "character.pv", source = "player.character.life")
  ResponseStartBattlePlayer toResponseStartBattleEntity(Player player, RollDiceDto initiativeDice);

  @Mapping(target = "name", source = "enemy.name")
  @Mapping(target = "character.pv", source = "enemy.character.life")
  ResponseStartBattleEnemy toResponseStartBattleEntity(Enemy enemy, RollDiceDto initiativeDice);

  @Mapping(target = "player", expression = "java(toResponseStartBattleEntity(battle.getPlayer(), playerRollDice))")
  @Mapping(target = "enemy", expression = "java(toResponseStartBattleEntity(battle.getEnemy(), enemyRollDice))")
  ResponseStartBattleDto toDto(Battle battle, RollDiceDto playerRollDice, RollDiceDto enemyRollDice);

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "turns", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "endedAt", ignore = true)
  @Mapping(target = "playerBattleCharacter", source = "player.character")
  @Mapping(target = "enemyBattleCharacter", source = "enemy.character")
  @Mapping(target = "enemy", source = "enemy")
  @Mapping(target = "player", source = "player")
  Battle toEntity(Enemy enemy, Player player, Boolean isPlayerInitiative);

  @Mapping(target = "enemyBattles", ignore = true)
  @Mapping(target = "playerBattles", ignore = true)
  @Mapping(target = "pv", source = "character.life")
  BattleCharacter toEntity(Character character);
}
