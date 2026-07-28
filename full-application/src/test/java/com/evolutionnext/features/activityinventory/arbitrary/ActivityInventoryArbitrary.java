package com.evolutionnext.features.activityinventory.arbitrary;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

public final class ActivityInventoryArbitrary {
    private ActivityInventoryArbitrary() {
    }

    public static Arbitrary<String> activityNames() {
        return Arbitraries.strings()
            .alpha()
            .ofMinLength(3)
            .ofMaxLength(40)
            .map(value -> "Activity " + value);
    }

    public static Arbitrary<String> userNames() {
        return Arbitraries.strings()
            .alpha()
            .ofMinLength(3)
            .ofMaxLength(20)
            .map(value -> "activity-user-" + value);
    }
}
