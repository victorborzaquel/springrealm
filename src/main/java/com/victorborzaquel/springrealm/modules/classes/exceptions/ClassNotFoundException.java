package com.victorborzaquel.springrealm.modules.classes.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.NotFoundException;

public class ClassNotFoundException extends NotFoundException {

  public ClassNotFoundException() {
    super("Class not found");
  }

}
