package com.victorborzaquel.springrealm.modules.battles.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RollInitialDiceDto {
  @NotEmpty(message = "Player username is required")
  private String playerUsername;
}
