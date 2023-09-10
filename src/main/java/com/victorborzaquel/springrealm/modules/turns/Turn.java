package com.victorborzaquel.springrealm.modules.turns;

import java.util.UUID;

import org.hibernate.Hibernate;

import com.victorborzaquel.springrealm.modules.battles.Battle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "turns")
@Table(name = "turns")
public class Turn {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "number", nullable = false)
  private Integer number;

  @Column(name = "is_player_turn", nullable = false)
  private Boolean isPlayerTurn;

  @Column(name = "player_pv", nullable = false)
  private Integer playerPV;

  @Column(name = "enemy_pv", nullable = false)
  private Integer enemyPV;

  @Column(name = "attack_dice", nullable = false)
  private Integer attackDice;
  
  @Column(name = "defense_dice", nullable = false)
  private Integer defenseDice;

  @Column(name = "damage", nullable = false)
  private Integer damage;

  @ManyToOne(optional = false)
  private Battle battle;

}
