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
public class ResponsePlayerBattleDto implements Serializable {
  private String name;
  private String username;
  private ResponseBattleCharacterDto character;
}
