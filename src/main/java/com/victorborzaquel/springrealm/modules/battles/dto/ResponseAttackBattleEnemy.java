package com.victorborzaquel.springrealm.modules.battles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAttackBattleEnemy {
  private String name;
  private String slug;
  private ResponseBattleCharacter character;
  private ResponseTurnDto turn;
}
