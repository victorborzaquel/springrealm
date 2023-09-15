package com.victorborzaquel.springrealm.modules.enemies.dto;

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
public class UpdateEnemyDto {
  @NotEmpty(message = "First name is required")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "First name must be alphanumeric")
  private String firstName;

  @Pattern(regexp = "^[a-zA-Z ]*$", message = "Last name must be alphanumeric")
  private String lastName;

  @NotEmpty(message = "Slug is required")
  @Pattern(regexp = "^[a-z-]+$", message = "Slug must be in lowercase and without spaces")
  private String slug;

  @NotEmpty(message = "Character slug is required")
  private String characterSlug;
}
