package com.victorborzaquel.springrealm.modules.battles;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.victorborzaquel.springrealm.modules.players.Player;

public interface BattleRepository extends JpaRepository<Battle, UUID> {

  Page<Battle> findAllByPlayer(Pageable pageable, Player player);

  Boolean existsByPlayerAndInProgressTrue(Player player);

  Optional<Battle> findByPlayerAndInProgressTrue(Player player);

}
