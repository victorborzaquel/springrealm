package com.victorborzaquel.springrealm.modules.characters.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
  @Pattern(regexp = "^[a-z-]+$", message = "Slug must be in lowercase and without spaces")
  private String slug;

  @NotNull(message = "Life is required")
  @Min(value = 0, message = "Life must be greater than or equal to 0")
  @Max(value = 100, message = "Life must be less than or equal to 100")
  private Integer life;

  @NotNull(message = "Strength is required")
  @Min(value = 0, message = "Strength must be greater than or equal to 0")
  @Max(value = 100, message = "Strength must be less than or equal to 100")
  private Integer strength;

  @NotNull(message = "Agility is required")
  @Min(value = 0, message = "Agility must be greater than or equal to 0")
  @Max(value = 100, message = "Agility must be less than or equal to 100")
  private Integer agility;

  @NotNull(message = "Defense is required")
  @Min(value = 0, message = "Defense must be greater than or equal to 0")
  @Max(value = 100, message = "Defense must be less than or equal to 100")
  private Integer defense;

  @NotNull(message = "Quantity dices is required")
  @Min(value = 1, message = "Quantity dices must be greater than 0")
  @Max(value = 5, message = "Quantity dices must be less than 6")
  private Integer quantityDices;

  @NotNull(message = "Quantity faces is required")
  @Min(value = 3, message = "Quantity faces must be greater than 2")
  @Max(value = 25, message = "Quantity faces must be less than 26")
  private Integer quantityFaces;
}
