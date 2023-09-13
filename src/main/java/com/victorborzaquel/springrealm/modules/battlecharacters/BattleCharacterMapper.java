package com.victorborzaquel.springrealm.modules.battlecharacters;

import com.victorborzaquel.springrealm.modules.battlecharacters.dto.ResponseBattleCharacterDto;

public class BattleCharacterMapper {
  private BattleCharacterMapper() {
  }

  public static ResponseBattleCharacterDto toDto(BattleCharacterEntity entity) {
    return ResponseBattleCharacterDto.builder()
        .id(entity.getId())
        .type(entity.getType())
        .name(entity.getName())
        .slug(entity.getSlug())
        .pv(entity.getPv())
        .strength(entity.getStrength())
        .agility(entity.getAgility())
        .defense(entity.getDefense())
        .quantityDices(entity.getQuantityDices())
        .quantityFaces(entity.getQuantityFaces())
        .dice(entity.getDice())
        .isDead(entity.getIsDead())
        .build();
  }
}
