package com.evolutionnext;

import com.evolutionnext.features.account.application.service.AnonymousAccountCommandApplicationService;
import com.evolutionnext.features.account.application.service.AnonymousAccountQueryApplicationService;
import com.evolutionnext.features.account.infrastructure.adapter.in.AccountHttpHandler;
import com.evolutionnext.features.account.port.out.AccountRepository;
import com.evolutionnext.features.activityinventory.application.service.LoggedInActivityInventoryCommandApplicationService;
import com.evolutionnext.features.activityinventory.application.service.LoggedInActivityInventoryQueryApplicationService;
import com.evolutionnext.features.activityinventory.infrastructure.adapter.in.ActivityInventoryHttpHandler;
import com.evolutionnext.features.activityinventory.infrastructure.adapter.out.InMemoryActivityInventoryRepository;
import com.evolutionnext.features.activityinventory.port.out.ActivityInventoryRepository;
import com.evolutionnext.features.todotoday.application.service.LoggedInTodoTodayCommandApplicationService;
import com.evolutionnext.features.todotoday.application.service.LoggedInTodoTodayQueryApplicationService;
import com.evolutionnext.features.todotoday.infrastructure.adapter.in.TodoTodayHttpHandler;
import com.evolutionnext.features.todotoday.infrastructure.adapter.out.InMemoryLoggedInTodoTodayRepository;
import com.evolutionnext.features.welcome.infrastructure.adapter.in.WelcomeHttpHandler;
import com.evolutionnext.http.HealthHandler;
import com.evolutionnext.http.ResourceLoader;
import com.evolutionnext.http.SafeHttpHandler;
import com.evolutionnext.http.StaticAssetHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class AccountApplication {
    public HttpServer start(int port, AccountRepository accountRepository) {
        return start(port, accountRepository, new InMemoryActivityInventoryRepository());
    }

    public HttpServer start(int port,
                            AccountRepository accountRepository,
                            ActivityInventoryRepository activityInventoryRepository) {
        try {
            var server = HttpServer.create(new InetSocketAddress(port), 0);
            var commandService = new AnonymousAccountCommandApplicationService(accountRepository);
            var queryService = new AnonymousAccountQueryApplicationService(accountRepository);
            var todoTodayRepository = new InMemoryLoggedInTodoTodayRepository();
            var todoTodayCommandService = new LoggedInTodoTodayCommandApplicationService(todoTodayRepository);
            var todoTodayQueryService = new LoggedInTodoTodayQueryApplicationService(todoTodayRepository);
            var activityInventoryCommandService =
                new LoggedInActivityInventoryCommandApplicationService(activityInventoryRepository);
            var activityInventoryQueryService =
                new LoggedInActivityInventoryQueryApplicationService(activityInventoryRepository);
            var resourceLoader = new ResourceLoader();
            server.createContext("/health", new SafeHttpHandler(new HealthHandler()));
            server.createContext("/assets", new SafeHttpHandler(new StaticAssetHandler(resourceLoader)));
            server.createContext("/account",
                new SafeHttpHandler(new AccountHttpHandler(commandService, queryService, resourceLoader)));
            server.createContext("/dashboard",
                new SafeHttpHandler(new AccountHttpHandler(commandService, queryService, resourceLoader)));
            server.createContext("/todo-today",
                new SafeHttpHandler(new TodoTodayHttpHandler(todoTodayCommandService, todoTodayQueryService, resourceLoader)));
            server.createContext("/activity-inventory",
                new SafeHttpHandler(new ActivityInventoryHttpHandler(
                    activityInventoryCommandService, activityInventoryQueryService, resourceLoader)));
            server.createContext("/record-sheet",
                new SafeHttpHandler(new AccountHttpHandler(commandService, queryService, resourceLoader)));
            server.createContext("/", new SafeHttpHandler(new WelcomeHttpHandler(resourceLoader)));
            server.start();
            return server;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to start application", exception);
        }
    }
}
