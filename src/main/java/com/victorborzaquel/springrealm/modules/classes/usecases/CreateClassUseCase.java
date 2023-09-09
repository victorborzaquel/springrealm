package com.victorborzaquel.springrealm.modules.classes.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.classes.Class;
import com.victorborzaquel.springrealm.modules.classes.ClassMapper;
import com.victorborzaquel.springrealm.modules.classes.ClassRepository;
import com.victorborzaquel.springrealm.modules.classes.dto.CreateClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;
import com.victorborzaquel.springrealm.modules.classes.exceptions.ClassAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateClassUseCase {
  private final ClassRepository repository;

  public ResponseClassDto execute(CreateClassDto dto) {
    validate(dto);

    Class entity = ClassMapper.INSTANCE.toEntity(dto);

    Class entityCreate = repository.save(entity);

    return ClassMapper.INSTANCE.toDto(entityCreate);
  }

  private void validate(CreateClassDto dto) {
    List<String> errors = new ArrayList<>();

    if (repository.findByName(dto.getName()).isPresent()) {
      errors.add("name already exists");
    }
    
    if (errors.size() > 0) {
      throw new ClassAlreadyExistsException(errors);
    }
  }
}
