package com.evolutionnext.features.activityinventory.application.service;

import com.evolutionnext.features.activityinventory.application.query.ActivityInventoryQueryResult;
import com.evolutionnext.features.activityinventory.port.in.LoggedInActivityInventoryQueryPort;
import com.evolutionnext.features.activityinventory.port.out.ActivityInventoryRepository;

public final class LoggedInActivityInventoryQueryApplicationService
    implements LoggedInActivityInventoryQueryPort {
    private final ActivityInventoryRepository repository;

    public LoggedInActivityInventoryQueryApplicationService(ActivityInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActivityInventoryQueryResult currentView(String userName) {
        var activities = repository.findByUserName(userName).activities().stream()
            .map(activity -> new ActivityInventoryQueryResult.ActivityView(
                activity.activityName().value(),
                activity.priority()))
            .toList();
        return new ActivityInventoryQueryResult.CurrentActivityInventoryView(activities);
    }
}
