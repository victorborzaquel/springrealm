package com.victorborzaquel.springrealm.modules.turns.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTurnDto {

  private Integer number;
  private Boolean isPlayerTurn;
  private Integer playerPV;
  private Integer enemyPV;
  private Integer attackDice;
  private Integer defenseDice;
  private Integer damage;
  
}
