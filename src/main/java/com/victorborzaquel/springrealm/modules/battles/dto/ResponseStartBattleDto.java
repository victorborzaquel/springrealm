package com.victorborzaquel.springrealm.modules.battles.dto;

import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStartBattleDto {

  private Boolean isPlayerInitiative;
  private Integer playerPV;
  private Integer enemyPV;
  
  private ResponsePlayerDto player;
  private ResponseEnemyDto enemy;

  private RollDicesDto playerRollDices;
  private RollDicesDto enemyRollDices;

}
