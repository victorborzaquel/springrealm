package com.victorborzaquel.springrealm.api;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.victorborzaquel.springrealm.modules.classes.dto.CreateClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.UpdateClassDto;
import com.victorborzaquel.springrealm.modules.classes.usecases.CreateClassUseCase;
import com.victorborzaquel.springrealm.modules.classes.usecases.DeleteClassUseCase;
import com.victorborzaquel.springrealm.modules.classes.usecases.FindAllClassesUseCase;
import com.victorborzaquel.springrealm.modules.classes.usecases.FindOneClassUseCase;
import com.victorborzaquel.springrealm.modules.classes.usecases.UpdateClassUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassResource {
  private final FindAllClassesUseCase findAllClasses;
  private final CreateClassUseCase createClass;
  private final UpdateClassUseCase updateClass;
  private final FindOneClassUseCase findOneClass;
  private final DeleteClassUseCase deleteClass;

  @GetMapping("{id}")
  public ResponseClassDto findOne(@PathVariable UUID id) {
    return findOneClass.execute(id);
  }

  @GetMapping
  public Page<ResponseClassDto> findAll(
    @RequestParam(defaultValue = "0") Integer page, 
    @RequestParam(defaultValue = "20") Integer size,
    @RequestParam(defaultValue = "name") String sort,
    @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
    return findAllClasses.execute(pageable);
  }

  @PostMapping
  public ResponseClassDto create(@RequestBody @Valid CreateClassDto dto) {
    return createClass.execute(dto);
  }

  @PutMapping("{id}")
  public ResponseClassDto update(@PathVariable UUID id, @RequestBody @Valid UpdateClassDto dto) {
    return updateClass.execute(id, dto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    deleteClass.execute(id);
  }
}
