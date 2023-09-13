package com.victorborzaquel.springrealm.modules.turns;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;

public interface TurnRepository extends JpaRepository<TurnEntity, UUID> {
  Integer countByBattle(BattleEntity battle);

  List<TurnEntity> findAllByBattle(BattleEntity battle);

  Page<TurnEntity> findAllByBattle(BattleEntity battle, Pageable pageable);
}
