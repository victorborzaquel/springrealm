package com.victorborzaquel.springrealm.modules.battles;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.victorborzaquel.springrealm.modules.players.PlayerEntity;

public interface BattleRepository extends JpaRepository<BattleEntity, UUID> {
  Boolean existsByPlayerAndEndedAtNull(PlayerEntity player);

  Boolean existsByPlayerUsernameAndEndedAtNull(String username);

  Optional<BattleEntity> findByPlayerAndEndedAtNull(PlayerEntity player);

  Optional<BattleEntity> findByPlayerUsernameAndEndedAtNull(String username);

  @Query("SELECT b FROM battles b WHERE b.player = :player ORDER BY b.endedAt DESC")
  Optional<BattleEntity> findLastEndedAtByPlayer(PlayerEntity player);

  Page<BattleEntity> findAllByPlayerAndEndedAtNotNull(PlayerEntity player, Pageable pageable);

  Page<BattleEntity> findAllByEndedAtNotNull(Pageable pageable);
}
