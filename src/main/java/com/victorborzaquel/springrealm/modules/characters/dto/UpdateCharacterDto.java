package com.victorborzaquel.springrealm.modules.characters.dto;

import java.io.Serializable;

import com.victorborzaquel.springrealm.modules.characters.CharacterType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCharacterDto {
  @NotEmpty(message = "Name is required")
  private String name;

  @NotEmpty(message = "Slug is required")
  private String slug;

  @NotNull(message = "Life is required")
  private Integer life;

  @NotNull(message = "Strength is required")
  private Integer strength;

  @NotNull(message = "Agility is required")
  private Integer agility;

  @NotNull(message = "Defense is required")
  private Integer defense;

  @NotNull(message = "Quantity dices is required")
  private Integer quantityDices;

  @NotNull(message = "Quantity faces is required")
  private Integer quantityFaces;
}
