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
public class UpdatePlayerDto {
  @NotEmpty(message = "First name is required")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "First name must be alphanumeric")
  private String firstName;

  @Pattern(regexp = "^[a-zA-Z ]*$", message = "Last name must be alphanumeric")
  private String lastName;

  @NotEmpty(message = "Username is required")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must be alphanumeric")
  private String username;

  @NotEmpty(message = "Character slug is required")
  private String characterSlug;
}
