package com.evolutionnext.features.account;

import com.evolutionnext.AccountApplication;
import com.evolutionnext.features.account.infrastructure.adapter.out.InMemoryAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

class AccountAccessSeleniumE2ETest {
    private com.sun.net.httpserver.HttpServer server;
    private URI baseUri;
    private WebDriver browser;

    @BeforeEach
    void startApplication() {
        server = new AccountApplication().start(0, new InMemoryAccountRepository());
        baseUri = URI.create("http://localhost:" + server.getAddress().getPort());
        browser = new SafariDriver();
    }

    @AfterEach
    void stopApplication() {
        if (browser != null) {
            browser.quit();
        }
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void browserLoginShowsPersonalizedDashboardGreeting() {
        browser.get(baseUri.resolve("/account/register").toString());
        waitForElement(By.name("userName")).sendKeys("casey");
        waitForElement(By.name("password")).sendKeys("correct-horse-battery-staple");
        waitForElement(By.cssSelector("button[type='submit']")).click();
        waitForText("Dashboard");

        waitForElement(By.cssSelector("form[action='/account/logout'] button")).click();
        waitForText("One focused session at a time.");

        browser.get(baseUri.resolve("/account/login").toString());
        waitForElement(By.name("userName")).sendKeys("casey");
        waitForElement(By.name("password")).sendKeys("correct-horse-battery-staple");
        waitForElement(By.cssSelector("button[type='submit']")).click();

        waitForText("Dashboard");
        waitForText("Hello, Casey");
    }

    private void waitForText(String text) {
        new WebDriverWait(browser, Duration.ofSeconds(5))
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), text));
    }

    private WebElement waitForElement(By by) {
        return new WebDriverWait(browser, Duration.ofSeconds(5))
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.elementToBeClickable(by));
    }
}
