package com.victorborzaquel.springrealm.modules.battles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleCharacter {

  private String name;
  private Integer life;
  private Integer strength;
  private Integer agility;
  private Integer defense;
  private String dice;

}
