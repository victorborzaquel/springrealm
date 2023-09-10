package com.victorborzaquel.springrealm.modules.characters.exceptions;

import com.victorborzaquel.springrealm.exceptions.base.NotFoundException;

public class CharacterNotFoundException extends NotFoundException {
  private static String reason = "Character not found";

  public CharacterNotFoundException() {
    super(reason);
  }
}
