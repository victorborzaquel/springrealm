package com.victorborzaquel.springrealm.modules.players.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.NotFoundException;

public class PlayerNotFoundException extends NotFoundException {
  private static String reason = "Player not found";

  public PlayerNotFoundException() {
    super(reason);
  }
}
