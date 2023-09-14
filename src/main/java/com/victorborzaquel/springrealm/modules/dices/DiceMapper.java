package com.victorborzaquel.springrealm.modules.dices;

import com.victorborzaquel.springrealm.modules.dices.dto.ResponseDiceDto;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

public class DiceMapper {
  private DiceMapper() {
  }

  public static DiceEntity toEntity(RollDiceDto dto) {
    if (dto == null) {
      return null;
    }

    return DiceEntity.builder()
        .name(dto.getName())
        .result(dto.getResult())
        .moves(dto.getMoves())
        .build();
  }

  public static ResponseDiceDto toDto(DiceEntity dice) {
    if (dice == null) {
      return null;
    }

    return ResponseDiceDto.builder()
        .name(dice.getName())
        .result(dice.getResult())
        .moves(dice.getMoves())
        .build();
  }
}
