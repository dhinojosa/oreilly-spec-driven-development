package com.evolutionnext;

import com.evolutionnext.features.account.infrastructure.adapter.out.InMemoryAccountRepository;
import com.evolutionnext.features.account.infrastructure.adapter.out.JdbcAccountRepository;
import com.evolutionnext.features.account.port.out.AccountRepository;
import com.evolutionnext.features.activityinventory.infrastructure.adapter.out.InMemoryActivityInventoryRepository;
import com.evolutionnext.features.activityinventory.infrastructure.adapter.out.JdbcActivityInventoryRepository;
import com.evolutionnext.features.activityinventory.port.out.ActivityInventoryRepository;

public final class Runner {
    private Runner() {
    }

    public static void main(String[] args) {
        var port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        var repositories = repositories();
        new AccountApplication().start(
            port, repositories.accountRepository(), repositories.activityInventoryRepository());
    }

    private static Repositories repositories() {
        var databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isBlank()) {
            return new Repositories(
                new InMemoryAccountRepository(),
                new InMemoryActivityInventoryRepository());
        }
        var username = System.getenv().getOrDefault("DATABASE_USERNAME", "postgres");
        var password = System.getenv().getOrDefault("DATABASE_PASSWORD", "postgres");
        return new Repositories(
            new JdbcAccountRepository(databaseUrl, username, password),
            new JdbcActivityInventoryRepository(databaseUrl, username, password));
    }

    private record Repositories(AccountRepository accountRepository,
                                ActivityInventoryRepository activityInventoryRepository) {
    }
}
