package com.victorborzaquel.springrealm.utils.dices;

import java.util.ArrayList;
import java.util.List;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

public class DiceUtil {

  public static RollDicesDto rollDice(Integer quantityDices, Integer faces) {
    Integer result = 0;
    List<Integer> moves = new ArrayList<>();

    for (int i = 0; i < quantityDices; i++) {
      Integer move = (int) (Math.random() * faces) + 1;

      moves.add(move);
      result += move;
    }

    return RollDicesDto.builder()
        .result(result)
        .moves(moves)
        .dice(String.format("%dd%d", quantityDices, faces))
        .build();
  }

  public static RollDicesDto rollTurnDice() {
    return rollDice(1, 12);
  }

  public static RollDicesDto rollDamageDice(Character character) {
    return rollDice(character.getQuantityDices(), character.getQuantityFaces());
  }

  public static RollDicesDto rollInitiativeDice() {
    return rollDice(1, 20);
  }

}
