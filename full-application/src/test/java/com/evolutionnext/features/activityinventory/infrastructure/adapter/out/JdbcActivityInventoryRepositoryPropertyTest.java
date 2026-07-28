package com.evolutionnext.features.activityinventory.infrastructure.adapter.out;

import com.evolutionnext.features.activityinventory.arbitrary.ActivityInventoryArbitrary;
import com.evolutionnext.features.activityinventory.domain.model.ActivityInventory;
import com.evolutionnext.features.activityinventory.domain.model.ActivityName;
import com.evolutionnext.features.activityinventory.domain.model.ActivityPriority;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.testcontainers.Container;
import net.jqwik.testcontainers.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
class JdbcActivityInventoryRepositoryPropertyTest {
    @Container(restartPerTry = false)
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15.2");

    private final String userNamePrefix = "activity-jdbc-" + UUID.randomUUID() + "-";

    @Property(tries = 3)
    void everySavedActivityCanBeReadForItsUser(
        @ForAll("userNames") String generatedUserName,
        @ForAll("activityNames") String activityName) {
        var userName = userNamePrefix + generatedUserName;
        var repository = repository();
        var inventory = new ActivityInventory()
            .add(new ActivityName(activityName), ActivityPriority.HIGH);

        repository.save(userName, inventory);

        var reloadedInventory = repository().findByUserName(userName);
        assertThat(reloadedInventory.activities()).containsAll(inventory.activities());
    }

    private JdbcActivityInventoryRepository repository() {
        return new JdbcActivityInventoryRepository(
            POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }

    @Provide
    net.jqwik.api.Arbitrary<String> userNames() {
        return ActivityInventoryArbitrary.userNames();
    }

    @Provide
    net.jqwik.api.Arbitrary<String> activityNames() {
        return ActivityInventoryArbitrary.activityNames();
    }
}
