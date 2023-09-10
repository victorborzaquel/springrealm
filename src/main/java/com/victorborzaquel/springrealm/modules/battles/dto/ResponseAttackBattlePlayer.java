package com.victorborzaquel.springrealm.modules.battles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAttackBattlePlayer {
  private String name;
  private String username;
  private ResponseBattleCharacter character;
  private ResponseTurnDto turn;
}
