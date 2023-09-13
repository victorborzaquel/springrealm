package com.victorborzaquel.springrealm.modules.players;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {
  boolean existsByUsername(String username);

  boolean existsByUsernameAndIdNot(String username, UUID id);

  Optional<PlayerEntity> findByUsernameIgnoreCase(String username);
}
