package com.victorborzaquel.springrealm.modules.battles.dto;

import java.io.Serializable;

import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRollInitialDiceDto implements Serializable {
  private Boolean isDraw;
  private Boolean isPlayerInitiative;
  private RollDiceDto playerDice;
  private RollDiceDto enemyDice;
}
