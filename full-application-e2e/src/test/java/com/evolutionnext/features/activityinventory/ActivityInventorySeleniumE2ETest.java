package com.evolutionnext.features.activityinventory;

import com.evolutionnext.e2e.support.ApplicationBrowserSupport;
import com.evolutionnext.e2e.support.FullApplicationComposeSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityInventorySeleniumE2ETest {
    private final FullApplicationComposeSupport application = new FullApplicationComposeSupport();
    private final ApplicationBrowserSupport browser = new ApplicationBrowserSupport();

    @BeforeEach
    void startApplication() {
        application.start();
        browser.start(application.baseUri());
    }

    @AfterEach
    void stopApplication() {
        browser.close();
        application.close();
    }

    @Test
    void activityRemainsAfterLeavingAndReturningThroughTheBrowser() {
        var activityInventory = browser.registrationPage()
            .open()
            .register("activity-ui-" + UUID.randomUUID(), "correct-horse-battery-staple")
            .openActivityInventory()
            .addHighPriorityActivity("Call Mother");

        var returnedInventory = activityInventory.openDashboard().openActivityInventory();
        var activity = returnedInventory.activityNamed("Call Mother");

        assertThat(activity.name()).isEqualTo("Call Mother");
        assertThat(activity.priorityText()).isEqualTo("High priority");
    }
}
