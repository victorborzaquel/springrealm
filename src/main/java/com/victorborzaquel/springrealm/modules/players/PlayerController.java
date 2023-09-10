package com.victorborzaquel.springrealm.modules.players;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.UpdatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.usecases.CreatePlayerUseCase;
import com.victorborzaquel.springrealm.modules.players.usecases.FindAllPlayersUseCase;
import com.victorborzaquel.springrealm.modules.players.usecases.FindOnePlayerByUsernameUseCase;
import com.victorborzaquel.springrealm.modules.players.usecases.FindOnePlayerUseCase;
import com.victorborzaquel.springrealm.modules.players.usecases.UpdatePlayerUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {
  private final FindOnePlayerUseCase findOnePlayerUseCase;
  private final FindOnePlayerByUsernameUseCase findOnePlayerByUsernameUseCase;
  private final FindAllPlayersUseCase findAllPlayersUseCase;
  private final CreatePlayerUseCase createPlayerUseCase;
  private final UpdatePlayerUseCase updatePlayerUseCase;

  @GetMapping("{id}")
  public ResponsePlayerDto findOne(@PathVariable UUID id) {
    return findOnePlayerUseCase.execute(id);
  }

  @GetMapping("username/{username}")
  public ResponsePlayerDto findOneByUsername(@PathVariable String username) {
    return findOnePlayerByUsernameUseCase.execute(username);
  }

  @GetMapping
  public Page<ResponsePlayerDto> findAll(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "username") String sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findAllPlayersUseCase.execute(pageable);
  }

  @PostMapping
  public ResponsePlayerDto create(@Valid @RequestBody CreatePlayerDto dto) {
    return createPlayerUseCase.execute(dto);
  }

  @PutMapping("{id}")
  public ResponsePlayerDto update(@PathVariable UUID id, @Valid @RequestBody UpdatePlayerDto dto) {
    return updatePlayerUseCase.execute(id, dto);
  }

}
