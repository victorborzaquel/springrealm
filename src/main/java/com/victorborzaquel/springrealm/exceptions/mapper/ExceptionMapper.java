package com.victorborzaquel.springrealm.exceptions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.victorborzaquel.springrealm.exceptions.base.AppException;
import com.victorborzaquel.springrealm.exceptions.dto.ResponseExceptionDTO;

@Mapper
public interface ExceptionMapper {

  ExceptionMapper INSTANCE = Mappers.getMapper(ExceptionMapper.class);

  @Mapping(target = "message", source = "appMessage")
  ResponseExceptionDTO toResponse(AppException exception);
}
