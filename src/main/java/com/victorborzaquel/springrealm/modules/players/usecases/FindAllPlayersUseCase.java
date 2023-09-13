package com.victorborzaquel.springrealm.modules.players.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerMapper;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllPlayersUseCase {
  private final PlayerRepository playerRepository;

  public Page<ResponsePlayerDto> execute(Pageable pageable) {
    Page<PlayerEntity> characters = playerRepository.findAll(pageable);

    return PlayerMapper.toDto(characters);
  }
}
