package com.victorborzaquel.springrealm.modules.battles.exceptions;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.shared.exceptions.base.ConflictException;

public class NotAtThatStageException extends ConflictException {
  public NotAtThatStageException(BattleEntity battle) {
    super("Not at that stage. current stage is: " + battle.getStatus().name());
  }

}
