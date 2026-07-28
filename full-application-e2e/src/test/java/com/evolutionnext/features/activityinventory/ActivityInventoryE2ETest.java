package com.evolutionnext.features.activityinventory;

import com.evolutionnext.e2e.support.FullApplicationComposeSupport;
import io.restassured.filter.cookie.CookieFilter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivityInventoryE2ETest {
    private final FullApplicationComposeSupport application = new FullApplicationComposeSupport();

    @BeforeAll
    void startE2EEnvironment() {
        application.start();
    }

    @AfterAll
    void stopE2EEnvironment() {
        application.close();
    }

    @Test
    void savedActivityRemainsAvailableThroughThePackagedApplication() {
        var cookies = new CookieFilter();
        var userName = "activity-api-" + UUID.randomUUID();

        io.restassured.RestAssured.given()
            .baseUri(application.baseUri().toString())
            .filter(cookies)
            .contentType("application/x-www-form-urlencoded")
            .formParam("userName", userName)
            .formParam("password", "correct-horse-battery-staple")
            .when()
            .post("/account/register")
            .then()
            .statusCode(200);

        io.restassured.RestAssured.given()
            .baseUri(application.baseUri().toString())
            .filter(cookies)
            .contentType("application/x-www-form-urlencoded")
            .formParam("activityName", "Call Mother")
            .formParam("priority", "HIGH")
            .when()
            .post("/activity-inventory/activity")
            .then()
            .statusCode(200)
            .body(containsString("Call Mother"))
            .body(containsString("High priority"));

        io.restassured.RestAssured.given()
            .baseUri(application.baseUri().toString())
            .filter(cookies)
            .when()
            .get("/dashboard")
            .then()
            .statusCode(200);

        io.restassured.RestAssured.given()
            .baseUri(application.baseUri().toString())
            .filter(cookies)
            .when()
            .get("/activity-inventory")
            .then()
            .statusCode(200)
            .body(containsString("Call Mother"))
            .body(containsString("High priority"));
    }
}
