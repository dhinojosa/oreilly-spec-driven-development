package com.evolutionnext.features.account;

import com.evolutionnext.e2e.support.FullApplicationComposeSupport;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountAccessE2ETest {
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
    void applicationHealthIsAvailable() {
        RestAssured.given()
            .baseUri(application.baseUri().toString())
            .when()
            .get("/health")
            .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }
}
