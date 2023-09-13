package com.victorborzaquel.springrealm.modules.dices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

@Component
public class DiceProvider {

  public RollDiceDto rollDice(Integer quantityDices, Integer faces) {
    Random random = new Random();
    Integer result = 0;
    List<Integer> moves = new ArrayList<>();

    for (int i = 0; i < quantityDices; i++) {
      Integer move = random.nextInt(faces) + 1;

      moves.add(move);
      result += move;
    }

    return RollDiceDto.builder()
        .result(result)
        .moves(moves)
        .name(getDiceName(quantityDices, faces))
        .build();
  }

  public RollDiceDto rollTurnDice() {
    return rollDice(1, 12);
  }

  public RollDiceDto rollDamageDice(BattleCharacterEntity character) {
    return rollDice(character.getQuantityDices(), character.getQuantityFaces());
  }

  public RollDiceDto rollInitiativeDice() {
    return rollDice(1, 20);
  }

  public static String getDiceName(Integer quantityDices, Integer quantityFaces) {
    return String.format("%dd%d", quantityDices, quantityFaces);
  }
}
