package com.victorborzaquel.springrealm.modules.turns;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorborzaquel.springrealm.modules.players.Player;

public interface TurnRepository extends JpaRepository<Turn, UUID>  {
  
}
