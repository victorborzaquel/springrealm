package com.victorborzaquel.springrealm.modules.battles.dto;

import com.victorborzaquel.springrealm.modules.dices.dto.ResponseDiceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAttackBattleDto {
  private Integer turn;
  private Boolean isPlayerTurn;
  private Integer attackPower;
  private Integer defensePower;
  private Integer damage;
  private ResponseDiceDto attackDice;
  private ResponseDiceDto defenseDice;
  private ResponseDiceDto damageDice;
  private ResponseBattleCharacterDto player;
  private ResponseBattleCharacterDto enemy;
}
