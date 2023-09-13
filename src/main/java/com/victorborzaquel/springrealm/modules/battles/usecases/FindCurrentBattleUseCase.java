package com.victorborzaquel.springrealm.modules.battles.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleMapper;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.dto.ResponseBattleDto;
import com.victorborzaquel.springrealm.modules.battles.exceptions.PlayerNotAlreadyInBattleException;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCurrentBattleUseCase {
  private final PlayerRepository playerRepository;
  private final BattleRepository battleRepository;

  public ResponseBattleDto execute(String playerUsername) {
    PlayerEntity player = playerRepository.findByUsernameIgnoreCase(playerUsername)
        .orElseThrow(PlayerNotFoundException::new);

    BattleEntity battle = battleRepository.findByPlayerAndEndedAtNull(player)
        .orElseThrow(PlayerNotAlreadyInBattleException::new);

    return BattleMapper.toDto(battle);
  }
}
