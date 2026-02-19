package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;


public class WdHubStatusTests extends TestBase{

    @Test
    public void statusTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void unauthorizedStatusTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void requiredKeysTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasKey("value"));
    }
    @Test
    public void requiredKeyReadyTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }


    @Test
    public void requiredValueTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", equalTo("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"));
    }



    @Test
    public void statusSchemaTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_schema.json"));
    }
}
