package com.evolutionnext.features.account.page;

import com.evolutionnext.e2e.support.BrowserSupport;
import com.evolutionnext.features.activityinventory.page.ActivityInventoryPage;
import com.evolutionnext.features.todotoday.page.TodoTodayPage;
import org.openqa.selenium.By;

public final class DashboardPage {
    private final BrowserSupport browser;

    public DashboardPage(BrowserSupport browser) {
        this.browser = browser;
    }

    public DashboardPage waitUntilVisible() {
        browser.waitForText("Dashboard");
        return this;
    }

    public boolean showsText(String text) {
        browser.waitForText(text);
        return true;
    }

    public WelcomePage logOut() {
        browser.waitForElement(By.cssSelector("form[action='/account/logout'] button")).click();
        return new WelcomePage(browser).waitUntilVisible();
    }

    public TodoTodayPage openTodoToday() {
        browser.waitForElement(By.linkText("Todo today page")).click();
        return new TodoTodayPage(browser).waitUntilVisible();
    }

    public ActivityInventoryPage openActivityInventory() {
        browser.waitForElement(By.linkText("Activity inventory page")).click();
        return new ActivityInventoryPage(browser).waitUntilVisible();
    }
}
