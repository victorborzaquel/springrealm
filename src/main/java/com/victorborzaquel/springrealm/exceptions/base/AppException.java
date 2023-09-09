package com.victorborzaquel.springrealm.exceptions.base;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AppException extends RuntimeException {
  private final LocalDateTime timestamp = LocalDateTime.now();
  private final Integer status;
  private final HttpStatus httpStatus;
  private final String reason;
  private final List<String> errors;
  private final String appMessage;

  public AppException(List<String> errors, HttpStatus httpStatus, String reason) {
    HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    this.errors = errors != null ? new ArrayList<>(errors) : new ArrayList<>();
    this.appMessage = reason != null ? reason : "Error in the application";
    this.httpStatus = httpStatus != null ? httpStatus : defaultHttpStatus;
    this.reason = httpStatus != null ? httpStatus.getReasonPhrase() : defaultHttpStatus.getReasonPhrase();
    this.status = httpStatus != null ? httpStatus.value() : defaultHttpStatus.value();
  }

  public AppException(HttpStatus httpStatus, String reason) {
    this(new ArrayList<>(), httpStatus, reason);
  }

  public AppException(HttpStatus httpStatus) {
    this(new ArrayList<>(), httpStatus, null);
  }

  public AppException() {
    this(new ArrayList<>(), null, null);
  }
}
