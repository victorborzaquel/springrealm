package com.victorborzaquel.springrealm.modules.characters.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.ConflictException;

public class EnemiesHasCharacterException extends ConflictException {
  private static String reason = "Enemies has a character";

  public EnemiesHasCharacterException() {
    super(reason);
  }
}
