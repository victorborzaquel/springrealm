package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseRollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.dto.RollInitialDiceDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.NotAtThatStageException;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;
import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RollInitialDiceUseCase {

  private final DiceProvider diceProvider;
  private final BattleRepository battleRepository;

  public ResponseRollInitialDiceDto execute(RollInitialDiceDto dto) {
    BattleEntity battle = this.battleRepository.findByPlayerUsernameAndEndedAtNull(dto.getPlayerUsername())
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    if (!battle.isInitialRoll()) {
      throw new NotAtThatStageException(battle);
    }

    RollDiceDto playerDice = diceProvider.rollInitiativeDice();
    RollDiceDto enemyDice = diceProvider.rollInitiativeDice();

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
