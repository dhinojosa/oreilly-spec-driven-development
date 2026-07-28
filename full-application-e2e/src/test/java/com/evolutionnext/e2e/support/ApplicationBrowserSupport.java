package com.evolutionnext.e2e.support;

import com.evolutionnext.AccountApplication;
import com.evolutionnext.features.account.infrastructure.adapter.out.InMemoryAccountRepository;
import com.evolutionnext.features.account.page.LoginPage;
import com.evolutionnext.features.account.page.RegistrationPage;
import com.sun.net.httpserver.HttpServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URI;

public final class ApplicationBrowserSupport implements AutoCloseable {
    private HttpServer server;
    private WebDriver browser;
    private BrowserSupport browserSupport;

    public void start() {
        server = new AccountApplication().start(0, new InMemoryAccountRepository());
        var baseUri = URI.create("http://localhost:" + server.getAddress().getPort());
        start(baseUri);
    }

    public void start(URI baseUri) {
        browser = new SafariDriver();
        browserSupport = new BrowserSupport(browser, baseUri);
    }

    public RegistrationPage registrationPage() {
        return new RegistrationPage(browserSupport);
    }

    public LoginPage loginPage() {
        return new LoginPage(browserSupport);
    }

    @Override
    public void close() {
        if (browser != null) {
            browser.quit();
        }
        if (server != null) {
            server.stop(0);
        }
    }
}
