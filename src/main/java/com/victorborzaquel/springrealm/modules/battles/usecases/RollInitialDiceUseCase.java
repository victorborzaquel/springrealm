package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseRollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.dto.RollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.NotAtThatStageException;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.DiceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RollInitialDiceUseCase {

  private final DiceProvider diceProvider;
  private final BattleRepository battleRepository;

  @CacheEvict(key = "#dto.playerUsername", value = "currentBattle")
  public ResponseRollInitialDiceDto execute(RollInitialDiceDto dto) {
    BattleEntity battle = this.battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    if (!battle.isInitialRoll()) {
      throw new NotAtThatStageException(battle);
    }

    DiceDto playerDice = diceProvider.rollInitiativeDice();
    DiceDto enemyDice = diceProvider.rollInitiativeDice();

    Boolean isPlayerInitiative = null;
    Boolean isDraw = playerDice.getResult().equals(enemyDice.getResult());

    if (!isDraw) {
      isPlayerInitiative = playerDice.getResult() > enemyDice.getResult();

      battle.setIsPlayerInitiative(isPlayerInitiative);

      if (isPlayerInitiative) {
        battle.setPlayerTurn();
      } else {
        battle.setEnemyTurn();
      }

      battleRepository.save(battle);
    }

    return BattleMapper.toRollInitialDiceDto(isPlayerInitiative, playerDice, enemyDice);
  }
}
