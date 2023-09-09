package com.victorborzaquel.springrealm.modules.classes.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.classes.ClassRepository;
import com.victorborzaquel.springrealm.modules.classes.exceptions.ClassNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteClassUseCase {
  private final ClassRepository repository;

  public void execute(UUID id) {
    repository.findById(id).orElseThrow(ClassNotFoundException::new);

    repository.deleteById(id);
  }
}
