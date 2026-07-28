package com.evolutionnext.features.activityinventory.application.command;

import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;

public sealed interface ActivityInventoryResult permits ActivityInventoryResult.ActivityAdded {
    record ActivityAdded(String activityName,
                         ActivityPriority priority) implements ActivityInventoryResult {
    }
}
