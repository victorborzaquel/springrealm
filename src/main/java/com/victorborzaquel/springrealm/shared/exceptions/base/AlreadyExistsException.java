package com.victorborzaquel.springrealm.shared.exceptions.base;

import java.util.List;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends AppException {

  public AlreadyExistsException(List<String> errors, String reason) {
    super(
        errors,
        HttpStatus.CONFLICT,
        reason != null ? reason : "Already exists");
  }

  public AlreadyExistsException(String reason) {
    this(null, reason);
  }

  public AlreadyExistsException() {
    this(null, null);
  }

}
