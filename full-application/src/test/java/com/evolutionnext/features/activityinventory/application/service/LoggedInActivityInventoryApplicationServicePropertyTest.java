package com.evolutionnext.features.activityinventory.application.service;

import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryCommand;
import com.evolutionnext.features.activityinventory.application.query.ActivityInventoryQueryResult;
import com.evolutionnext.features.activityinventory.arbitrary.ActivityInventoryArbitrary;
import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;
import com.evolutionnext.features.activityinventory.infrastructure.adapter.out.InMemoryActivityInventoryRepository;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static org.assertj.core.api.Assertions.assertThat;

class LoggedInActivityInventoryApplicationServicePropertyTest {
    @Property
    void everyAcceptedAddCommandIsVisibleThroughTheQueryPort(
        @ForAll("userNames") String userName,
        @ForAll("activityNames") String activityName) {
        var repository = new InMemoryActivityInventoryRepository();
        var commandService = new LoggedInActivityInventoryCommandApplicationService(repository);
        var queryService = new LoggedInActivityInventoryQueryApplicationService(repository);

        commandService.execute(new ActivityInventoryCommand.AddActivity(
            userName, activityName, ActivityPriority.HIGH));

        var view = (ActivityInventoryQueryResult.CurrentActivityInventoryView)
            queryService.currentView(userName);
        assertThat(view.activities()).anySatisfy(activity -> {
            assertThat(activity.activityName()).isEqualTo(activityName);
            assertThat(activity.priority()).isEqualTo(ActivityPriority.HIGH);
        });
    }

    @Provide
    net.jqwik.api.Arbitrary<String> userNames() {
        return ActivityInventoryArbitrary.userNames();
    }

    @Provide
    net.jqwik.api.Arbitrary<String> activityNames() {
        return ActivityInventoryArbitrary.activityNames();
    }
}
