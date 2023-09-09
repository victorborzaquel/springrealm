package com.victorborzaquel.springrealm.modules.classes;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "classes")
@Table(name = "classes")
public class Class {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "life")
  private Integer life;

  @Column(name = "strength")
  private Integer strength;

  @Column(name = "agility")
  private Integer agility;

  @Column(name = "defense")
  private Integer defense;

  @Column(name = "quantity_dices")
  private Integer quantityDices;

  @Column(name = "quantity_faces")
  private Integer quantityFaces;

  public String getDice() {
    return String.format("%dd%d", this.quantityDices, this.quantityFaces);
  }
}
