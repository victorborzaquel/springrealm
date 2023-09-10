package com.victorborzaquel.springrealm.modules.characters;

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

import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.UpdateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.usecases.CreateCharacterUseCase;
import com.victorborzaquel.springrealm.modules.characters.usecases.FindAllCharactersByTypeUseCase;
import com.victorborzaquel.springrealm.modules.characters.usecases.FindAllCharactersUseCase;
import com.victorborzaquel.springrealm.modules.characters.usecases.FindOneCharacterBySlugUseCase;
import com.victorborzaquel.springrealm.modules.characters.usecases.FindOneCharacterUseCase;
import com.victorborzaquel.springrealm.modules.characters.usecases.UpdateCharacterBySlugUseCase;
import com.victorborzaquel.springrealm.modules.characters.usecases.UpdateCharacterUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {
  private final FindAllCharactersByTypeUseCase findAllCharactersByTypeUseCase;
  private final FindAllCharactersUseCase findAllCharactersUseCase;
  private final CreateCharacterUseCase createCharacterUseCase;
  private final UpdateCharacterUseCase updateCharacterUseCase;
  private final UpdateCharacterBySlugUseCase updateCharacterBySlugUseCase;
  private final FindOneCharacterUseCase findOneCharacterUseCase;
  private final FindOneCharacterBySlugUseCase findOneCharacterBySlugUseCase;

  @PostMapping
  public ResponseCharacterDto create(@Valid @RequestBody CreateCharacterDto dto) {
    return createCharacterUseCase.execute(dto);
  }

  @GetMapping("{id}")
  public ResponseCharacterDto findOne(@PathVariable UUID id) {
    return findOneCharacterUseCase.execute(id);
  }

  @GetMapping("slug/{slug}")
  public ResponseCharacterDto findOneByName(@PathVariable String slug) {
    return findOneCharacterBySlugUseCase.execute(slug);
  }

  @GetMapping
  public Page<ResponseCharacterDto> findAll(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "name") String sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findAllCharactersUseCase.execute(pageable);
  }

  @GetMapping("type")
  public Page<ResponseCharacterDto> findAll(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "name") String sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction,
      @RequestParam CharacterType type) {
    Pageable pageable = PageRequest.of(page, size, direction, sort);

    return findAllCharactersByTypeUseCase.execute(pageable, type);
  }

  @PutMapping("{id}")
  public ResponseCharacterDto update(@PathVariable UUID id, @Valid @RequestBody UpdateCharacterDto dto) {
    return updateCharacterUseCase.execute(id, dto);
  }

  @PutMapping("slug/{slug}")
  public ResponseCharacterDto updateBySlug(@PathVariable String slug, @Valid @RequestBody UpdateCharacterDto dto) {
    return updateCharacterBySlugUseCase.execute(slug, dto);
  }
}
