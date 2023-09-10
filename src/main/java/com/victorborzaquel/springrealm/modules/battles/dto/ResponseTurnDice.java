package com.victorborzaquel.springrealm.modules.battles.dto;

import com.victorborzaquel.springrealm.utils.dto.RollDiceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTurnDice {
  private Integer total;
  private RollDiceDto rollDice;
}
