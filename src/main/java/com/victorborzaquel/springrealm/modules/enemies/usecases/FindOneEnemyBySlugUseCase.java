package com.victorborzaquel.springrealm.modules.enemies.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.enemies.Enemy;
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
    Enemy enemy = playerRepository.findBySlugIgnoreCase(slug).orElseThrow(EnemyNotFoundException::new);

    return EnemyMapper.INSTANCE.toDto(enemy);
  }
}
