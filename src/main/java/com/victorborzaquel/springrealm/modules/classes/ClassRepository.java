package com.victorborzaquel.springrealm.modules.classes;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, UUID>{
  
}
