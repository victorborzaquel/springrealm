package com.victorborzaquel.springrealm.modules.battles.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleCharacterDto implements Serializable {
  private String name;
  private Integer pv;
  private Integer strength;
  private Integer agility;
  private Integer defense;
  private String dice;
}
