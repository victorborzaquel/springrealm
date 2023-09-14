package com.victorborzaquel.springrealm.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.battles.dto.AttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.DefenseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseRollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.RollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.dto.StartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.StartRandomBattleDto;
import com.victorborzaquel.springrealm.modules.battles.usecases.AttackBattleUseCase;
import com.victorborzaquel.springrealm.modules.battles.usecases.DefenseBattleUseCase;
import com.victorborzaquel.springrealm.modules.battles.usecases.FindCurrentBattleUseCase;
import com.victorborzaquel.springrealm.modules.battles.usecases.RollInitialDiceUseCase;
import com.victorborzaquel.springrealm.modules.battles.usecases.StartBattleUseCase;
import com.victorborzaquel.springrealm.modules.battles.usecases.StartRandomBattleUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("battles")
public class BattleController {
  private final FindCurrentBattleUseCase findCurrentBattleUseCase;
  private final StartBattleUseCase startBattleUseCase;
  private final StartRandomBattleUseCase startRandomBattleUseCase;
  private final AttackBattleUseCase attackBattleUseCase;
  private final DefenseBattleUseCase defenseBattleUseCase;
  private final RollInitialDiceUseCase rollInitialDiceUseCase;

  @GetMapping("current")
  public ResponseBattleDto findCurrentBattle(@Valid @RequestParam String playerUsername) {
    return findCurrentBattleUseCase.execute(playerUsername);
  }

  @PostMapping("start")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseStartBattleDto start(@Valid @RequestBody StartBattleDto dto) {
    return startBattleUseCase.execute(dto);
  }

  @PostMapping("start/random")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseStartBattleDto startRandom(@Valid @RequestBody StartRandomBattleDto dto) {
    return startRandomBattleUseCase.execute(dto);
  }

  @PostMapping("attack")
  public ResponseAttackBattleDto attack(@Valid @RequestBody AttackBattleDto dto) {
    return attackBattleUseCase.execute(dto);
  }

  @PostMapping("defense")
  public ResponseAttackBattleDto defense(@Valid @RequestBody DefenseBattleDto dto) {
    return defenseBattleUseCase.execute(dto);
  }

  @PostMapping("initial")
  public ResponseRollInitialDiceDto getRollInitialDiceUseCase(@Valid @RequestBody RollInitialDiceDto dto) {
    return rollInitialDiceUseCase.execute(dto);
  }
}
