package com.evolutionnext.features.activityinventory.application.service;

import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryCommand;
import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryResult;
import com.evolutionnext.features.activityinventory.domain.model.ActivityName;
import com.evolutionnext.features.activityinventory.port.in.LoggedInActivityInventoryCommandPort;
import com.evolutionnext.features.activityinventory.port.out.ActivityInventoryRepository;

public final class LoggedInActivityInventoryCommandApplicationService
    implements LoggedInActivityInventoryCommandPort {
    private final ActivityInventoryRepository repository;

    public LoggedInActivityInventoryCommandApplicationService(ActivityInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActivityInventoryResult execute(ActivityInventoryCommand command) {
        return switch (command) {
            case ActivityInventoryCommand.AddActivity(var userName, var activityName, var priority) -> {
                var activityInventory = repository.findByUserName(userName)
                    .add(new ActivityName(activityName), priority);
                repository.save(userName, activityInventory);
                yield new ActivityInventoryResult.ActivityAdded(activityName, priority);
            }
        };
    }
}
