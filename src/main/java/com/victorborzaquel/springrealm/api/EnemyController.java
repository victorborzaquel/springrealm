package com.victorborzaquel.springrealm.api;

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

import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.ResponseEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.usecases.CreateEnemyUseCase;
import com.victorborzaquel.springrealm.modules.enemies.usecases.FindAllEnemiesUseCase;
import com.victorborzaquel.springrealm.modules.enemies.usecases.FindOneEnemyBySlugUseCase;
import com.victorborzaquel.springrealm.modules.enemies.usecases.FindOneEnemyUseCase;
import com.victorborzaquel.springrealm.modules.enemies.usecases.UpdateEnemyBySlugUseCase;
import com.victorborzaquel.springrealm.modules.enemies.usecases.UpdateEnemyUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("enemies")
public class EnemyController {
  private final CreateEnemyUseCase createEnemyUseCase;
  private final FindOneEnemyUseCase findOneEnemyUseCase;
  private final FindOneEnemyBySlugUseCase findOneEnemyBySlugUseCase;
  private final FindAllEnemiesUseCase findAllEnemiesUseCase;
  private final UpdateEnemyUseCase updateEnemyUseCase;
  private final UpdateEnemyBySlugUseCase updateEnemyBySlugUseCase;

  @PostMapping
  public ResponseEnemyDto create(@Valid @RequestBody CreateEnemyDto dto) {
    return createEnemyUseCase.execute(dto);
  }

  @GetMapping("{id}")
  public ResponseEnemyDto findOne(@PathVariable UUID id) {
    return findOneEnemyUseCase.execute(id);
  }

  @GetMapping("slug/{slug}")
  public ResponseEnemyDto findOneBySlug(@PathVariable String slug) {
    return findOneEnemyBySlugUseCase.execute(slug);
  }

  @GetMapping
  public Page<ResponseEnemyDto> findAll(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "slug") String sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findAllEnemiesUseCase.execute(pageable);
  }

  @PutMapping("{id}")
  public ResponseEnemyDto update(@PathVariable UUID id, @Valid @RequestBody UpdateEnemyDto dto) {
    return updateEnemyUseCase.execute(id, dto);
  }

  @PutMapping("slug/{slug}")
  public ResponseEnemyDto updateBySlug(@PathVariable String slug, @Valid @RequestBody UpdateEnemyDto dto) {
    return updateEnemyBySlugUseCase.execute(slug, dto);
  }
}
