package com.victorborzaquel.springrealm.modules.classes.usecases;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.classes.Class;
import com.victorborzaquel.springrealm.modules.classes.ClassMapper;
import com.victorborzaquel.springrealm.modules.classes.ClassRepository;
import com.victorborzaquel.springrealm.modules.classes.dto.CreateClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateClassUseCase {
  private final ClassRepository repository;

  public ResponseClassDto execute(CreateClassDto dto) {
    Class entity = ClassMapper.INSTANCE.toEntity(dto);

    Class entityCreate = repository.save(entity);

    return ClassMapper.INSTANCE.toDto(entityCreate);
  }
}
