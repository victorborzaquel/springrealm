package com.victorborzaquel.springrealm.modules.history.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTurnLogDto implements Serializable {
  private LocalDateTime createdAt;
  private Integer number;
  private Boolean isPlayerTurn;
  private Integer playerPV;
  private Integer enemyPV;
  private Integer attackPower;
  private Integer defensePower;
  private Integer damage;
}
