package com.victorborzaquel.springrealm.shared.exceptions.base;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ConflictException extends AppException {

  public ConflictException(List<String> errors, String reason) {
    super(
        errors,
        HttpStatus.CONFLICT,
        reason != null ? reason : "Conflict in the application");
  }

  public ConflictException(String reason) {
    this(null, reason);
  }

  public ConflictException() {
    this(null, null);
  }

}
