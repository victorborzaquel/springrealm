package com.victorborzaquel.springrealm.modules.enemies.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.NotFoundException;

public class EnemyIsNotHeroException extends NotFoundException {

  private static String reason = "Enemy cannot have a hero character";

  public EnemyIsNotHeroException() {
    super(reason);
  }

}
