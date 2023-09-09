package com.victorborzaquel.springrealm.modules.classes.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.classes.Class;
import com.victorborzaquel.springrealm.modules.classes.ClassMapper;
import com.victorborzaquel.springrealm.modules.classes.ClassRepository;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.UpdateClassDto;
import com.victorborzaquel.springrealm.modules.classes.exceptions.ClassAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.classes.exceptions.ClassNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateClassUseCase {
  private final ClassRepository repository;

  public ResponseClassDto execute(UUID id, UpdateClassDto dto) {
    validate(id, dto);
    
    repository.findById(id).orElseThrow(ClassNotFoundException::new);

    Class entity = ClassMapper.INSTANCE.toEntity(id, dto);

    Class entityCreate = repository.save(entity);

    return ClassMapper.INSTANCE.toDto(entityCreate);
  }

  private void validate(UUID id, UpdateClassDto dto) {
    List<String> errors = new ArrayList<>();

    if (repository.findByNameAndIdNot(dto.getName(), id).isPresent()) {
      errors.add("name already exists");
    }
    
    if (errors.size() > 0) {
      throw new ClassAlreadyExistsException(errors);
    }
  }
}
