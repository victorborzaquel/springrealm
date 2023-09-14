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
import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.dto.UpdateCharacterDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CharacterControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private CharacterRepository characterRepository;

  private CharacterEntity characterEntity;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = "/characters";
    RestAssured.rootPath = "";

    characterEntity = CharacterEntity.builder()
        .slug("hero")
        .agility(10)
        .strength(10)
        .defense(10)
        .life(12)
        .type(CharacterType.HERO)
        .name("hero")
        .quantityDices(1)
        .quantityFaces(12)
        .build();

    characterEntity = characterRepository.save(characterEntity);
  }

  @Test
  void create() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Super Hero")
        .life(20)
        .strength(7)
        .defense(5)
        .agility(6)
        .quantityDices(1)
        .quantityFaces(12)
        .slug("super-hero")
        .type(CharacterType.HERO)
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("name", equalTo(dto.getName()))
        .body("life", equalTo(dto.getLife()))
        .body("strength", equalTo(dto.getStrength()))
        .body("defense", equalTo(dto.getDefense()))
        .body("agility", equalTo(dto.getAgility()))
        .body("dice", equalTo(dto.getQuantityDices() + "d" + dto.getQuantityFaces()))
        .body("type", equalTo(dto.getType().toString()))
        .body("slug", equalTo(dto.getSlug()));

    Optional<CharacterEntity> result = characterRepository.findBySlugIgnoreCase(dto.getSlug());

    assertEquals(dto.getName(), result.get().getName());
    assertEquals(dto.getLife(), result.get().getLife());
    assertEquals(dto.getStrength(), result.get().getStrength());
    assertEquals(dto.getDefense(), result.get().getDefense());
    assertEquals(dto.getAgility(), result.get().getAgility());
    assertEquals(dto.getQuantityDices(), result.get().getQuantityDices());
    assertEquals(dto.getQuantityFaces(), result.get().getQuantityFaces());
    assertEquals(dto.getType(), result.get().getType());
    assertEquals(dto.getSlug(), result.get().getSlug());
  }

  @Test
  void findAll() {
    RestAssured.rootPath = "content[0]";

    given()
        .when()
        .get()
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(characterEntity.getId().toString()))
        .body("name", equalTo(characterEntity.getName()))
        .body("life", equalTo(characterEntity.getLife()))
        .body("strength", equalTo(characterEntity.getStrength()))
        .body("defense", equalTo(characterEntity.getDefense()))
        .body("agility", equalTo(characterEntity.getAgility()))
        .body("dice", equalTo(characterEntity.getQuantityDices() + "d" + characterEntity.getQuantityFaces()))
        .body("type", equalTo(characterEntity.getType().toString()))
        .body("slug", equalTo(characterEntity.getSlug()));
  }

  @Test
  void findOne() {
    given()
        .when()
        .get(characterEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(characterEntity.getId().toString()))
        .body("name", equalTo(characterEntity.getName()))
        .body("life", equalTo(characterEntity.getLife()))
        .body("strength", equalTo(characterEntity.getStrength()))
        .body("defense", equalTo(characterEntity.getDefense()))
        .body("agility", equalTo(characterEntity.getAgility()))
        .body("dice", equalTo(characterEntity.getQuantityDices() + "d" + characterEntity.getQuantityFaces()))
        .body("type", equalTo(characterEntity.getType().toString()))
        .body("slug", equalTo(characterEntity.getSlug()));
  }

  @Test
  void findOneBySlug() {
    given()
        .when()
        .get("slug/" + characterEntity.getSlug())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(characterEntity.getId().toString()))
        .body("name", equalTo(characterEntity.getName()))
        .body("life", equalTo(characterEntity.getLife()))
        .body("strength", equalTo(characterEntity.getStrength()))
        .body("defense", equalTo(characterEntity.getDefense()))
        .body("agility", equalTo(characterEntity.getAgility()))
        .body("dice", equalTo(characterEntity.getQuantityDices() + "d" + characterEntity.getQuantityFaces()))
        .body("type", equalTo(characterEntity.getType().toString()))
        .body("slug", equalTo(characterEntity.getSlug()));
  }

  @Test
  void update() {
    UpdateCharacterDto dto = UpdateCharacterDto.builder()
        .name("Warrior")
        .life(20)
        .strength(7)
        .defense(5)
        .agility(6)
        .quantityDices(1)
        .quantityFaces(12)
        .slug("warrior")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put(characterEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo(dto.getName()))
        .body("life", equalTo(dto.getLife()))
        .body("strength", equalTo(dto.getStrength()))
        .body("defense", equalTo(dto.getDefense()))
        .body("agility", equalTo(dto.getAgility()))
        .body("dice", equalTo(dto.getQuantityDices() + "d" + dto.getQuantityFaces()))
        .body("type", equalTo(characterEntity.getType().toString()))
        .body("slug", equalTo(dto.getSlug()));

    Optional<CharacterEntity> result = characterRepository.findById(characterEntity.getId());

    assertEquals(Optional.of(characterEntity), result);
    assertEquals(dto.getName(), result.get().getName());
    assertEquals(dto.getLife(), result.get().getLife());
    assertEquals(dto.getStrength(), result.get().getStrength());
    assertEquals(dto.getDefense(), result.get().getDefense());
    assertEquals(dto.getAgility(), result.get().getAgility());
    assertEquals(dto.getQuantityDices(), result.get().getQuantityDices());
    assertEquals(dto.getQuantityFaces(), result.get().getQuantityFaces());
    assertEquals(characterEntity.getType(), result.get().getType());
    assertEquals(dto.getSlug(), result.get().getSlug());
  }

  @Test
  void updateBySlug() {
    UpdateCharacterDto dto = UpdateCharacterDto.builder()
        .name("Warrior")
        .life(20)
        .strength(7)
        .defense(5)
        .agility(6)
        .quantityDices(1)
        .quantityFaces(12)
        .slug("warrior")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put("slug/" + characterEntity.getSlug())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo(dto.getName()))
        .body("life", equalTo(dto.getLife()))
        .body("strength", equalTo(dto.getStrength()))
        .body("defense", equalTo(dto.getDefense()))
        .body("agility", equalTo(dto.getAgility()))
        .body("dice", equalTo(dto.getQuantityDices() + "d" + dto.getQuantityFaces()))
        .body("type", equalTo(characterEntity.getType().toString()))
        .body("slug", equalTo(dto.getSlug()));

    Optional<CharacterEntity> result = characterRepository.findById(characterEntity.getId());

    assertEquals(Optional.of(characterEntity), result);
    assertEquals(dto.getName(), result.get().getName());
    assertEquals(dto.getLife(), result.get().getLife());
    assertEquals(dto.getStrength(), result.get().getStrength());
    assertEquals(dto.getDefense(), result.get().getDefense());
    assertEquals(dto.getAgility(), result.get().getAgility());
    assertEquals(dto.getQuantityDices(), result.get().getQuantityDices());
    assertEquals(dto.getQuantityFaces(), result.get().getQuantityFaces());
    assertEquals(characterEntity.getType(), result.get().getType());
    assertEquals(dto.getSlug(), result.get().getSlug());
  }
}
