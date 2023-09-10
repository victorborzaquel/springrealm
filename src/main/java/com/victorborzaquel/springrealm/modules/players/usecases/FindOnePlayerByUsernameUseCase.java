package com.victorborzaquel.springrealm.modules.players.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerMapper;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindOnePlayerByUsernameUseCase {
  private final PlayerRepository playerRepository;

  public ResponsePlayerDto execute(String username) {
    Player player = playerRepository.findByUsernameIgnoreCase(username).orElseThrow(PlayerNotFoundException::new);

    return PlayerMapper.INSTANCE.toDto(player);
  }
}
