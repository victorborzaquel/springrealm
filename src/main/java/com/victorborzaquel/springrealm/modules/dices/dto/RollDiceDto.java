package com.victorborzaquel.springrealm.modules.dices.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RollDiceDto {
  @NotNull(message = "Quantity dices is required")
  @Min(value = 1, message = "Quantity dices must be greater than 0")
  @Max(value = 5, message = "Quantity dices must be less than 6")
  private Integer quantityDices;

  @NotNull(message = "Quantity faces is required")
  @Min(value = 3, message = "Quantity faces must be greater than 2")
  @Max(value = 25, message = "Quantity faces must be less than 26")
  private Integer quantityFaces;
}
