package com.victorborzaquel.springrealm.modules.players.dto;

import java.io.Serializable;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePlayerDto implements Serializable {
  private UUID id;
  private String firstName;
  private String lastName;
  private String fullName;
  private String username;
  private ResponseCharacterDto character;
}
