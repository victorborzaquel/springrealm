package com.victorborzaquel.springrealm.modules.history.dto;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleLogDto implements Serializable {
  private ResponseBattleHistoryDto battle;
  private Page<ResponseTurnLogDto> turns;
}
