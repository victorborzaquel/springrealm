package com.victorborzaquel.springrealm.modules.battlecharacters;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.dices.DiceProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "battle_characters")
@Table(name = "battle_characters")
public class BattleCharacterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private CharacterType type;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "slug", nullable = false)
  private String slug;

  @Column(name = "life", nullable = false)
  private Integer life;

  @Column(name = "pv", nullable = false)
  private Integer pv;

  @Column(name = "strength", nullable = false)
  private Integer strength;

  @Column(name = "agility", nullable = false)
  private Integer agility;

  @Column(name = "defense", nullable = false)
  private Integer defense;

  @Column(name = "quantity_dices", nullable = false)
  private Integer quantityDices;

  @Column(name = "quantity_faces", nullable = false)
  private Integer quantityFaces;

  @OneToMany(mappedBy = "playerBattleCharacter")
  private List<BattleEntity> playerBattles;

  @OneToMany(mappedBy = "enemyBattleCharacter")
  private List<BattleEntity> enemyBattles;

  public void damage(Integer damage) {
    this.pv = Math.max(0, pv - damage);
  }

  public Integer calculeDefense(Integer defense) {
    return this.defense + this.agility + defense;
  }

  public Integer calculeAttack(Integer attack) {
    return this.strength + this.agility + attack;
  }

  public String getDice() {
    return DiceProvider.getDiceName(quantityDices, quantityFaces);
  }

  public Boolean getIsAlive() {
    return pv > 0;
  }

  public Boolean getIsDead() {
    return pv <= 0;
  }
}
