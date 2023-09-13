package com.victorborzaquel.springrealm.modules.logs.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleHistoryDto {
  private UUID id;
  private LocalDateTime date;
  private Boolean playerWin;
  private Integer quantityTurns;
  private Boolean isPlayerInitiative;
  private ResponsePlayerDto player;
  private ResponseEnemyDto enemy;
}
