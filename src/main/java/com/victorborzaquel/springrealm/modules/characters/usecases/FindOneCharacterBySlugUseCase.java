package com.victorborzaquel.springrealm.modules.characters.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindOneCharacterBySlugUseCase {
  private final CharacterRepository characterRepository;

  public ResponseCharacterDto execute(String slug) {
    CharacterEntity character = characterRepository.findBySlugIgnoreCase(slug)
        .orElseThrow(CharacterNotFoundException::new);

    return CharacterMapper.toDto(character);
  }
}
