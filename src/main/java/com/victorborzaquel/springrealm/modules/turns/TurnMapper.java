package com.victorborzaquel.springrealm.modules.turns;

import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.dices.DiceMapper;

public class TurnMapper {
  private TurnMapper() {
  }

  public static ResponseAttackBattleDto toResponseAttackBattleDto(TurnEntity turn) {
    return ResponseAttackBattleDto.builder()
        .attackDice(DiceMapper.toDto(turn.getAttackDice()))
        .defenseDice(DiceMapper.toDto(turn.getDefenseDice()))
        .attackPower(turn.getAttackPower())
        .build();
  }
}
