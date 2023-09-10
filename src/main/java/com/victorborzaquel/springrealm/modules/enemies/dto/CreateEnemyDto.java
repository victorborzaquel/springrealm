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
public class CreateEnemyDto {

  @NotEmpty(message = "Name is required")
  private String name;

  @NotEmpty(message = "Slug is required")
  @Pattern(regexp = "^[a-z]+$", message = "Slug must be in lowercase and without spaces")
  private String slug;

  @NotEmpty(message = "Character name is required")
  private String characterName;

}
