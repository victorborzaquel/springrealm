package com.victorborzaquel.springrealm.modules.battles.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStartBattleDto implements Serializable {
  private UUID id;
  private ResponsePlayerBattleDto player;
  private ResponseEnemyBattleDto enemy;
}
