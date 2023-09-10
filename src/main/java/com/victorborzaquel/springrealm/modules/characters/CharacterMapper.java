package com.victorborzaquel.springrealm.modules.characters;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.ResponseCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.UpdateCharacterDto;

@Mapper
public interface CharacterMapper {
  CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

  ResponseCharacterDto toDto(Character character);

  List<ResponseCharacterDto> toDto(List<Character> characters);

  default Page<ResponseCharacterDto> toDto(Page<Character> characterPage) {
    return new PageImpl<>(
        toDto(characterPage.getContent()),
        characterPage.getPageable(),
        characterPage.getTotalElements());
  }

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "enemies", ignore = true)
  @Mapping(target = "players", ignore = true)
  Character toEntity(CreateCharacterDto dto);

  @Mapping(target = "type", ignore = true)
  @Mapping(target = "enemies", ignore = true)
  @Mapping(target = "players", ignore = true)
  Character toEntity(UUID id, UpdateCharacterDto dto);
}
