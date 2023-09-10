package com.victorborzaquel.springrealm.modules.battles.dto;

import com.victorborzaquel.springrealm.utils.dto.RollDiceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStartBattleEnemy {
  private String name;
  private String slug;
  private ResponseBattleCharacter character;
  private RollDiceDto initiativeDice;
}
