package com.victorborzaquel.springrealm.modules.logs.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleLogDto {
  private ResponseBattleHistoryDto battle;
  private Page<ResponseTurnLogDto> turns;
}
