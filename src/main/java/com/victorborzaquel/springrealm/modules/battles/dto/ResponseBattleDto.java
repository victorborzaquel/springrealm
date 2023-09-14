package com.victorborzaquel.springrealm.modules.battles.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleDto implements Serializable {
  private UUID id;
  private LocalDateTime createdAt;
  private LocalDateTime endedAt;
  private Boolean isInProgress;
  private Boolean isPlayerWinner;
  private Integer quantityTurns;
  private Boolean isPlayerInitiative;
  private ResponsePlayerBattleDto player;
  private ResponseEnemyBattleDto enemy;
}
