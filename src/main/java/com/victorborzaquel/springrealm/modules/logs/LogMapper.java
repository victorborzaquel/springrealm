package com.victorborzaquel.springrealm.modules.logs;

import java.util.List;

import org.springframework.data.domain.Page;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.logs.dto.ResponseBattleHistoryDto;
import com.victorborzaquel.springrealm.modules.logs.dto.ResponseBattleLogDto;
import com.victorborzaquel.springrealm.modules.logs.dto.ResponseTurnLogDto;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.turns.TurnEntity;

public class LogMapper {
  public static Page<ResponseBattleHistoryDto> toResponseBattleHistoryDto(Page<BattleEntity> battles) {
    return battles.map(LogMapper::toResponseBattleHistoryDto);
  }

  public static List<ResponseBattleHistoryDto> toResponseBattleHistoryDto(List<BattleEntity> battles) {
    return battles.stream().map(LogMapper::toResponseBattleHistoryDto).toList();
  }

  public static ResponseBattleLogDto toResponseBattleLogDto(BattleEntity battle, Page<TurnEntity> turns) {
    return ResponseBattleLogDto.builder()
        .battle(toResponseBattleHistoryDto(battle))
        .turns(toResponseTurnLogDto(turns))
        .build();
  }

  public static Page<ResponseTurnLogDto> toResponseTurnLogDto(Page<TurnEntity> turns) {
    return turns.map(LogMapper::toResponseTurnDto);
  }

  public static List<ResponseTurnLogDto> toResponseTurnLogDto(List<TurnEntity> turns) {
    return turns.stream().map(LogMapper::toResponseTurnDto).toList();
  }

  public static ResponseTurnLogDto toResponseTurnDto(TurnEntity turn) {
    return ResponseTurnLogDto.builder()
        .attackPower(turn.getAttackPower())
        .defensePower(turn.getDefensePower())
        .isPlayerTurn(turn.getIsPlayerTurn())
        .createdAt(turn.getCreatedAt())
        .damage(turn.getDamage())
        .number(turn.getNumber())
        .enemyPV(turn.getEnemyPV())
        .playerPV(turn.getPlayerPV())
        .build();
  }

  public static ResponseBattleHistoryDto toResponseBattleHistoryDto(BattleEntity battle) {
    return ResponseBattleHistoryDto.builder()
        .id(battle.getId())
        .date(battle.getCreatedAt())
        .enemy(toResponseEnemyDto(battle.getEnemy(), battle.getEnemyBattleCharacter()))
        .isPlayerInitiative(battle.getIsPlayerInitiative())
        .player(toResponsePlayerDto(battle.getPlayer(), battle.getPlayerBattleCharacter()))
        .playerWin(battle.getIsPlayerWinner())
        .quantityTurns(battle.getQuantityTurns())
        .build();
  }

  public static ResponsePlayerDto toResponsePlayerDto(PlayerEntity player, BattleCharacterEntity character) {
    return ResponsePlayerDto.builder()
        .firstName(player.getFirstName())
        .lastName(player.getLastName())
        .fullName(player.getName())
        .username(player.getUsername())
        .character(toResponseCharacterDto(character))
        .build();
  }

  public static ResponseEnemyDto toResponseEnemyDto(EnemyEntity enemy, BattleCharacterEntity character) {
    return ResponseEnemyDto.builder()
        .firstName(enemy.getFirstName())
        .lastName(enemy.getLastName())
        .fullName(enemy.getName())
        .slug(enemy.getSlug())
        .character(toResponseCharacterDto(character))
        .build();
  }

  public static ResponseBattleCharacterDto toResponseBattleCharacterDto(BattleCharacterEntity character) {
    return ResponseBattleCharacterDto.builder()
        .name(character.getName())
        .agility(character.getAgility())
        .defense(character.getDefense())
        .dice(character.getDice())
        .pv(character.getPv())
        .name(character.getName())
        .strength(character.getStrength())
        .build();
  }

  public static ResponseCharacterDto toResponseCharacterDto(BattleCharacterEntity character) {
    return ResponseCharacterDto.builder()
        .name(character.getName())
        .agility(character.getAgility())
        .defense(character.getDefense())
        .dice(character.getDice())
        .life(character.getPv())
        .name(character.getName())
        .slug(character.getSlug())
        .strength(character.getStrength())
        .type(character.getType())
        .build();
  }
}
