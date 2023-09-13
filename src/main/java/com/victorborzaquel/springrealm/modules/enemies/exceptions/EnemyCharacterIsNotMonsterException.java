package com.victorborzaquel.springrealm.modules.enemies.exceptions;

import com.victorborzaquel.springrealm.shared.exceptions.base.NotFoundException;

public class EnemyCharacterIsNotMonsterException extends NotFoundException {
  private static String reason = "Enemy character is not a monster";

  public EnemyCharacterIsNotMonsterException() {
    super(reason);
  }
}
