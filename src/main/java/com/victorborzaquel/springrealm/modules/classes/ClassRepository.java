package com.victorborzaquel.springrealm.modules.classes;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, UUID> {
  Optional<Class> findByName(String name);

  Optional<Class> findByNameAndIdNot(String name, UUID id);
}
