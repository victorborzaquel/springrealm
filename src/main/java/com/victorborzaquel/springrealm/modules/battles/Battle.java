package com.victorborzaquel.springrealm.modules.battles;

import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;

import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.turns.Turn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "battles")
@Table(name = "battles")
public class Battle {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  private Player player;

  @ManyToOne(optional = false)
  private Enemy enemy;

  @Column(name = "is_player_initiative", nullable = false)
  private Boolean isPlayerInitiative;

  @Column(name = "in_progress", nullable = false)
  @Builder.Default
  private Boolean inProgress = true;

  @Column(name = "player_pv")
  private Integer playerPV;

  @Column(name = "enemy_pv")
  private Integer enemyPV;

  @OneToMany(mappedBy = "battle")
  private List<Turn> turns;

  public void initializeTurns() {
    Hibernate.initialize(turns);
  }

  public void playerAttack(Integer damage) {
    enemyPV = enemyPV - damage < 0 ? 0 : enemyPV - damage;
  }

  public void enemyAttack(Integer damage) {
    playerPV = playerPV - damage < 0 ? 0 : playerPV - damage;
  }

  public void endBattle() {
    inProgress = false;
  }
}
