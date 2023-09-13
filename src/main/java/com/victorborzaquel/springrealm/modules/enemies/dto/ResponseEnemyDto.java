package com.victorborzaquel.springrealm.modules.enemies.dto;

import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEnemyDto {
  private String firstName;
  private String lastName;
  private String fullName;
  private String slug;
  private ResponseCharacterDto character;
}
