package com.evolutionnext.features.activityinventory.domain.model;

import java.util.ArrayList;
import java.util.List;

public final class ActivityInventory {
    private final List<Activity> activities;

    public ActivityInventory() {
        this(List.of());
    }

    private ActivityInventory(List<Activity> activities) {
        this.activities = List.copyOf(activities);
    }

    public static ActivityInventory restore(List<Activity> activities) {
        return new ActivityInventory(activities);
    }

    public ActivityInventory add(ActivityName activityName, ActivityPriority priority) {
        var updatedActivities = new ArrayList<>(activities);
        updatedActivities.add(new Activity(ActivityId.newId(), activityName, priority));
        return new ActivityInventory(updatedActivities);
    }

    public List<Activity> activities() {
        return activities;
    }
}
