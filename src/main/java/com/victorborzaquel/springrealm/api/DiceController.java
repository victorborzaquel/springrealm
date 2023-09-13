package com.victorborzaquel.springrealm.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;
import com.victorborzaquel.springrealm.modules.dices.usecases.RollDiceUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("dices")
public class DiceController {
  private final RollDiceUseCase rollDiceUseCase;

  @GetMapping("roll")
  public RollDiceDto get(
      @RequestParam("quantityDices") Integer quantityDices,
      @RequestParam("quantityFaces") Integer quantityFaces) {
    return rollDiceUseCase.execute(quantityDices, quantityFaces);
  }
}
