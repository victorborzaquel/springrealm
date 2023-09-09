package com.victorborzaquel.springrealm.modules.classes.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.victorborzaquel.springrealm.modules.classes.Class;
import com.victorborzaquel.springrealm.modules.classes.ClassMapper;
import com.victorborzaquel.springrealm.modules.classes.ClassRepository;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAllClassesUseCase {
  private final ClassRepository repository;

  public Page<ResponseClassDto> execute(Pageable pageable) {
    Page<Class> pageClasses = repository.findAll(pageable);

    return ClassMapper.INSTANCE.toDto(pageClasses);
  }
}
