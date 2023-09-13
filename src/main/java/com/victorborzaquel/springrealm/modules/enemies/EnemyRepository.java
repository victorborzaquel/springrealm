package com.victorborzaquel.springrealm.modules.enemies;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnemyRepository extends JpaRepository<EnemyEntity, UUID> {
  boolean existsBySlug(String slug);

  boolean existsBySlugAndIdNot(String slug, UUID id);

  Optional<EnemyEntity> findBySlugIgnoreCase(String slug);

  @Query(value = "SELECT * FROM enemies ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
  Optional<EnemyEntity> findRandom();
}
