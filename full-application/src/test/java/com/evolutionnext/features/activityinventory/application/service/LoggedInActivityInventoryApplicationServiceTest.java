package com.evolutionnext.features.activityinventory.application.service;

import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryCommand;
import com.evolutionnext.features.activityinventory.application.query.ActivityInventoryQueryResult;
import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;
import com.evolutionnext.features.activityinventory.infrastructure.adapter.out.InMemoryActivityInventoryRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoggedInActivityInventoryApplicationServiceTest {
    @Test
    void activitiesAddedThroughTheCommandPortAreVisibleThroughTheQueryPort() {
        var repository = new InMemoryActivityInventoryRepository();
        var commandService = new LoggedInActivityInventoryCommandApplicationService(repository);
        var queryService = new LoggedInActivityInventoryQueryApplicationService(repository);

        commandService.execute(new ActivityInventoryCommand.AddActivity(
            "casey", "Call Mother", ActivityPriority.HIGH));

        var view = (ActivityInventoryQueryResult.CurrentActivityInventoryView)
            queryService.currentView("casey");
        assertThat(view.activities()).singleElement().satisfies(activity -> {
            assertThat(activity.activityName()).isEqualTo("Call Mother");
            assertThat(activity.priority()).isEqualTo(ActivityPriority.HIGH);
        });
    }
}
