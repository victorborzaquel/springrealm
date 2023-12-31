package com.victorborzaquel.springrealm.modules.players.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePlayerByUsernameUseCase {
  private final PlayerRepository playerRepository;

  public void execute(String username) {
    PlayerEntity player = playerRepository.findByUsernameIgnoreCase(username).orElseThrow(PlayerNotFoundException::new);

    playerRepository.delete(player);
  }
}
