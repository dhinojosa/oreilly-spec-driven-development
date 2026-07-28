package com.evolutionnext.features.activityinventory.application.query;

import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;

import java.util.List;

public sealed interface ActivityInventoryQueryResult
    permits ActivityInventoryQueryResult.CurrentActivityInventoryView {
    record CurrentActivityInventoryView(List<ActivityView> activities)
        implements ActivityInventoryQueryResult {
    }

    record ActivityView(String activityName, ActivityPriority priority) {
    }
}
