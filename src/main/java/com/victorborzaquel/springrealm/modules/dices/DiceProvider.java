package com.victorborzaquel.springrealm.modules.dices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.dices.dto.DiceDto;
import com.victorborzaquel.springrealm.shared.exceptions.base.ConflictException;

@Component
public class DiceProvider {
  public DiceDto rollDice(Integer quantityDices, Integer faces) {
    if (quantityDices == null || faces == null) {
      throw new ConflictException("Quantity dices and faces are required");
    }

    if (quantityDices < 1 || quantityDices > 5) {
      throw new ConflictException("Quantity dices must be between 1 and 5");
    }

    if (faces < 3 || faces > 25) {
      throw new ConflictException("Quantity faces must be between 3 and 25");
    }

    Random random = new Random();
    Integer result = 0;
    List<Integer> moves = new ArrayList<>();

    for (int i = 0; i < quantityDices; i++) {
      Integer move = random.nextInt(faces) + 1;

      moves.add(move);
      result += move;
    }

    return DiceDto.builder()
        .result(result)
        .moves(moves)
        .name(getDiceName(quantityDices, faces))
        .build();
  }

  public DiceDto rollTurnDice() {
    return rollDice(1, 12);
  }

  public DiceDto rollDamageDice(BattleCharacterEntity character) {
    return rollDice(character.getQuantityDices(), character.getQuantityFaces());
  }

  public DiceDto rollInitiativeDice() {
    return rollDice(1, 20);
  }

  public static String getDiceName(Integer quantityDices, Integer quantityFaces) {
    return String.format("%dd%d", quantityDices, quantityFaces);
  }
}
