package com.victorborzaquel.springrealm.modules.turns;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.turns.Turn;

public interface TurnRepository extends JpaRepository<Turn, UUID> {

  Integer countByBattle(Battle battle);

  List<Turn> findAllByBattle(Battle battle);

}
