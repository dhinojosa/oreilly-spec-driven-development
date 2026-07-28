package com.evolutionnext.e2e.support;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

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

    public void setViewport(int width, int height) {
        browser.manage().window().setSize(new Dimension(width, height));
    }

    public ElementBounds bounds(By selector) {
        var rectangle = withDiagnostics("element to be visible: " + selector, () ->
            browserWait()
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(selector)))
            .getRect();
        return new ElementBounds(
            rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public WebElement waitForElement(By selector) {
        return withDiagnostics("element to be clickable: " + selector, () ->
            browserWait()
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(selector)));
    }

    public List<WebElement> waitForElements(By selector) {
        return withDiagnostics("elements to be present: " + selector, () ->
            browserWait()
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> {
                    var elements = driver.findElements(selector);
                    return elements.isEmpty() ? null : elements;
                }));
    }

    public void waitForText(String text) {
        withDiagnostics("body text to contain: " + text, () ->
            browserWait()
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), text)));
    }

    public void waitForInputValue(String cssSelector, String value) {
        withDiagnostics("input value to equal: " + value, () ->
            browserWait()
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> value.equals(
                    driver.findElement(By.cssSelector(cssSelector)).getDomProperty("value"))));
    }

    private WebDriverWait browserWait() {
        return new WebDriverWait(browser, timeout);
    }

    private <T> T withDiagnostics(String expectation, Supplier<T> wait) {
        try {
            return wait.get();
        } catch (TimeoutException exception) {
            var bodyText = browser.findElement(By.tagName("body")).getText();
            var pageSource = browser.getPageSource();
            if (pageSource.length() > 4_000) {
                pageSource = pageSource.substring(0, 4_000) + "\n[page source truncated]";
            }
            throw new TimeoutException("""
                Timed out waiting for %s
                Current URL: %s
                Visible body text:
                %s
                Page source:
                %s
                """.formatted(expectation, browser.getCurrentUrl(), bodyText, pageSource), exception);
        }
    }
}
