package com.victorborzaquel.springrealm.modules.dices.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDiceDto implements Serializable {
  private String name;
  private Integer result;
  private List<Integer> moves;
}
