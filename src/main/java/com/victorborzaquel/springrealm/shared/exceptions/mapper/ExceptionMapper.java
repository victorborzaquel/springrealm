package com.victorborzaquel.springrealm.shared.exceptions.mapper;

import com.victorborzaquel.springrealm.shared.exceptions.base.AppException;
import com.victorborzaquel.springrealm.shared.exceptions.dto.ResponseExceptionDTO;

public class ExceptionMapper {
  public static ResponseExceptionDTO toResponse(AppException exception) {
    return ResponseExceptionDTO.builder()
        .errors(exception.getErrors())
        .message(exception.getAppMessage())
        .reason(exception.getReason())
        .status(exception.getStatus())
        .timestamp(exception.getTimestamp())
        .build();
  }
}
