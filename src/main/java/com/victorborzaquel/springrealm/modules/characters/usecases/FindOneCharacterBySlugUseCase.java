package com.victorborzaquel.springrealm.modules.characters.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.Character;
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
    Character character = characterRepository.findBySlugIgnoreCase(slug).orElseThrow(CharacterNotFoundException::new);

    return CharacterMapper.INSTANCE.toDto(character);
  }
}
