package com.victorborzaquel.springrealm.modules.players;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterMapper;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.UpdatePlayerDto;

public class PlayerMapper {
  private PlayerMapper() {
  }

  public static ResponsePlayerDto toDto(PlayerEntity player) {
    return ResponsePlayerDto.builder()
        .id(player.getId())
        .character(CharacterMapper.toDto(player.getCharacter()))
        .firstName(player.getFirstName())
        .lastName(player.getLastName())
        .fullName(player.getName())
        .username(player.getUsername())
        .build();
  }

  public static PlayerEntity toEntity(CreatePlayerDto dto, CharacterEntity character) {
    return PlayerEntity.builder()
        .character(character)
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .character(character)
        .username(dto.getUsername())
        .id(UUID.randomUUID())
        .build();
  }

  public static PlayerEntity toEntity(UUID id, UpdatePlayerDto dto, CharacterEntity character) {
    return PlayerEntity.builder()
        .character(character)
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .character(character)
        .username(dto.getUsername())
        .id(id)
        .build();
  }

  public static List<ResponsePlayerDto> toDto(List<PlayerEntity> players) {
    return players.stream().map(PlayerMapper::toDto).toList();
  }

  public static Page<ResponsePlayerDto> toDto(Page<PlayerEntity> players) {
    return players.map(PlayerMapper::toDto);
  }
}
