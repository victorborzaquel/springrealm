package com.victorborzaquel.springrealm.api;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.logs.dto.ResponseBattleHistoryDto;
import com.victorborzaquel.springrealm.modules.logs.dto.ResponseBattleLogDto;
import com.victorborzaquel.springrealm.modules.logs.usecases.FindAllByPlayerUsernameUseCase;
import com.victorborzaquel.springrealm.modules.logs.usecases.FindAllUseCase;
import com.victorborzaquel.springrealm.modules.logs.usecases.FindBattleLogUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("logs")
public class LogController {
  private final FindAllUseCase findAllUseCase;
  private final FindAllByPlayerUsernameUseCase findAllByPlayerUsernameUseCase;
  private final FindBattleLogUseCase findBattleLogUseCase;

  @GetMapping
  public Page<ResponseBattleHistoryDto> findAll(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "endedAt") String sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findAllUseCase.execute(pageable);
  }

  @GetMapping("{id}")
  public ResponseBattleLogDto findBattleLog(
      @PathVariable UUID id,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "number") String sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findBattleLogUseCase.execute(id, pageable);
  }

  @GetMapping("player/{username}")
  public Page<ResponseBattleHistoryDto> findAllByPlayerUsername(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "createdAt") String sort,
      @RequestParam(defaultValue = "DESC") Sort.Direction direction,
      @PathVariable String username) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findAllByPlayerUsernameUseCase.execute(username, pageable);
  }
}
