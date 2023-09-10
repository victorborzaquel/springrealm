package com.victorborzaquel.springrealm.modules.players.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerMapper;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.UpdatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePlayerUseCase {
  private final PlayerRepository playerRepository;
  private final CharacterRepository characterRepository;

  public ResponsePlayerDto execute(UUID id, UpdatePlayerDto dto) {
    playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
    validate(id, dto);

    Character character = characterRepository.findBySlugIgnoreCase(dto.getCharacterSlug())
        .orElseThrow(CharacterNotFoundException::new);

    Player player = PlayerMapper.INSTANCE.toEntity(id, dto, character);

    playerRepository.save(player);

    return PlayerMapper.INSTANCE.toDto(player);
  }

  private void validate(UUID id, UpdatePlayerDto dto) {
    List<String> errors = new ArrayList<>();

    if (playerRepository.existsByUsernameAndIdNot(dto.getUsername(), id)) {
      errors.add("username already exists");
    }

    if (!errors.isEmpty()) {
      throw new PlayerAlreadyExistsException(errors);
    }
  }
}
