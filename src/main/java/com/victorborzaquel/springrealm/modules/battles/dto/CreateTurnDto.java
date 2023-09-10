package com.victorborzaquel.springrealm.modules.battles.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTurnDto {
  private Integer number;
  private Boolean isPlayerTurn;
  private Integer playerPV;
  private Integer enemyPV;
  private Integer attackDice;
  private Integer defenseDice;
  private Integer damage;
  private UUID battleId;
}
