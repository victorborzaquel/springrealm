package com.victorborzaquel.springrealm.modules.battles.exceptions;

import com.victorborzaquel.springrealm.shared.exceptions.base.NotFoundException;

public class BattleNotFoundException extends NotFoundException {

  private static String reason = "Battle not found";

  public BattleNotFoundException() {
    super(reason);
  }

}
