package com.victorborzaquel.springrealm.modules.history.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.history.LogMapper;
import com.victorborzaquel.springrealm.modules.history.dto.ResponseBattleHistoryDto;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllByPlayerUsernameUseCase {
  private final BattleRepository battleRepository;
  private final PlayerRepository playerRepository;

  public Page<ResponseBattleHistoryDto> execute(String username, Pageable pageable) {
    PlayerEntity player = playerRepository.findByUsernameIgnoreCase(username).orElseThrow(PlayerNotFoundException::new);

    Page<BattleEntity> battles = battleRepository.findAllByPlayerAndEndedAtNotNull(player, pageable);

    return LogMapper.toResponseBattleHistoryDto(battles);
  }
}
