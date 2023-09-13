package com.victorborzaquel.springrealm.config.dataloader.usecases;

import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.usecases.CreateEnemyUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateEnemiesDataUseCase {

  private final CreateEnemyUseCase createEnemyUseCase;

  public void execute() {
    orc();
    giant();
    werewolf();
  }

  private void orc() {
    CreateEnemyDto dto = CreateEnemyDto.builder()
        .firstName("Grugor")
        .lastName("Skullcrusher")
        .characterSlug("orc")
        .slug("grugorskullcrusher")
        .build();

    createEnemyUseCase.execute(dto);
  }

  private void giant() {
    CreateEnemyDto dto = CreateEnemyDto.builder()
        .firstName("Thundar")
        .lastName("Rockcrusher")
        .characterSlug("giant")
        .slug("thundarrockcrusher")
        .build();

    createEnemyUseCase.execute(dto);
  }

  private void werewolf() {
    CreateEnemyDto dto = CreateEnemyDto.builder()
        .slug("fenrirbloodmoon")
        .firstName("Fenrir")
        .lastName("Bloodmoon")
        .characterSlug("werewolf")
        .build();

    createEnemyUseCase.execute(dto);
  }
}
