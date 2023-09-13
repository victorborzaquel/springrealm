package com.victorborzaquel.springrealm.modules.players.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerMapper;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePlayerUseCase {
  private final PlayerRepository playerRepository;
  private final CharacterRepository characterRepository;

  public ResponsePlayerDto execute(CreatePlayerDto dto) {
    validate(dto);

    CharacterEntity character = characterRepository.findBySlugIgnoreCase(dto.getCharacterSlug())
        .orElseThrow(CharacterNotFoundException::new);

    PlayerEntity player = PlayerMapper.toEntity(dto, character);

    playerRepository.save(player);

    return PlayerMapper.toDto(player);
  }

  private void validate(CreatePlayerDto dto) {
    List<String> errors = new ArrayList<>();

    if (playerRepository.existsByUsername(dto.getUsername())) {
      errors.add("username already exists");
    }

    if (!errors.isEmpty()) {
      throw new PlayerAlreadyExistsException(errors);
    }
  }
}
