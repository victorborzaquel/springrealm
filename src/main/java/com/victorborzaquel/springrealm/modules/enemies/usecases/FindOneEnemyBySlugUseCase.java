package com.victorborzaquel.springrealm.modules.enemies.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyMapper;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindOneEnemyBySlugUseCase {
  private final EnemyRepository playerRepository;

  public ResponseEnemyDto execute(String slug) {
    EnemyEntity enemy = playerRepository.findBySlugIgnoreCase(slug).orElseThrow(EnemyNotFoundException::new);

    return EnemyMapper.toDto(enemy);
  }
}
