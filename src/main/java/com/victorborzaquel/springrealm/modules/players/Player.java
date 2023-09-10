package com.victorborzaquel.springrealm.modules.players;

import java.util.List;
import java.util.UUID;

import com.victorborzaquel.springrealm.modules.battles.Battle;
import com.victorborzaquel.springrealm.modules.characters.Character;
import com.victorborzaquel.springrealm.utils.NameUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "players")
@Table(name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @ManyToOne(optional = false)
  private Character character;

  @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE)
  private List<Battle> battles;

  public String getName() {
    return NameUtil.getFullName(this.firstName, this.lastName);
  }
}
