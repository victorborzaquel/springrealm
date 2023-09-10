package com.victorborzaquel.springrealm.modules.enemies.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.enemies.EnemyMapper;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindOneEnemyUseCase {
  private final EnemyRepository EnemyRepository;

  public ResponseEnemyDto execute(UUID id) {
    Enemy enemy = EnemyRepository.findById(id).orElseThrow(EnemyNotFoundException::new);

    return EnemyMapper.INSTANCE.toDto(enemy);
  }
}
