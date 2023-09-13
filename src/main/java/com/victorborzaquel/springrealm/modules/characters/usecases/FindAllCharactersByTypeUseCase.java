package com.victorborzaquel.springrealm.modules.characters.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllCharactersByTypeUseCase {
  private final CharacterRepository characterRepository;

  public Page<ResponseCharacterDto> execute(Pageable pageable, CharacterType type) {
    Page<CharacterEntity> characters = characterRepository.findAllByType(pageable, type);

    return CharacterMapper.toDto(characters);
  }
}
