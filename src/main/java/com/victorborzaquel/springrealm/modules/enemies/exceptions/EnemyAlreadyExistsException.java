package com.victorborzaquel.springrealm.modules.enemies.exceptions;

import java.util.List;

import com.victorborzaquel.springrealm.exceptions.base.AlreadyExistsException;

public class EnemyAlreadyExistsException extends AlreadyExistsException {

  private static String reason = "Enemy already exists";

  public EnemyAlreadyExistsException() {
    super(reason);
  }

  public EnemyAlreadyExistsException(List<String> errors) {
    super(errors, reason);
  }
}
