package com.victorborzaquel.springrealm.modules.players.exceptions;

import java.util.List;

import com.victorborzaquel.springrealm.shared.exceptions.base.AlreadyExistsException;

public class PlayerAlreadyExistsException extends AlreadyExistsException {
  private static String reason = "Player already exists";

  public PlayerAlreadyExistsException() {
    super(reason);
  }

  public PlayerAlreadyExistsException(List<String> errors) {
    super(errors, reason);
  }
}
