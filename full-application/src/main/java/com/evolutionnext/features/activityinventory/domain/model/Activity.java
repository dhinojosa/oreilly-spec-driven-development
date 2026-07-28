package com.evolutionnext.features.activityinventory.domain.model;

import java.util.Objects;

public record Activity(ActivityId activityId,
                       ActivityName activityName,
                       ActivityPriority priority) {
    public Activity {
        Objects.requireNonNull(activityId, "Activity id is required");
        Objects.requireNonNull(activityName, "Activity name is required");
        Objects.requireNonNull(priority, "Activity priority is required");
    }
}
