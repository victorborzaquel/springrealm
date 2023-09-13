package com.victorborzaquel.springrealm.modules.characters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID> {
  Page<CharacterEntity> findAllByType(Pageable pageable, CharacterType type);

  boolean existsByName(String name);

  boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

  Optional<CharacterEntity> findByNameIgnoreCase(String name);

  boolean existsBySlug(String slug);

  boolean existsBySlugIgnoreCaseAndIdNot(String slug, UUID id);

  Optional<CharacterEntity> findBySlugIgnoreCase(String slug);
}
