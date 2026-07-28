package com.evolutionnext.features.account;

import com.evolutionnext.e2e.support.ApplicationBrowserSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountAccessSeleniumE2ETest {
    private final ApplicationBrowserSupport application = new ApplicationBrowserSupport();

    @BeforeEach
    void startApplication() {
        application.start();
    }

    @AfterEach
    void stopApplication() {
        application.close();
    }

    @Test
    void browserLoginShowsPersonalizedDashboardGreeting() {
        var dashboard = application.registrationPage()
            .open()
            .register("casey", "correct-horse-battery-staple");

        assertThat(dashboard.logOut().showsTagline()).isTrue();

        var personalizedDashboard = application.loginPage()
            .open()
            .logIn("casey", "correct-horse-battery-staple");

        assertThat(personalizedDashboard.showsText("Dashboard")).isTrue();
        assertThat(personalizedDashboard.showsText("Hello, Casey")).isTrue();
    }
}
