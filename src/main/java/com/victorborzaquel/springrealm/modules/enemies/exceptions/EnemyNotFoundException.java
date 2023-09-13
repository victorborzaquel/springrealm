package com.victorborzaquel.springrealm.modules.enemies.exceptions;

import com.victorborzaquel.springrealm.shared.exceptions.base.NotFoundException;

public class EnemyNotFoundException extends NotFoundException {
  private static String reason = "Enemy not found";

  public EnemyNotFoundException() {
    super(reason);
  }
}
