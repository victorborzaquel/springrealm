package com.victorborzaquel.springrealm.modules.characters.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindOneCharacterUseCase {
  private final CharacterRepository characterRepository;

  public ResponseCharacterDto execute(UUID id) {
    CharacterEntity character = characterRepository.findById(id).orElseThrow(CharacterNotFoundException::new);

    return CharacterMapper.toDto(character);
  }
}
