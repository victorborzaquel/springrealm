package com.victorborzaquel.springrealm.modules.battlecharacters.dto;

import java.io.Serializable;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.characters.CharacterType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBattleCharacterDto implements Serializable {
  private UUID id;
  private CharacterType type;
  private String name;
  private String slug;
  private Integer pv;
  private Integer strength;
  private Integer agility;
  private Integer defense;
  private Integer quantityDices;
  private Integer quantityFaces;
  private String dice;
  private Boolean isDead;
}
