package com.victorborzaquel.springrealm.modules.battles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.victorborzaquel.springrealm.modules.battlecharacters.BattleCharacterEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.turns.TurnEntity;

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
public class BattleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "ended_at", nullable = true)
  private LocalDateTime endedAt;

  @ManyToOne(optional = false)
  private PlayerEntity player;

  @ManyToOne(optional = false)
  private EnemyEntity enemy;

  @Column(nullable = false)
  @Builder.Default
  private Integer quantityTurns = 0;

  @Column(nullable = false)
  @Builder.Default
  private BattleStatus status = BattleStatus.INITIAL_ROLL;

  @Column(name = "is_player_initiative", nullable = true)
  private Boolean isPlayerInitiative;

  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  private BattleCharacterEntity playerBattleCharacter;

  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  private BattleCharacterEntity enemyBattleCharacter;

  @OneToMany(mappedBy = "battle", cascade = CascadeType.REMOVE)
  private List<TurnEntity> turns;

  public void setPlayerTurn() {
    this.status = BattleStatus.PLAYER_TURN;
  }

  public void setEnemyTurn() {
    this.status = BattleStatus.ENEMY_TURN;
  }

  public void addTurn() {
    this.quantityTurns++;
  }

  public Boolean isInitialRoll() {
    return this.status == BattleStatus.INITIAL_ROLL;
  }

  public Boolean isPlayerTurn() {
    return this.status == BattleStatus.PLAYER_TURN;
  }

  public Boolean isEnemyTurn() {
    return this.status == BattleStatus.ENEMY_TURN;
  }

  public void endBattle() {
    this.endedAt = LocalDateTime.now();
    this.status = BattleStatus.FINISHED;
  }

  public Boolean getIsInProgress() {
    return this.endedAt == null;
  }

  public Boolean getIsPlayerWinner() {
    if (getIsInProgress()) {
      return null;
    }

    return this.playerBattleCharacter.getPv() > 0;
  }
}
