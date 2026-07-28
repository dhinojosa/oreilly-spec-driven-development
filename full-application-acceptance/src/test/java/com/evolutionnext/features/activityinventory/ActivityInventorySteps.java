package com.evolutionnext.features.activityinventory;

import com.evolutionnext.AccountApplication;
import com.evolutionnext.features.account.infrastructure.adapter.out.InMemoryAccountRepository;
import com.google.inject.Inject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public final class ActivityInventorySteps {
    private final ActivityInventoryScenarioState state;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Inject
    public ActivityInventorySteps(ActivityInventoryScenarioState state) {
        this.state = state;
    }

    @Before
    public void startApplication() {
        state.rememberServer(new AccountApplication().start(0, new InMemoryAccountRepository()));
    }

    @After
    public void stopApplication() {
        state.stopServer();
    }

    @Given("a logged-in user is on the dashboard")
    public void loggedInUserIsOnTheDashboard() throws Exception {
        registerAndStayLoggedIn();
        state.rememberLastResponse(get("/dashboard"));
        assertThat(state.lastResponse().body()).contains("Dashboard");
    }

    @When("the user opens the activity inventory page")
    public void userOpensTheActivityInventoryPage() throws Exception {
        state.rememberLastResponse(get("/activity-inventory"));
        assertThat(state.lastResponse().statusCode()).isEqualTo(200);
    }

    @And("the user adds an activity named {string} with high priority")
    public void userAddsAnActivityWithHighPriority(String activityName) throws Exception {
        addActivity(activityName);
    }

    @Then("the activity inventory shows {string}")
    public void activityInventoryShows(String activityName) {
        assertThat(state.lastResponse().body()).contains(activityName);
    }

    @And("{string} is shown as high priority")
    public void activityIsShownAsHighPriority(String activityName) {
        assertThat(state.lastResponse().body()).contains(activityName);
        assertThat(state.lastResponse().body()).contains("High priority");
    }

    @Given("a logged-in user has added {string} with high priority")
    public void loggedInUserHasAddedWithHighPriority(String activityName) throws Exception {
        registerAndStayLoggedIn();
        addActivity(activityName);
    }

    @When("the user leaves and returns to the activity inventory page")
    public void userLeavesAndReturnsToTheActivityInventoryPage() throws Exception {
        state.rememberLastResponse(get("/dashboard"));
        assertThat(state.lastResponse().body()).contains("Dashboard");
        state.rememberLastResponse(get("/activity-inventory"));
    }

    private void registerAndStayLoggedIn() throws Exception {
        state.rememberLastResponse(post(
            "/account/register", "userName=casey&password=correct-horse-battery-staple"));
    }

    private void addActivity(String activityName) throws Exception {
        state.rememberLastResponse(post("/activity-inventory/activity",
            "activityName=%s&priority=HIGH".formatted(encode(activityName))));
    }

    private HttpResponse<String> get(String path) throws Exception {
        return send(HttpRequest.newBuilder(state.baseUri().resolve(path)).GET());
    }

    private HttpResponse<String> post(String path, String body) throws Exception {
        return send(HttpRequest.newBuilder(state.baseUri().resolve(path))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(body)));
    }

    private HttpResponse<String> send(HttpRequest.Builder builder) throws Exception {
        if (state.authenticationCookie() != null) {
            builder.header("Cookie", state.authenticationCookie());
        }
        var response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        response.headers().firstValue("Set-Cookie")
            .map(header -> header.split(";", 2)[0])
            .ifPresent(state::rememberAuthenticationCookie);
        return response;
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
