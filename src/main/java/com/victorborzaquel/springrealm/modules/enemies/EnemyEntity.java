package com.victorborzaquel.springrealm.modules.enemies;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.battles.BattleEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.shared.utils.NameUtil;

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
@Entity(name = "enemies")
@Table(name = "enemies")
public class EnemyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @ManyToOne(optional = false)
  private CharacterEntity character;

  @OneToMany(mappedBy = "enemy")
  private List<BattleEntity> battles;

  public String getName() {
    return NameUtil.getFullName(this.firstName, this.lastName);
  }
}
