package com.evolutionnext.e2e.support;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public final class BrowserSupport {
    private final WebDriver browser;
    private final URI baseUri;
    private final Duration timeout;

    public BrowserSupport(WebDriver browser, URI baseUri) {
        this(browser, baseUri, Duration.ofSeconds(5));
    }

    BrowserSupport(WebDriver browser, URI baseUri, Duration timeout) {
        this.browser = browser;
        this.baseUri = baseUri;
        this.timeout = timeout;
    }

    public void open(String path) {
        browser.get(baseUri.resolve(path).toString());
    }

    public WebElement waitForElement(By selector) {
        return browserWait()
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.elementToBeClickable(selector));
    }

    public List<WebElement> waitForElements(By selector) {
        return browserWait()
            .ignoring(StaleElementReferenceException.class)
            .until(driver -> {
                var elements = driver.findElements(selector);
                return elements.isEmpty() ? null : elements;
            });
    }

    public void waitForText(String text) {
        browserWait()
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), text));
    }

    public void waitForInputValue(String cssSelector, String value) {
        browserWait()
            .ignoring(StaleElementReferenceException.class)
            .until(driver -> value.equals(
                driver.findElement(By.cssSelector(cssSelector)).getDomProperty("value")));
    }

    private WebDriverWait browserWait() {
        return new WebDriverWait(browser, timeout);
    }
}
