package com.victorborzaquel.springrealm.config.dataloader.usecases;

import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
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
        .name("Grugor Skullcrusher")
        .characterName("orc")
        .slug("grugorskullcrusher")
        .build();

    createEnemyUseCase.execute(dto);
  }

  private void giant() {
    CreateEnemyDto dto = CreateEnemyDto.builder()
        .name("Thundar Rockcrusher")
        .characterName("giant")
        .slug("thundarrockcrusher")
        .build();

    createEnemyUseCase.execute(dto);
  }

  private void werewolf() {
    CreateEnemyDto dto = CreateEnemyDto.builder()
        .name("Fenrir Bloodmoon")
        .slug("fenrirbloodmoon")
        .characterName("werewolf")
        .build();

    createEnemyUseCase.execute(dto);
  }
}
