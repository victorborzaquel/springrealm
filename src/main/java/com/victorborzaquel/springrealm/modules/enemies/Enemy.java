package com.victorborzaquel.springrealm.modules.enemies;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.modules.battles.Battle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  @Column(name = "name", nullable = true)
  private String name;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @ManyToOne(optional = false)
  private Character character;

  @OneToMany(mappedBy = "enemy")
  private List<Battle> battles;

  public String getName() {
    if (lastName == null || lastName.isEmpty()) return firstName;

    return String.format("%s %s", firstName, lastName);
  }
}
