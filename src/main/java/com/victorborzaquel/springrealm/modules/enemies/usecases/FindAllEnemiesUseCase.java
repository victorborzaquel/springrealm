package com.victorborzaquel.springrealm.modules.enemies.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.enemies.EnemyMapper;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllEnemiesUseCase {
  private final EnemyRepository enemyRepository;

  public Page<ResponseEnemyDto> execute(Pageable pageable) {
    Page<Enemy> characters = enemyRepository.findAll(pageable);

    return EnemyMapper.INSTANCE.toDto(characters);
  }
}
