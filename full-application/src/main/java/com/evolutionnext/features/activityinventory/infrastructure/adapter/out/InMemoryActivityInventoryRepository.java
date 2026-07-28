package com.evolutionnext.features.activityinventory.infrastructure.adapter.out;

import com.evolutionnext.features.activityinventory.domain.model.ActivityInventory;
import com.evolutionnext.features.activityinventory.port.out.ActivityInventoryRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryActivityInventoryRepository implements ActivityInventoryRepository {
    private final Map<String, ActivityInventory> inventories = new ConcurrentHashMap<>();

    @Override
    public ActivityInventory findByUserName(String userName) {
        return inventories.getOrDefault(userName, new ActivityInventory());
    }

    @Override
    public void save(String userName, ActivityInventory activityInventory) {
        inventories.put(userName, activityInventory);
    }
}
