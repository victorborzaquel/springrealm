package com.victorborzaquel.springrealm.modules.players.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlayerDto {
  @NotEmpty(message = "First name is required")
  private String firstName;

  @NotEmpty(message = "Last name is required")
  private String lastName;

  @NotEmpty(message = "Username is required")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must be alphanumeric")
  private String username;

  @NotEmpty(message = "Character name is required")
  private String characterName;
}
