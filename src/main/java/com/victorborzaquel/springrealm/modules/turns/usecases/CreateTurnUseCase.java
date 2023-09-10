package com.victorborzaquel.springrealm.modules.turns.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerMapper;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;
import com.victorborzaquel.springrealm.modules.turns.dto.CreateTurnDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTurnUseCase {
  private final TurnRepository turnRepository;

  public ResponsePlayerDto execute(CreateTurnDto dto) {
    
    validate(dto);

    Character character = characterRepository.findByNameIgnoreCase(dto.getCharacterName())
        .orElseThrow(CharacterNotFoundException::new);

    Player player = PlayerMapper.INSTANCE.toEntity(dto, character);

    playerRepository.save(player);

    return PlayerMapper.INSTANCE.toDto(player);
  }

  private void validate(CreateTurnDto dto) {
    List<String> errors = new ArrayList<>();

    if (playerRepository.existsByUsername(dto.getUsername())) {
      errors.add("username already exists");
    }

    if (!errors.isEmpty()) {
      throw new PlayerAlreadyExistsException(errors);
    }
  }
}
