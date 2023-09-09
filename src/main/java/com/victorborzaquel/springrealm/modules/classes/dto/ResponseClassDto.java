package com.victorborzaquel.springrealm.modules.classes.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ResponseClassDto {
  private UUID id;
  private String name;
  private Integer life;
  private Integer strength;
  private Integer agility;
  private Integer defense;
  private String dice;
}
