package com.victorborzaquel.springrealm.modules.enemies;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.characters.Character;

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
public class Enemy {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "slug", nullable = false)
  private String slug;

  @ManyToOne(optional = false)
  private Character character;

  @OneToMany(mappedBy = "enemy")
  private List<Battle> battles;
}
