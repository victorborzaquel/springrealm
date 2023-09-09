package com.victorborzaquel.springrealm.modules.classes.exceptions;

import java.util.List;

import com.victorborzaquel.springrealm.exceptions.base.AlreadyExistsException;

public class ClassAlreadyExistsException extends AlreadyExistsException {

  private static String reason = "Class already exists";

  public ClassAlreadyExistsException() {
    super(reason);
  }

  public ClassAlreadyExistsException(List<String> errors) {
    super(errors, reason);
  }
}
