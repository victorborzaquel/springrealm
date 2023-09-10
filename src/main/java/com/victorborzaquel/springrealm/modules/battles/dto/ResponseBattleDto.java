package com.victorborzaquel.springrealm.modules.battles.dto;

import java.util.List;

import com.victorborzaquel.springrealm.modules.turns.dto.ResponseTurnDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleDto {

  private Boolean isPlayerInitiative;

  private ResponseBattleEntity player;
  private ResponseBattleEntity enemy;

  private List<ResponseTurnDto> turns;

}
