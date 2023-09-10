package com.victorborzaquel.springrealm.config.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.config.dataloader.usecases.CreateCharactersDataUseCase;
import com.victorborzaquel.springrealm.config.dataloader.usecases.CreateEnemiesDataUseCase;
import com.victorborzaquel.springrealm.config.dataloader.usecases.CreatePlayersDataUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeedDataLoader implements ApplicationRunner {

  private final CreateCharactersDataUseCase createCharactersDataUseCase;
  private final CreateEnemiesDataUseCase createEnemiesDataUseCase;
  private final CreatePlayersDataUseCase createPlayersDataUseCase;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    createCharactersDataUseCase.execute();
    createEnemiesDataUseCase.execute();
    createPlayersDataUseCase.execute();
  }

}
