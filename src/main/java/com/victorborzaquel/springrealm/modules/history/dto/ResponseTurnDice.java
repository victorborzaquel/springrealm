package com.victorborzaquel.springrealm.modules.history.dto;

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
public class ResponseTurnDice implements Serializable {
  private Integer total;
  private RollDiceDto rollDice;
}
