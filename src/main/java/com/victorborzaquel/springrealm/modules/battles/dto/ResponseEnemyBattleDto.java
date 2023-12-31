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
public class ResponseEnemyBattleDto implements Serializable {
  private String name;
  private String slug;
  private ResponseBattleCharacterDto character;
}
