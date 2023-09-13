package com.victorborzaquel.springrealm.modules.characters.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllCharactersUseCase {
  private final CharacterRepository characterRepository;

  public Page<ResponseCharacterDto> execute(Pageable pageable) {
    Page<CharacterEntity> characters = characterRepository.findAll(pageable);

    return CharacterMapper.toDto(characters);
  }
}
