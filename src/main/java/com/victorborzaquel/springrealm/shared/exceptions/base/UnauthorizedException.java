package com.victorborzaquel.springrealm.shared.exceptions.base;

import java.util.List;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AppException {
  public UnauthorizedException(List<String> errors, String reason) {
    super(
        errors,
        HttpStatus.UNAUTHORIZED,
        reason != null ? reason : "Unauthorized");
  }

  public UnauthorizedException(String reason) {
    this(null, reason);
  }

  public UnauthorizedException() {
    this(null, null);
  }
}
