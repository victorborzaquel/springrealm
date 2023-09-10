package com.victorborzaquel.springrealm.utils;

import java.util.ArrayList;
import java.util.List;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacter;
import com.victorborzaquel.springrealm.utils.dto.RollDiceDto;

public class DiceUtil {

  public static RollDiceDto rollDice(Integer quantityDices, Integer faces) {
    Integer result = 0;
    List<Integer> moves = new ArrayList<>();

    for (int i = 0; i < quantityDices; i++) {
      Integer move = (int) (Math.random() * faces) + 1;

      moves.add(move);
      result += move;
    }

    return RollDiceDto.builder()
        .result(result)
        .moves(moves)
        .name(getDiceName(quantityDices, faces))
        .build();
  }

  public static RollDiceDto rollTurnDice() {
    return rollDice(1, 12);
  }

  public static RollDiceDto rollDamageDice(BattleCharacter character) {
    return rollDice(character.getQuantityDices(), character.getQuantityFaces());
  }

  public static RollDiceDto rollInitiativeDice() {
    return rollDice(1, 20);
  }

  public static String getDiceName(Integer quantityDices, Integer quantityFaces) {
    return String.format("%dd%d", quantityDices, quantityFaces);
  }
}
