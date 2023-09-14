package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.StartRandomBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StartRandomBattleUseCase {

  private final BattleRepository battleRepository;
  private final EnemyRepository enemyRepository;
  private final PlayerRepository playerRepository;

  @CacheEvict(key = "#dto.playerUsername", value = "currentBattle")
  public ResponseStartBattleDto execute(StartRandomBattleDto dto) {
    EnemyEntity enemy = enemyRepository.findRandom().orElseThrow(EnemyNotFoundException::new);
    PlayerEntity player = playerRepository.findByUsernameIgnoreCase(dto.getPlayerUsername())
        .orElseThrow(PlayerNotFoundException::new);

    if (battleRepository.existsByPlayerUsernameAndEndedAtNull(player.getUsername())) {
      throw new PlayerAlreadyInBattleException();
    }

    BattleEntity battle = BattleMapper.toEntity(player, enemy);

    battleRepository.save(battle);

    return BattleMapper.toStartBattleDto(battle);
  }
}
