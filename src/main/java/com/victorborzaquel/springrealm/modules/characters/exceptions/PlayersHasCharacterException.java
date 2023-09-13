package com.victorborzaquel.springrealm.modules.characters.exceptions;

import com.victorborzaquel.springrealm.shared.exceptions.base.ConflictException;

public class PlayersHasCharacterException extends ConflictException {
  private static String reason = "Players has a character";

  public PlayersHasCharacterException() {
    super(reason);
  }
}
