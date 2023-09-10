package com.victorborzaquel.springrealm.modules.players;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
  boolean existsByUsername(String username);

  boolean existsByUsernameAndIdNot(String username, UUID id);

  Optional<Player> findByUsernameIgnoreCase(String username);
}
