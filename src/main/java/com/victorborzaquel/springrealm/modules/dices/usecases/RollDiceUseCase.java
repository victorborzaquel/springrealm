package com.victorborzaquel.springrealm.modules.dices.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.DiceDto;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RollDiceUseCase {
  private final DiceProvider diceProvider;

  public DiceDto execute(RollDiceDto dto) {
    return diceProvider.rollDice(dto.getQuantityDices(), dto.getQuantityFaces());
  }
}
