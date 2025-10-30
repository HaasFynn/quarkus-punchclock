package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class EntryResourceIT extends EntryResourceTest {

  @Test
  void testIndexEndpoint () {
    given()
      .when().get("/entries")
      .then()
      .statusCode(200);
  }

  @Test
  void testCreateEndpoint () {
    createCategory();

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
      .body("category.id", equalTo(1))
      .body("checkIn", equalTo("2025-10-24T08:30:00"))
      .body("checkOut", equalTo("2025-10-24T18:30:00"))
      .body("tags", hasSize(0));
  }

  private void createCategory () {
    String json = """
      {
        "name": "category1"
      }
      """;

    given()
      .contentType(ContentType.JSON)
      .body(json)
      .when().post("/categories/create")
      .then()
      .statusCode(200);
  }
}
