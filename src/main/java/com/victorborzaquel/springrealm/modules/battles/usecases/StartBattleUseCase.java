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
import com.victorborzaquel.springrealm.utils.DiceUtil;
import com.victorborzaquel.springrealm.utils.dto.RollDiceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StartBattleUseCase {

  private final BattleRepository battleRepository;
  private final EnemyRepository enemyRepository;
  private final PlayerRepository playerRepository;

  private Boolean isPlayerStartBattle = null;
  private RollDiceDto playerRollDice = null;
  private RollDiceDto enemyRollDice = null;

  public ResponseStartBattleDto execute(StartBattleDto dto) {
    Enemy enemy = selectEnemy(dto.getEnemySlug());
    Player player = playerRepository.findByUsernameIgnoreCase(dto.getPlayerUsername())
        .orElseThrow(PlayerNotFoundException::new);

    if (battleRepository.existsByPlayerAndInProgressTrue(player)) {
      throw new PlayerAlreadyInBattleException();
    }

    rollDices();

    Battle battle = BattleMapper.INSTANCE.toEntity(enemy, player, isPlayerStartBattle);

    battleRepository.save(battle);

    return BattleMapper.INSTANCE.toDto(battle, playerRollDice, enemyRollDice);
  }

  private Enemy selectEnemy(String enemySlug) {
    if (enemySlug == null) {
      return enemyRepository.findRandom().orElseThrow(EnemyNotFoundException::new);
    }

    return enemyRepository.findBySlugIgnoreCase(enemySlug).orElseThrow(EnemyNotFoundException::new);
  }

  private void rollDices() {
    while (isPlayerStartBattle == null) {
      playerRollDice = DiceUtil.rollInitiativeDice();
      enemyRollDice = DiceUtil.rollInitiativeDice();

      if (playerRollDice.getResult() == enemyRollDice.getResult()) {
        continue;
      }

      isPlayerStartBattle = playerRollDice.getResult() > enemyRollDice.getResult();
    }
  }
}
