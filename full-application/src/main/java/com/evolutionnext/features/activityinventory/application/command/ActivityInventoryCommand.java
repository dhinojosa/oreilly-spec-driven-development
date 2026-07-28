package com.evolutionnext.features.activityinventory.application.command;

import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;

public sealed interface ActivityInventoryCommand permits ActivityInventoryCommand.AddActivity {
    record AddActivity(String userName,
                       String activityName,
                       ActivityPriority priority) implements ActivityInventoryCommand {
    }
}
