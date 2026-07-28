package com.evolutionnext.e2e.support;

import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.net.URI;
import java.time.Duration;

public final class FullApplicationComposeSupport implements AutoCloseable {
    private static final String APPLICATION_SERVICE = "full-application";
    private static final int APPLICATION_PORT = 8080;

    private ComposeContainer environment;

    public void start() {
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

    public URI baseUri() {
        return URI.create("http://%s:%d".formatted(
            environment.getServiceHost(APPLICATION_SERVICE, APPLICATION_PORT),
            environment.getServicePort(APPLICATION_SERVICE, APPLICATION_PORT)));
    }

    @Override
    public void close() {
        if (environment != null) {
            environment.stop();
        }
    }
}
