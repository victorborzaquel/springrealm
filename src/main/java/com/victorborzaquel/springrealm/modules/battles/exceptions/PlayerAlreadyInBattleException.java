package com.victorborzaquel.springrealm.modules.battles.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.AlreadyExistsException;

public class PlayerAlreadyInBattleException extends AlreadyExistsException {

  private static String reason = "Player already in battle";

  public PlayerAlreadyInBattleException() {
    super(reason);
  }

}
