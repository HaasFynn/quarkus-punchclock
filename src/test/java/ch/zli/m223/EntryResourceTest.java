package ch.zli.m223;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class EntryResourceTest {

  @Test
  public void testIndexEndpoint () {
    given()
      .when().get("/entries")
      .then()
      .statusCode(200);
  }

  @Test
  void testCreateEndpoint () {
    String json = """
      {
        "checkIn": "2025-10-24T08:30:00",
        "checkOut": "2025-10-24T18:30:00",
        "category": {"id": 1},
        "tags": []
      }
      """;

    given()
      .contentType(ContentType.JSON)
      .body(json)
      .when().post("/entries/create")
      .then()
      .statusCode(200);
  }

}
