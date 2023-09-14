package com.victorborzaquel.springrealm.modules.history.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTurnDto implements Serializable {
  ResponseTurnDice damageCaused;
  ResponseTurnDice attackPower;
  ResponseTurnDice adversaryDefensePower;
}
