package com.victorborzaquel.springrealm.modules.characters.dto;

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
public class ResponseCharacterDto implements Serializable {
  private UUID id;
  private String name;
  private String slug;
  private Integer life;
  private Integer strength;
  private Integer agility;
  private Integer defense;
  private String dice;
  private CharacterType type;
}
