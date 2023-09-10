package com.victorborzaquel.springrealm.modules.characters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

  Page<Character> findAllByType(Pageable pageable, CharacterType type);

  boolean existsByName(String name);

  boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

  Optional<Character> findByNameIgnoreCase(String name);

  boolean existsBySlug(String slug);

  boolean existsBySlugIgnoreCaseAndIdNot(String slug, UUID id);

  Optional<Character> findBySlugIgnoreCase(String slug);

}
