package com.victorborzaquel.springrealm.modules.battles;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.battles.dto.AttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseAttackBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.StartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.usecases.AttackBattleUseCase;
import com.victorborzaquel.springrealm.modules.battles.usecases.StartBattleUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/battles")
public class BattleController {
  private final StartBattleUseCase startBattleUseCase;
  private final AttackBattleUseCase attackBattleUseCase;

  @PostMapping("start")
  public ResponseStartBattleDto start(@Valid @RequestBody StartBattleDto dto) {
    return startBattleUseCase.execute(dto);
  }

  @PostMapping("attack")
  public ResponseAttackBattleDto attack(@Valid @RequestBody AttackBattleDto dto) {
    return attackBattleUseCase.execute(dto);
  }
}
