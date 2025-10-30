package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class EntryResourceTest {

  @Test
  void testIndexEndpoint () {
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
      .statusCode(200)
      .body("id", notNullValue())
      .body("checkIn", equalTo("2025-10-24T08:30:00"))
      .body("checkOut", equalTo("2025-10-24T18:30:00"))
      .body("category.id", equalTo(1))
      .body("category.name", equalTo("category1"))
      .body("tags", hasSize(0));
  }

}
