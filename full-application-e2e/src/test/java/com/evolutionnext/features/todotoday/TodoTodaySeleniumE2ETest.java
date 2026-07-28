package com.evolutionnext.features.todotoday;

import com.evolutionnext.e2e.support.ApplicationBrowserSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TodoTodaySeleniumE2ETest {
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
    void browserFlowCanAddEstimateWarnAndCompleteATask() {
        var task = application.registrationPage()
            .open()
            .register("casey", "correct-horse-battery-staple")
            .openTodoToday()
            .addTask("Build conference workshop")
            .setEstimate(7);

        assertThat(task.hasLargeTaskWarning()).isTrue();

        task.setCompletedPomodoros(8).complete();

        assertThat(task.isComplete()).isTrue();
        assertThat(task.text()).contains("Build conference workshop");
        assertThat(task.text()).contains("Large task warning");
        assertThat(task.text()).contains("Completed over the estimate");
        assertThat(task.isStruckThrough()).isTrue();
    }
}
