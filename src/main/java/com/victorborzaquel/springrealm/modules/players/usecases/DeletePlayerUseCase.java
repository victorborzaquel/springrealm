package com.victorborzaquel.springrealm.modules.players.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePlayerUseCase {
  private final PlayerRepository playerRepository;

  public void execute(UUID id) {
    PlayerEntity player = playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);

    playerRepository.delete(player);
  }
}
