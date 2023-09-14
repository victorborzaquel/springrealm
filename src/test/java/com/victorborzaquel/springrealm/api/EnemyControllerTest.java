package com.victorborzaquel.springrealm.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.enemies.EnemyEntity;
import com.victorborzaquel.springrealm.modules.enemies.EnemyRepository;
import com.victorborzaquel.springrealm.modules.enemies.dto.CreateEnemyDto;
import com.victorborzaquel.springrealm.modules.enemies.dto.UpdateEnemyDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EnemyControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private EnemyRepository enemyRepository;

  @Autowired
  private CharacterRepository characterRepository;

  private CharacterEntity characterEntity;
  private EnemyEntity enemyEntity;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = "/enemies";
    RestAssured.rootPath = "";

    characterEntity = CharacterEntity.builder()
        .slug("werewolf")
        .agility(10)
        .strength(10)
        .defense(10)
        .life(12)
        .type(CharacterType.MONSTER)
        .name("werewolf")
        .quantityDices(1)
        .quantityFaces(12)
        .build();

    characterEntity = characterRepository.save(characterEntity);

    enemyEntity = EnemyEntity.builder()
        .firstName("super")
        .slug("monster")
        .character(characterEntity)
        .lastName("monster")
        .build();

    enemyEntity = enemyRepository.save(enemyEntity);
  }

  @Test
  void create() {
    CreateEnemyDto dto = CreateEnemyDto.builder()
        .slug("goblin")
        .firstName("super")
        .characterSlug("werewolf")
        .lastName("goblin")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("slug", equalTo(dto.getSlug()))
        .body("firstName", equalTo(dto.getFirstName()))
        .body("lastName", equalTo(dto.getLastName()))
        .body("character.slug", equalTo(dto.getCharacterSlug()));

    Optional<EnemyEntity> result = enemyRepository.findBySlugIgnoreCase(dto.getSlug());

    assertEquals(dto.getSlug(), result.get().getSlug());
    assertEquals(dto.getFirstName(), result.get().getFirstName());
    assertEquals(dto.getLastName(), result.get().getLastName());
    assertEquals(dto.getCharacterSlug(), result.get().getCharacter().getSlug());
  }

  @Test
  void findAll() {
    RestAssured.rootPath = "content[0]";

    given()
        .when()
        .get()
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(enemyEntity.getId().toString()))
        .body("slug", equalTo(enemyEntity.getSlug()))
        .body("firstName", equalTo(enemyEntity.getFirstName()))
        .body("lastName", equalTo(enemyEntity.getLastName()))
        .body("character.slug", equalTo(enemyEntity.getCharacter().getSlug()));
  }

  @Test
  void findOne() {
    given()
        .when()
        .get(enemyEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(enemyEntity.getId().toString()))
        .body("slug", equalTo(enemyEntity.getSlug()))
        .body("firstName", equalTo(enemyEntity.getFirstName()))
        .body("lastName", equalTo(enemyEntity.getLastName()))
        .body("character.slug", equalTo(enemyEntity.getCharacter().getSlug()));
  }

  @Test
  void findOneByUsername() {
    given()
        .when()
        .get("slug/" + enemyEntity.getSlug())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(enemyEntity.getId().toString()))
        .body("slug", equalTo(enemyEntity.getSlug()))
        .body("firstName", equalTo(enemyEntity.getFirstName()))
        .body("lastName", equalTo(enemyEntity.getLastName()))
        .body("character.slug", equalTo(enemyEntity.getCharacter().getSlug()));
  }

  @Test
  void update() {
    UpdateEnemyDto dto = UpdateEnemyDto.builder()
        .slug("monster")
        .firstName("minotaur")
        .characterSlug("werewolf")
        .lastName("monster")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put(enemyEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("slug", equalTo(dto.getSlug()))
        .body("firstName", equalTo(dto.getFirstName()))
        .body("lastName", equalTo(dto.getLastName()))
        .body("character.slug", equalTo(dto.getCharacterSlug()))
        .body("id", equalTo(enemyEntity.getId().toString()));

    Optional<EnemyEntity> result = enemyRepository.findById(enemyEntity.getId());

    assertEquals(Optional.of(enemyEntity), result);
    assertEquals(dto.getSlug(), result.get().getSlug());
    assertEquals(dto.getFirstName(), result.get().getFirstName());
    assertEquals(dto.getLastName(), result.get().getLastName());
    assertEquals(dto.getCharacterSlug(), result.get().getCharacter().getSlug());
  }

  @Test
  void updateByUsername() {
    UpdateEnemyDto dto = UpdateEnemyDto.builder()
        .slug("monster")
        .firstName("minotaur")
        .characterSlug("werewolf")
        .lastName("monster")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put("slug/" + enemyEntity.getSlug())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("slug", equalTo(dto.getSlug()))
        .body("firstName", equalTo(dto.getFirstName()))
        .body("lastName", equalTo(dto.getLastName()))
        .body("character.slug", equalTo(dto.getCharacterSlug()))
        .body("id", equalTo(enemyEntity.getId().toString()));

    Optional<EnemyEntity> result = enemyRepository.findById(enemyEntity.getId());

    assertEquals(Optional.of(enemyEntity), result);
    assertEquals(dto.getSlug(), result.get().getSlug());
    assertEquals(dto.getFirstName(), result.get().getFirstName());
    assertEquals(dto.getLastName(), result.get().getLastName());
    assertEquals(dto.getCharacterSlug(), result.get().getCharacter().getSlug());
  }
}
