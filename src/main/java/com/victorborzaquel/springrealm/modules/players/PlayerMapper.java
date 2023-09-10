package com.victorborzaquel.springrealm.modules.players;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.UpdatePlayerDto;

@Mapper
public interface PlayerMapper {
  PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

  ResponsePlayerDto toDto(Player player);

  List<ResponsePlayerDto> toDto(List<Player> players);

  default Page<ResponsePlayerDto> toDto(Page<Player> playerPage) {
    return new PageImpl<>(
        toDto(playerPage.getContent()),
        playerPage.getPageable(),
        playerPage.getTotalElements());
  }

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "battles", ignore = true)
  Player toEntity(CreatePlayerDto dto, Character character);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "battles", ignore = true)
  Player toEntity(UUID id, UpdatePlayerDto dto, Character character);
}
