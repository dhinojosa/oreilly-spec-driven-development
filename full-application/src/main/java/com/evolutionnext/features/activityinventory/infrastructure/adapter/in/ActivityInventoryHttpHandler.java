package com.evolutionnext.features.activityinventory.infrastructure.adapter.in;

import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryCommand;
import com.evolutionnext.features.activityinventory.application.query.ActivityInventoryQueryResult;
import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;
import com.evolutionnext.features.activityinventory.port.in.LoggedInActivityInventoryCommandPort;
import com.evolutionnext.features.activityinventory.port.in.LoggedInActivityInventoryQueryPort;
import com.evolutionnext.http.AuthCookies;
import com.evolutionnext.http.FormParser;
import com.evolutionnext.http.HttpResponses;
import com.evolutionnext.http.ResourceLoader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ActivityInventoryHttpHandler implements HttpHandler {
    private final LoggedInActivityInventoryCommandPort commandPort;
    private final LoggedInActivityInventoryQueryPort queryPort;
    private final ResourceLoader resourceLoader;

    public ActivityInventoryHttpHandler(LoggedInActivityInventoryCommandPort commandPort,
                                        LoggedInActivityInventoryQueryPort queryPort,
                                        ResourceLoader resourceLoader) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var userName = AuthCookies.authenticatedUserName(exchange);
        if (userName.isEmpty()) {
            HttpResponses.html(exchange, 401, resourceLoader.text("welcome/anonymous/index.html"));
            return;
        }

        var method = exchange.getRequestMethod();
        var path = exchange.getRequestURI().getPath();
        if ("GET".equals(method) && "/activity-inventory".equals(path)) {
            HttpResponses.html(exchange, 200, activityInventoryPage(userName.orElseThrow()));
        } else if ("POST".equals(method) && "/activity-inventory/activity".equals(path)) {
            addActivity(exchange, userName.orElseThrow());
        } else {
            HttpResponses.text(exchange, 404, "Not found");
        }
    }

    private void addActivity(HttpExchange exchange, String userName) throws IOException {
        var form = FormParser.parse(
            new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
        commandPort.execute(new ActivityInventoryCommand.AddActivity(
            userName,
            form.getOrDefault("activityName", ""),
            ActivityPriority.valueOf(form.getOrDefault("priority", "HIGH"))));
        HttpResponses.html(exchange, 200, activityInventoryPage(userName));
    }

    private String activityInventoryPage(String userName) {
        var currentView = (ActivityInventoryQueryResult.CurrentActivityInventoryView)
            queryPort.currentView(userName);
        var activityMarkup = currentView.activities().stream()
            .map(activity -> """
                <li class="activity-inventory__item">
                    <span class="activity-inventory__name">%s</span>
                    <span class="activity-inventory__priority">%s priority</span>
                </li>
                """.formatted(
                escapeHtml(activity.activityName()),
                titleCase(activity.priority())))
            .reduce("", String::concat);
        return resourceLoader.text("activityinventory/activity-inventory.html")
            .replace("{{ACTIVITY_EMPTY_STATE}}", currentView.activities().isEmpty()
                ? "<p class=\"activity-inventory__empty\">No activities yet.</p>"
                : "")
            .replace("{{ACTIVITIES}}", activityMarkup);
    }

    private static String titleCase(ActivityPriority priority) {
        var value = priority.name().toLowerCase();
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private static String escapeHtml(String value) {
        return value
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }
}
