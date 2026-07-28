package com.evolutionnext.features.activityinventory.domain.model;

import java.util.Objects;
import java.util.UUID;

public record ActivityId(UUID value) {
    public ActivityId {
        Objects.requireNonNull(value, "Activity id is required");
    }

    public static ActivityId newId() {
        return new ActivityId(UUID.randomUUID());
    }
}
