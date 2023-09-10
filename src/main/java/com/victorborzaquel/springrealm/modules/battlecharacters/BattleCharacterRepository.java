package com.victorborzaquel.springrealm.modules.battlecharacters;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorborzaquel.springrealm.modules.battles.Battle;

public interface BattleCharacterRepository extends JpaRepository<BattleCharacter, UUID> {
  
}
