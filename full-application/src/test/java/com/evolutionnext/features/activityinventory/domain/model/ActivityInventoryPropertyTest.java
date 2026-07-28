package com.evolutionnext.features.activityinventory.domain.model;

import com.evolutionnext.features.activityinventory.arbitrary.ActivityInventoryArbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityInventoryPropertyTest {
    @Property
    void everyAddedActivityRemainsPresentWithItsPriority(@ForAll("activityNames") String activityName) {
        var inventory = new ActivityInventory()
            .add(new ActivityName(activityName), ActivityPriority.HIGH);

        assertThat(inventory.activities()).anySatisfy(activity -> {
            assertThat(activity.activityName().value()).isEqualTo(activityName);
            assertThat(activity.priority()).isEqualTo(ActivityPriority.HIGH);
        });
    }

    @Provide
    net.jqwik.api.Arbitrary<String> activityNames() {
        return ActivityInventoryArbitrary.activityNames();
    }
}
