package com.victorborzaquel.springrealm.modules.characters.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCharacterUseCase {
  private final CharacterRepository characterRepository;

  public ResponseCharacterDto execute(CreateCharacterDto dto) {
    validate(dto);

    Character character = CharacterMapper.INSTANCE.toEntity(dto);

    characterRepository.save(character);

    return CharacterMapper.INSTANCE.toDto(character);
  }

  private void validate(CreateCharacterDto dto) {
    List<String> errors = new ArrayList<>();

    if (characterRepository.existsByName(dto.getName())) {
      errors.add("name already exists");
    }

    if (!errors.isEmpty()) {
      throw new CharacterAlreadyExistsException(errors);
    }
  }
}