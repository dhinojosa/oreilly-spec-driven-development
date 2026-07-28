package com.evolutionnext.features.activityinventory.port.in;

import com.evolutionnext.features.activityinventory.application.query.ActivityInventoryQueryResult;

public interface LoggedInActivityInventoryQueryPort {
    ActivityInventoryQueryResult currentView(String userName);
}
