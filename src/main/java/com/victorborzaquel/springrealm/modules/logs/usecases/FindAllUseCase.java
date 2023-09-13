package com.victorborzaquel.springrealm.modules.logs.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.battles.BattleRepository;
import com.victorborzaquel.springrealm.modules.logs.LogMapper;
import com.victorborzaquel.springrealm.modules.logs.dto.ResponseBattleHistoryDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllUseCase {
  private final BattleRepository battleRepository;

  public Page<ResponseBattleHistoryDto> execute(Pageable pageable) {
    Page<BattleEntity> battles = battleRepository.findAllByEndedAtNotNull(pageable);

    return LogMapper.toResponseBattleHistoryDto(battles);
  }
}
