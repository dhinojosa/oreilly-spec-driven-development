package com.evolutionnext.features.todotoday;

import com.evolutionnext.e2e.support.ApplicationBrowserSupport;
import com.evolutionnext.e2e.support.FullApplicationComposeSupport;
import com.evolutionnext.e2e.support.WorkspaceLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticatedWorkspaceLayoutSeleniumE2ETest {
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
    void entryAndMainPanelsUseDesktopAndNarrowWorkspaceLayouts() {
        browser.setViewport(1440, 1000);
        var todoToday = browser.registrationPage()
            .open()
            .register("workspace-ui-" + UUID.randomUUID(), "correct-horse-battery-staple")
            .openTodoToday();

        assertDesktopWorkspace(todoToday.workspaceLayout());

        var activityInventory = todoToday.openActivityInventory();
        assertDesktopWorkspace(activityInventory.workspaceLayout());

        browser.setViewport(600, 900);
        assertNarrowWorkspace(activityInventory.workspaceLayout());

        var narrowTodoToday = activityInventory.openDashboard().openTodoToday();
        assertNarrowWorkspace(narrowTodoToday.workspaceLayout());
    }

    private static void assertDesktopWorkspace(WorkspaceLayout layout) {
        assertThat(layout.entryPanel().x()).isLessThan(layout.mainPanel().x());
        var contentWidth = layout.entryPanel().width() + layout.mainPanel().width();
        var entryShare = (double) layout.entryPanel().width() / contentWidth;
        assertThat(entryShare).isBetween(0.23, 0.27);
    }

    private static void assertNarrowWorkspace(WorkspaceLayout layout) {
        assertThat(layout.entryPanel().y()).isLessThan(layout.mainPanel().y());
        assertThat(layout.entryPanel().width()).isEqualTo(layout.mainPanel().width());
    }
}
