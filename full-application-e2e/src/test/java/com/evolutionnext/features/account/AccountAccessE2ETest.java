package com.evolutionnext.features.account;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountAccessE2ETest {
    private static final String APPLICATION_SERVICE = "full-application";
    private static final int APPLICATION_PORT = 8080;

    private ComposeContainer environment;

    @BeforeAll
    void startE2EEnvironment() {
        environment = new ComposeContainer(new File("docker-compose.yml"))
            .withPull(false)
            .withExposedService(
                APPLICATION_SERVICE,
                APPLICATION_PORT,
                Wait.forHttp("/health")
                    .forStatusCode(200)
                    .withStartupTimeout(Duration.ofSeconds(60)));
        environment.start();
    }

    @AfterAll
    void stopE2EEnvironment() {
        if (environment != null) {
            environment.stop();
        }
    }

    @Test
    void applicationHealthIsAvailable() {
        RestAssured.given()
            .baseUri("http://" + environment.getServiceHost(APPLICATION_SERVICE, APPLICATION_PORT))
            .port(environment.getServicePort(APPLICATION_SERVICE, APPLICATION_PORT))
            .when()
            .get("/health")
            .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }
}
