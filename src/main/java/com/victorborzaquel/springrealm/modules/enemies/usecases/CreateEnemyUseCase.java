package com.victorborzaquel.springrealm.modules.enemies.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.enemies.EnemyMapper;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyIsNotHeroException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateEnemyUseCase {
  private final EnemyRepository enemyRepository;
  private final CharacterRepository characterRepository;

  public ResponseEnemyDto execute(CreateEnemyDto dto) {
    validate(dto);

    Character character = characterRepository.findByNameIgnoreCase(dto.getCharacterName())
        .orElseThrow(CharacterNotFoundException::new);

    validateCharacter(character);

    Enemy enemy = EnemyMapper.INSTANCE.toEntity(dto, character);

    enemyRepository.save(enemy);

    return EnemyMapper.INSTANCE.toDto(enemy);
  }

  private void validateCharacter(Character character) {
    if (character.getType().equals(CharacterType.HERO)) {
      throw new EnemyIsNotHeroException();
    }
  }

  private void validate(CreateEnemyDto dto) {
    List<String> errors = new ArrayList<>();

    if (enemyRepository.existsByName(dto.getName())) {
      errors.add("name already exists");
    }

    if (enemyRepository.existsBySlug(dto.getSlug())) {
      errors.add("slug already exists");
    }

    if (!errors.isEmpty()) {
      throw new EnemyAlreadyExistsException(errors);
    }
  }
}
