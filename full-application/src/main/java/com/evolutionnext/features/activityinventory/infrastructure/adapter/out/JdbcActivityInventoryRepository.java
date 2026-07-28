package com.evolutionnext.features.activityinventory.infrastructure.adapter.out;

import com.evolutionnext.features.activityinventory.domain.model.Activity;
import com.evolutionnext.features.activityinventory.domain.model.ActivityId;
import com.evolutionnext.features.activityinventory.domain.model.ActivityInventory;
import com.evolutionnext.features.activityinventory.domain.model.ActivityName;
import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;
import com.evolutionnext.features.activityinventory.port.out.ActivityInventoryRepository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public final class JdbcActivityInventoryRepository implements ActivityInventoryRepository {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public JdbcActivityInventoryRepository(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        initialize();
    }

    @Override
    public ActivityInventory findByUserName(String userName) {
        var sql = """
            select activity_id, activity_name, priority
            from activity_inventory
            where user_name = ?
            order by activity_name, activity_id
            """;
        var activities = new ArrayList<Activity>();
        try (var connection = DriverManager.getConnection(jdbcUrl, username, password);
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    activities.add(new Activity(
                        new ActivityId((UUID) resultSet.getObject("activity_id")),
                        new ActivityName(resultSet.getString("activity_name")),
                        ActivityPriority.valueOf(resultSet.getString("priority"))));
                }
            }
            return ActivityInventory.restore(activities);
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to read activity inventory", exception);
        }
    }

    @Override
    public void save(String userName, ActivityInventory activityInventory) {
        var sql = """
            insert into activity_inventory(activity_id, user_name, activity_name, priority)
            values (?, ?, ?, ?)
            on conflict (activity_id) do update
            set user_name = excluded.user_name,
                activity_name = excluded.activity_name,
                priority = excluded.priority
            """;
        try (var connection = DriverManager.getConnection(jdbcUrl, username, password);
             var statement = connection.prepareStatement(sql)) {
            for (var activity : activityInventory.activities()) {
                statement.setObject(1, activity.activityId().value());
                statement.setString(2, userName);
                statement.setString(3, activity.activityName().value());
                statement.setString(4, activity.priority().name());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to save activity inventory", exception);
        }
    }

    private void initialize() {
        var sql = """
            create table if not exists activity_inventory(
                activity_id uuid primary key,
                user_name text not null,
                activity_name text not null,
                priority text not null
            )
            """;
        try (var connection = DriverManager.getConnection(jdbcUrl, username, password);
             var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new IllegalStateException("Unable to initialize activity inventory repository", exception);
        }
    }
}
