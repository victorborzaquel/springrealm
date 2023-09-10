package com.victorborzaquel.springrealm.modules.characters.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.UpdateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCharacterUseCase {
  private final CharacterRepository characterRepository;

  public ResponseCharacterDto execute(UUID id, UpdateCharacterDto dto) {
    characterRepository.findById(id).orElseThrow(CharacterNotFoundException::new);
    validate(id, dto);

    Character character = CharacterMapper.INSTANCE.toEntity(id, dto);

    characterRepository.save(character);

    return CharacterMapper.INSTANCE.toDto(character);
  }

  private void validate(UUID id, UpdateCharacterDto dto) {
    List<String> errors = new ArrayList<>();

    if (characterRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), id)) {
      errors.add("name already exists");
    }

    if (errors.size() > 0) {
      throw new CharacterAlreadyExistsException(errors);
    }
  }
}
