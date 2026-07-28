package com.evolutionnext.features.activityinventory.port.out;

import com.evolutionnext.features.activityinventory.domain.model.ActivityInventory;

public interface ActivityInventoryRepository {
    ActivityInventory findByUserName(String userName);

    void save(String userName, ActivityInventory activityInventory);
}
