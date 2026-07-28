package com.evolutionnext.features.activityinventory.infrastructure.adapter.in;

import com.evolutionnext.AccountApplication;
import com.evolutionnext.features.account.infrastructure.adapter.out.InMemoryAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityInventoryHttpHandlerTest {
    private com.sun.net.httpserver.HttpServer server;
    private URI baseUri;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private String authenticationCookie;

    @BeforeEach
    void startServer() {
        server = new AccountApplication().start(0, new InMemoryAccountRepository());
        baseUri = URI.create("http://localhost:" + server.getAddress().getPort());
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
        authenticationCookie = null;
    }

    @Test
    void requiresALoggedInUser() throws Exception {
        var response = get("/activity-inventory");

        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void addedActivityRemainsAfterLeavingAndReturning() throws Exception {
        registerAndStayLoggedIn();

        var added = post("/activity-inventory/activity",
            "activityName=%s&priority=HIGH".formatted(encode("Call Mother")));
        get("/dashboard");
        var returned = get("/activity-inventory");

        assertThat(added.statusCode()).isEqualTo(200);
        assertThat(returned.body()).contains("Call Mother");
        assertThat(returned.body()).contains("High priority");
    }

    private void registerAndStayLoggedIn() throws Exception {
        post("/account/register", "userName=casey&password=correct-horse-battery-staple");
    }

    private HttpResponse<String> get(String path) throws Exception {
        var builder = HttpRequest.newBuilder(baseUri.resolve(path)).GET();
        addCookie(builder);
        return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> post(String path, String body) throws Exception {
        var builder = HttpRequest.newBuilder(baseUri.resolve(path))
            .header("Content-Type", "application/x-www-form-urlencoded");
        addCookie(builder);
        var response = httpClient.send(builder.POST(HttpRequest.BodyPublishers.ofString(body)).build(),
            HttpResponse.BodyHandlers.ofString());
        response.headers().firstValue("Set-Cookie").ifPresent(this::rememberAuthenticationCookie);
        return response;
    }

    private void addCookie(HttpRequest.Builder builder) {
        if (authenticationCookie != null) {
            builder.header("Cookie", authenticationCookie);
        }
    }

    private void rememberAuthenticationCookie(String setCookieHeader) {
        authenticationCookie = setCookieHeader.split(";", 2)[0];
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
