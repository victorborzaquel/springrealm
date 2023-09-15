package com.victorborzaquel.springrealm.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.dices.dto.DiceDto;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;
import com.victorborzaquel.springrealm.modules.dices.usecases.RollDiceUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("dices")
public class DiceController {
  private final RollDiceUseCase rollDiceUseCase;

  @PostMapping("roll")
  public DiceDto roll(@Valid @RequestBody RollDiceDto dto) {
    return rollDiceUseCase.execute(dto);
  }
} 
