package com.victorborzaquel.springrealm.modules.battles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacter;
import com.victorborzaquel.springrealm.modules.enemies.Enemy;
import com.victorborzaquel.springrealm.modules.players.Player;
import com.victorborzaquel.springrealm.modules.turns.Turn;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "ended_at", nullable = true)
  private LocalDateTime endedAt;

  @ManyToOne(optional = false)
  private Player player;

  @ManyToOne(optional = false)
  private Enemy enemy;

  @Column(name = "is_player_initiative", nullable = false)
  private Boolean isPlayerInitiative;

  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  private BattleCharacter playerBattleCharacter;

  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  private BattleCharacter enemyBattleCharacter;

  @OneToMany(mappedBy = "battle", cascade = CascadeType.REMOVE)
  private List<Turn> turns;

  public void endBattle() {
    endedAt = LocalDateTime.now();
  }

  public Boolean getIsInProgress() {
    return endedAt == null;
  }

  public Boolean getIsPlayerWinner() {
    if (getIsInProgress()) {
      return null;
    }

    return playerBattleCharacter.getPv() > 0;
  }
}
