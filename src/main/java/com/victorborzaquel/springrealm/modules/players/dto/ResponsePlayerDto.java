package com.victorborzaquel.springrealm.modules.players.dto;

import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePlayerDto {
  private String name;
  private String username;
  private ResponseCharacterDto character;
}
