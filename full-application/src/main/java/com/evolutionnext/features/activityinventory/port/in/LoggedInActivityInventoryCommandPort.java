package com.evolutionnext.features.activityinventory.port.in;

import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryCommand;
import com.evolutionnext.features.activityinventory.application.command.ActivityInventoryResult;

public interface LoggedInActivityInventoryCommandPort {
    ActivityInventoryResult execute(ActivityInventoryCommand command);
}
