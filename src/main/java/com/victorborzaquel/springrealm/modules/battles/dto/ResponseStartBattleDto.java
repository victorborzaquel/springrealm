package com.victorborzaquel.springrealm.modules.battles.dto;

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

  private ResponseStartBattleEntity player;
  private ResponseStartBattleEntity enemy;

}
