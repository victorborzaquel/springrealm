package com.victorborzaquel.springrealm.modules.battles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTurnDto {
  ResponseTurnDice damageCaused;
  ResponseTurnDice attackPower;
  ResponseTurnDice adversaryDefensePower;
}
