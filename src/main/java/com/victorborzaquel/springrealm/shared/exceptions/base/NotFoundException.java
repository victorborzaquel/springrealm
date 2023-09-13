package com.victorborzaquel.springrealm.shared.exceptions.base;

import java.util.List;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {

  public NotFoundException(List<String> errors, String reason) {
    super(
        errors,
        HttpStatus.NOT_FOUND,
        reason != null ? reason : "Entity not found");
  }

  public NotFoundException(String reason) {
    this(null, reason);
  }

  public NotFoundException() {
    this(null, null);
  }

}
