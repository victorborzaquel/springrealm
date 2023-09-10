package com.victorborzaquel.springrealm.utils.dices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RollDicesDto {
  private Integer result;
  private String dice;
  private List<Integer> moves;
}
