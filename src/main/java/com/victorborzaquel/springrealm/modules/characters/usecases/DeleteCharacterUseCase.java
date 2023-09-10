package com.victorborzaquel.springrealm.modules.characters.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCharacterUseCase {
  private final CharacterRepository characterRepository;

  public final void execute(UUID id) {
    characterRepository.findById(id).orElseThrow(CharacterNotFoundException::new);

    characterRepository.deleteById(id);
  }
}
