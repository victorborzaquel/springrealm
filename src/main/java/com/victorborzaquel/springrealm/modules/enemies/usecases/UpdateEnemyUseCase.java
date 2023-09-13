package com.victorborzaquel.springrealm.modules.enemies.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyMapper;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyCharacterIsNotMonsterException;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateEnemyUseCase {
  private final EnemyRepository enemyRepository;
  private final CharacterRepository characterRepository;

  public ResponseEnemyDto execute(UUID id, UpdateEnemyDto dto) {
    enemyRepository.findById(id).orElseThrow(EnemyNotFoundException::new);
    validate(id, dto);

    CharacterEntity character = characterRepository.findBySlugIgnoreCase(dto.getCharacterSlug())
        .orElseThrow(CharacterNotFoundException::new);

    validateCharacter(character);

    EnemyEntity enemy = EnemyMapper.toEntity(id, dto, character);

    enemyRepository.save(enemy);

    return EnemyMapper.toDto(enemy);
  }

  private void validateCharacter(CharacterEntity character) {
    if (!character.getType().equals(CharacterType.MONSTER)) {
      throw new EnemyCharacterIsNotMonsterException();
    }
  }

  private void validate(UUID id, UpdateEnemyDto dto) {
    List<String> errors = new ArrayList<>();

    if (enemyRepository.existsBySlugAndIdNot(dto.getSlug(), id)) {
      errors.add("slug already exists");
    }

    if (!errors.isEmpty()) {
      throw new EnemyAlreadyExistsException(errors);
    }
  }
}
