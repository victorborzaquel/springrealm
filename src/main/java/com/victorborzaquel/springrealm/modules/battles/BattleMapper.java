package com.victorborzaquel.springrealm.modules.battles;

import java.time.LocalDateTime;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleCharacterDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseEnemyBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponsePlayerBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseRollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.dices.DiceMapper;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.turns.TurnEntity;

public class BattleMapper {
  private BattleMapper() {
  }

  public static ResponseStartBattleDto toStartBattleDto(BattleEntity battle) {
    return ResponseStartBattleDto.builder()
        .id(battle.getId())
        .enemy(toEnemyBattleDto(battle.getEnemy(), battle.getEnemyBattleCharacter()))
        .player(toPlayerBattleDto(battle.getPlayer(), battle.getPlayerBattleCharacter()))
        .build();
  }

  public static ResponseEnemyBattleDto toEnemyBattleDto(EnemyEntity enemy, BattleCharacterEntity battleCharacter) {
    return ResponseEnemyBattleDto.builder()
        .name(enemy.getName())
        .slug(enemy.getSlug())
        .character(toResponseBattleCharacter(battleCharacter))
        .build();
  }

  public static ResponsePlayerBattleDto toPlayerBattleDto(PlayerEntity player, BattleCharacterEntity battleCharacter) {
    return ResponsePlayerBattleDto.builder()
        .name(player.getName())
        .username(player.getUsername())
        .character(toResponseBattleCharacter(battleCharacter))
        .build();
  }

  public static ResponseRollInitialDiceDto toRollInitialDiceDto(Boolean isPlayerInitiative, RollDiceDto playerDice,
      RollDiceDto enemyDice) {
    return ResponseRollInitialDiceDto.builder()
        .isDraw(isPlayerInitiative == null)
        .isPlayerInitiative(isPlayerInitiative)
        .playerDice(playerDice)
        .enemyDice(enemyDice)
        .build();
  }

  public static ResponseBattleDto toDto(BattleEntity entity) {
    return ResponseBattleDto.builder()
        .id(entity.getId())
        .createdAt(entity.getCreatedAt())
        .endedAt(entity.getEndedAt())
        .enemy(toEnemyBattleDto(entity.getEnemy(), entity.getEnemyBattleCharacter()))
        .player(toPlayerBattleDto(entity.getPlayer(), entity.getPlayerBattleCharacter()))
        .isInProgress(entity.getIsInProgress())
        .isPlayerInitiative(entity.getIsPlayerInitiative())
        .isPlayerWinner(entity.getIsPlayerWinner())
        .quantityTurns(entity.getQuantityTurns())
        .build();
  }

  public static BattleEntity toEntity(PlayerEntity player, EnemyEntity enemy) {
    return BattleEntity.builder()
        .createdAt(LocalDateTime.now())
        .enemy(enemy)
        .enemyBattleCharacter(toBattleCharacter(enemy.getCharacter()))
        .id(UUID.randomUUID())
        .player(player)
        .playerBattleCharacter(toBattleCharacter(player.getCharacter()))
        .build();
  }

  public static BattleCharacterEntity toBattleCharacter(CharacterEntity character) {
    return BattleCharacterEntity.builder()
        .agility(character.getAgility())
        .defense(character.getDefense())
        .slug(character.getSlug())
        .pv(character.getLife())
        .name(character.getName())
        .quantityDices(character.getQuantityDices())
        .type(character.getType())
        .quantityFaces(character.getQuantityFaces())
        .strength(character.getStrength())
        .life(character.getLife())
        .build();
  }

  public static ResponseBattleCharacterDto toResponseBattleCharacter(BattleCharacterEntity entity) {
    return ResponseBattleCharacterDto.builder()
        .agility(entity.getAgility())
        .defense(entity.getDefense())
        .dice(entity.getDice())
        .pv(entity.getPv())
        .name(entity.getName())
        .strength(entity.getStrength())
        .dice(entity.getDice())
        .build();
  }

  public static ResponseAttackBattleDto toResponseAttackBattleDto(TurnEntity turn) {
    return ResponseAttackBattleDto.builder()
        .attackDice(DiceMapper.toDto(turn.getAttackDice()))
        .defenseDice(DiceMapper.toDto(turn.getDefenseDice()))
        .attackPower(turn.getAttackPower())
        .defensePower(turn.getDefensePower())
        .player(toResponseBattleCharacter(turn.getBattle().getPlayerBattleCharacter()))
        .enemy(toResponseBattleCharacter(turn.getBattle().getEnemyBattleCharacter()))
        .damage(turn.getDamage())
        .damageDice(DiceMapper.toDto(turn.getDamageDice()))
        .turn(turn.getNumber())
        .isPlayerTurn(turn.getIsPlayerTurn())
        .build();
  }
}