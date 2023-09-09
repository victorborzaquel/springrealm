package com.victorborzaquel.springrealm.modules.classes;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.victorborzaquel.springrealm.modules.classes.dto.CreateClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.ResponseClassDto;
import com.victorborzaquel.springrealm.modules.classes.dto.UpdateClassDto;

@Mapper
public interface ClassMapper {
  ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

  ResponseClassDto toDto(Class entity);

  List<ResponseClassDto> toDto(List<Class> entity);

  default Page<ResponseClassDto> toDto(Page<Class> entityPage) {
    List<ResponseClassDto> dtoList = toDto(entityPage.getContent());
    return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
  }

  @Mapping(target = "id", ignore = true)
  Class toEntity(CreateClassDto dto);

  Class toEntity(UUID id, UpdateClassDto dto);
}
