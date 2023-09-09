package com.victorborzaquel.springrealm.modules.classes.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.classes.Class;
import com.victorborzaquel.springrealm.modules.classes.ClassMapper;
import com.victorborzaquel.springrealm.modules.classes.ClassRepository;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;
import com.victorborzaquel.springrealm.modules.classes.exceptions.ClassNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindOneClassUseCase {
  private final ClassRepository repository;

  public ResponseClassDto execute(UUID id) {
    Class entityClass = repository.findById(id).orElseThrow(ClassNotFoundException::new);

    return ClassMapper.INSTANCE.toDto(entityClass);
  }
}
