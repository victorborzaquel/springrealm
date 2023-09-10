package com.victorborzaquel.springrealm.modules.characters;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;

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
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "characters")
@Table(name = "characters")
public class Character {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private CharacterType type;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "slug", unique = true, nullable = false)
  private String slug;

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

  @OneToMany(mappedBy = "character")
  private List<Player> players;

  @OneToMany(mappedBy = "character")
  private List<Enemy> enemies;
  
  public String getDice() {
    return String.format("%dd%d", this.quantityDices, this.quantityFaces);
  }
}
