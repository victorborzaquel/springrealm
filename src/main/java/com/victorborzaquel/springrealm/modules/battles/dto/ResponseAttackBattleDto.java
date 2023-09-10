package com.victorborzaquel.springrealm.modules.battles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAttackBattleDto {
  private Boolean inProgress;
  private Boolean isPlayerWinner;
  private Boolean isPlayerInitiative;
  private ResponseAttackBattlePlayer player;
  private ResponseAttackBattleEnemy enemy;
}
