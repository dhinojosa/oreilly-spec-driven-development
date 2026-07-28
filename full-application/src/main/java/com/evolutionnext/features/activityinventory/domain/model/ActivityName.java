package com.evolutionnext.features.activityinventory.domain.model;

public record ActivityName(String value) {
    public ActivityName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Activity name is required");
        }
    }
}
