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
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.UpdatePlayerDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PlayerControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private CharacterRepository characterRepository;

  private CharacterEntity characterEntity;
  private PlayerEntity playerEntity;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = "/players";
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

    playerEntity = PlayerEntity.builder()
        .firstName("victor")
        .username("victor")
        .character(characterEntity)
        .lastName("borzaquel")
        .build();

    playerEntity = playerRepository.save(playerEntity);
  }

  @Test
  void create() {
    CreatePlayerDto dto = CreatePlayerDto.builder()
        .username("victorborzaquel")
        .firstName("hugo")
        .characterSlug("hero")
        .lastName("borzaquel")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("username", equalTo(dto.getUsername()))
        .body("firstName", equalTo(dto.getFirstName()))
        .body("lastName", equalTo(dto.getLastName()))
        .body("character.slug", equalTo(dto.getCharacterSlug()));

    Optional<PlayerEntity> result = playerRepository.findByUsernameIgnoreCase(dto.getUsername());

    assertEquals(dto.getUsername(), result.get().getUsername());
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
        .body("id", equalTo(playerEntity.getId().toString()))
        .body("username", equalTo(playerEntity.getUsername()))
        .body("firstName", equalTo(playerEntity.getFirstName()))
        .body("lastName", equalTo(playerEntity.getLastName()))
        .body("character.slug", equalTo(playerEntity.getCharacter().getSlug()));
  }

  @Test
  void findOne() {
    given()
        .when()
        .get(playerEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(playerEntity.getId().toString()))
        .body("username", equalTo(playerEntity.getUsername()))
        .body("firstName", equalTo(playerEntity.getFirstName()))
        .body("lastName", equalTo(playerEntity.getLastName()))
        .body("character.slug", equalTo(playerEntity.getCharacter().getSlug()));
  }

  @Test
  void findOneByUsername() {
    given()
        .when()
        .get("username/" + playerEntity.getUsername())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(playerEntity.getId().toString()))
        .body("username", equalTo(playerEntity.getUsername()))
        .body("firstName", equalTo(playerEntity.getFirstName()))
        .body("lastName", equalTo(playerEntity.getLastName()))
        .body("character.slug", equalTo(playerEntity.getCharacter().getSlug()));
  }

  @Test
  void update() {
    UpdatePlayerDto dto = UpdatePlayerDto.builder()
        .username("victor")
        .firstName("hugo")
        .characterSlug("hero")
        .lastName("borzaquel")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put(playerEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("username", equalTo(dto.getUsername()))
        .body("firstName", equalTo(dto.getFirstName()))
        .body("lastName", equalTo(dto.getLastName()))
        .body("character.slug", equalTo(dto.getCharacterSlug()))
        .body("id", equalTo(playerEntity.getId().toString()));

    Optional<PlayerEntity> result = playerRepository.findById(playerEntity.getId());

    assertEquals(Optional.of(playerEntity), result);
    assertEquals(dto.getUsername(), result.get().getUsername());
    assertEquals(dto.getFirstName(), result.get().getFirstName());
    assertEquals(dto.getLastName(), result.get().getLastName());
    assertEquals(dto.getCharacterSlug(), result.get().getCharacter().getSlug());
  }

  @Test
  void updateByUsername() {
    UpdatePlayerDto dto = UpdatePlayerDto.builder()
        .username("victor")
        .firstName("hugo")
        .characterSlug("hero")
        .lastName("borzaquel")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put("username/" + playerEntity.getUsername())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("username", equalTo(dto.getUsername()))
        .body("firstName", equalTo(dto.getFirstName()))
        .body("lastName", equalTo(dto.getLastName()))
        .body("character.slug", equalTo(dto.getCharacterSlug()))
        .body("id", equalTo(playerEntity.getId().toString()));

    Optional<PlayerEntity> result = playerRepository.findById(playerEntity.getId());

    assertEquals(Optional.of(playerEntity), result);
    assertEquals(dto.getUsername(), result.get().getUsername());
    assertEquals(dto.getFirstName(), result.get().getFirstName());
    assertEquals(dto.getLastName(), result.get().getLastName());
    assertEquals(dto.getCharacterSlug(), result.get().getCharacter().getSlug());
  }

  @Test
  void delete() {
    given()
        .when()
        .delete(playerEntity.getId().toString())
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());

    Optional<PlayerEntity> result = playerRepository.findById(playerEntity.getId());

    assertEquals(Optional.empty(), result);
  }

  @Test
  void deleteByUsername() {
    given()
        .when()
        .delete("username/" + playerEntity.getUsername())
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());

    Optional<PlayerEntity> result = playerRepository.findByUsernameIgnoreCase(playerEntity.getUsername());

    assertEquals(Optional.empty(), result);
  }
}
