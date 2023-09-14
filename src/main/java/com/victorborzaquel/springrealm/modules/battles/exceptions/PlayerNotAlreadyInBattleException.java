package com.victorborzaquel.springrealm.modules.battles.exceptions;

import com.victorborzaquel.springrealm.shared.exceptions.base.AlreadyExistsException;

public class PlayerNotAlreadyInBattleException extends AlreadyExistsException {
  private static String reason = "Player not already in battle";

  public PlayerNotAlreadyInBattleException() {
    super(reason);
  }
}
