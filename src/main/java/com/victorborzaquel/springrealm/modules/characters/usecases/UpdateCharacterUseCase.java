package com.victorborzaquel.springrealm.modules.characters.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
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
    CharacterEntity currentCharacter = characterRepository.findById(id).orElseThrow(CharacterNotFoundException::new);
    validate(id, dto);

    CharacterEntity character = CharacterMapper.toEntity(currentCharacter.getId(), dto, currentCharacter.getType());

    characterRepository.save(character);

    return CharacterMapper.toDto(character);
  }

  private void validate(UUID id, UpdateCharacterDto dto) {
    List<String> errors = new ArrayList<>();

    if (characterRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), id)) {
      errors.add("name already exists");
    }

    if (characterRepository.existsBySlugIgnoreCaseAndIdNot(dto.getSlug(), id)) {
      errors.add("slug already exists");
    }

    if (errors.size() > 0) {
      throw new CharacterAlreadyExistsException(errors);
    }
  }
}
