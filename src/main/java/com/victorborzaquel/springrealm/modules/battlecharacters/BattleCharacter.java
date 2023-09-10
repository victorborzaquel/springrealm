package com.victorborzaquel.springrealm.modules.battlecharacters;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;

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
public class BattleCharacter {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private CharacterType type;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "life", nullable = false)
  private Integer life;

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
  private List<Battle> playerBattles;

  @OneToMany(mappedBy = "enemyBattleCharacter")
  private List<Battle> enemyBattles;

  public void damage(Integer damage) {
    life = Math.max(0, life - damage);
  }

  public String getDice() {
    return quantityDices + "d" + quantityFaces;
  }

  public Boolean isAlive() {
    return life > 0;
  }

  public Boolean isDead() {
    return life <= 0;
  }
}
