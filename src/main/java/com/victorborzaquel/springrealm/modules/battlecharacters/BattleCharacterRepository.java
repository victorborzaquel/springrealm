package com.victorborzaquel.springrealm.modules.battlecharacters;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleCharacterRepository extends JpaRepository<BattleCharacterEntity, UUID> {

}
