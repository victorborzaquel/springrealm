package com.victorborzaquel.springrealm.modules.classes.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class CreateClassDto {
  @NotEmpty(message = "Name is required")
  private String name;

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
