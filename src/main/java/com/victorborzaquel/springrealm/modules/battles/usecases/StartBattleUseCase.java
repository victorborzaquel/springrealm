package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseStartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.dto.StartBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.exceptions.EnemyNotFoundException;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;
import com.victorborzaquel.springrealm.utils.dices.DiceUtil;
import com.victorborzaquel.springrealm.utils.dices.dto.RollDicesDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StartBattleUseCase {

  private final BattleRepository battleRepository;
  private final EnemyRepository enemyRepository;
  private final PlayerRepository playerRepository;

  private Boolean isPlayerStartBattle = null;
  private RollDicesDto playerRollDices = null;
  private RollDicesDto enemyRollDices = null;

  public ResponseStartBattleDto execute(StartBattleDto dto) {
    Enemy enemy = enemyRepository.findBySlugIgnoreCase(dto.getEnemySlug()).orElseThrow(EnemyNotFoundException::new);
    Player player = playerRepository.findByUsernameIgnoreCase(dto.getPlayerUsername())
        .orElseThrow(PlayerNotFoundException::new);

    if (battleRepository.existsByPlayerAndInProgressTrue(player)) {
      throw new PlayerAlreadyInBattleException();
    }

    rollDices();

    Battle battle = BattleMapper.INSTANCE.toEntity(enemy, player, isPlayerStartBattle);

    battleRepository.save(battle);

    return BattleMapper.INSTANCE.toDto(battle, playerRollDices, enemyRollDices);
  }

  private void rollDices() {
    while (isPlayerStartBattle == null) {
      playerRollDices = DiceUtil.rollInitiativeDice();
      enemyRollDices = DiceUtil.rollInitiativeDice();

      if (playerRollDices.getResult() == enemyRollDices.getResult()) {
        continue;
      }

      isPlayerStartBattle = playerRollDices.getResult() > enemyRollDices.getResult();
    }
  }
}
