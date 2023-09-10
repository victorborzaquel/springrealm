package com.victorborzaquel.springrealm.config.dataloader.usecases;

import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.usecases.CreateEnemyUseCase;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.usecases.CreatePlayerUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreatePlayersDataUseCase {

  private final CreatePlayerUseCase createPlayerUseCase;

  public void execute() {
    warrior();
  }

  private void warrior() {
    CreatePlayerDto dto = CreatePlayerDto.builder()
        .characterName("warrior")
        .firstName("Victor")
        .lastName("Borzaquel")
        .username("victorborzaquel")
        .build();

    createPlayerUseCase.execute(dto);
  }

}
