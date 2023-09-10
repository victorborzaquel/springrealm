package com.victorborzaquel.springrealm.modules.characters.exceptions;

import java.util.List;

import com.victorborzaquel.springrealm.exceptions.base.AlreadyExistsException;

public class CharacterAlreadyExistsException extends AlreadyExistsException {
  private static String reason = "Character already exists";

  public CharacterAlreadyExistsException() {
    super(reason);
  }

  public CharacterAlreadyExistsException(List<String> errors) {
    super(errors, reason);
  }
}
