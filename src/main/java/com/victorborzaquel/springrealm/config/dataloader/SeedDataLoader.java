package com.victorborzaquel.springrealm.config.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.config.dataloader.usecases.CreateCharactersDataUseCase;
import com.victorborzaquel.springrealm.config.dataloader.usecases.CreateEnemiesDataUseCase;
import com.victorborzaquel.springrealm.config.dataloader.usecases.CreatePlayersDataUseCase;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class SeedDataLoader implements ApplicationRunner {
  private final CreateCharactersDataUseCase createCharactersDataUseCase;
  private final CreateEnemiesDataUseCase createEnemiesDataUseCase;
  private final CreatePlayersDataUseCase createPlayersDataUseCase;

  private final CharacterRepository characterRepository;
  private final EnemyRepository enemyRepository;
  private final PlayerRepository playerRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (characterRepository.count() == 0) {
      createCharactersDataUseCase.execute();
    }
    if (enemyRepository.count() == 0) {
      createEnemiesDataUseCase.execute();
    }
    if (playerRepository.count() == 0) {
      createPlayersDataUseCase.execute();
    }
  }
}
