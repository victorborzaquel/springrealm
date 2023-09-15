package com.victorborzaquel.springrealm.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.victorborzaquel.springrealm.modules.dices.dto.RollDiceDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DiceControllerTest {
  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = "/dices";
    RestAssured.rootPath = "";
  }

  @Test
  void roll() {
    RollDiceDto dto = RollDiceDto.builder().quantityDices(2).quantityFaces(6).build();

    given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post("roll")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo("2d6"))
        .body("result", both(greaterThan(1)).and(lessThan(13)))
        .body("moves[0]", both(greaterThan(0)).and(lessThan(7)))
        .body("moves[1]", both(greaterThan(0)).and(lessThan(7)));
  }
}
