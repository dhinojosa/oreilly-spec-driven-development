package com.evolutionnext.features.activityinventory.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityInventoryTest {
    @Test
    void addsAndReadsAHighPriorityActivity() {
        var inventory = new ActivityInventory()
            .add(new ActivityName("Call Mother"), ActivityPriority.HIGH);

        assertThat(inventory.activities()).singleElement().satisfies(activity -> {
            assertThat(activity.activityName().value()).isEqualTo("Call Mother");
            assertThat(activity.priority()).isEqualTo(ActivityPriority.HIGH);
        });
    }
}
