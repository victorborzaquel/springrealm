package com.victorborzaquel.springrealm.modules.dices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RollDiceDto {
  private String name;
  private Integer result;
  private List<Integer> moves;
}
