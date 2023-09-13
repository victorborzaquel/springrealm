package com.victorborzaquel.springrealm.modules.logs.usecases;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.battles.exceptions.BattleNotFoundException;
import com.victorborzaquel.springrealm.modules.logs.LogMapper;
import com.victorborzaquel.springrealm.modules.logs.dto.ResponseBattleLogDto;
import com.victorborzaquel.springrealm.modules.turns.TurnEntity;
import com.victorborzaquel.springrealm.modules.turns.TurnRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindBattleLogUseCase {
  private final BattleRepository battleRepository;
  private final TurnRepository turnRepository;

  public ResponseBattleLogDto execute(UUID id, Pageable pageable) {
    BattleEntity battle = battleRepository.findById(id).orElseThrow(BattleNotFoundException::new);

    Page<TurnEntity> turns = turnRepository.findAllByBattle(battle, pageable);

    return LogMapper.toResponseBattleLogDto(battle, turns);
  }
}
