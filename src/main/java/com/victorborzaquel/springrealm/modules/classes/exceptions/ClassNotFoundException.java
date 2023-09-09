package com.victorborzaquel.springrealm.modules.classes.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.NotFoundException;

public class ClassNotFoundException extends NotFoundException {

  private static String reason = "Class not found";

  public ClassNotFoundException() {
    super(reason);
  }

}
