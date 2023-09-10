package com.victorborzaquel.springrealm.modules.enemies;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnemyRepository extends JpaRepository<Enemy, UUID> {
  boolean existsByName(String name);
  boolean existsByNameAndIdNot(String name, UUID id);
  Optional<Enemy> findByNameIgnoreCase(String name);

  boolean existsBySlug(String slug);
  boolean existsBySlugAndIdNot(String slug, UUID id);
  Optional<Enemy> findBySlugIgnoreCase(String slug);
}
