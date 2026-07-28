package com.evolutionnext.features.activityinventory.page;

import com.evolutionnext.e2e.support.BrowserSupport;
import com.evolutionnext.e2e.support.WorkspaceLayout;
import com.evolutionnext.features.account.page.DashboardPage;
import com.evolutionnext.features.activityinventory.component.ActivityComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public final class ActivityInventoryPage {
    private final BrowserSupport browser;

    public ActivityInventoryPage(BrowserSupport browser) {
        this.browser = browser;
    }

    public ActivityInventoryPage waitUntilVisible() {
        browser.waitForElement(By.name("activityName"));
        return this;
    }

    public ActivityInventoryPage addHighPriorityActivity(String activityName) {
        browser.waitForElement(By.name("activityName")).sendKeys(activityName);
        new Select(browser.waitForElement(By.name("priority"))).selectByValue("HIGH");
        browser.waitForElement(By.cssSelector(
            "form[action='/activity-inventory/activity'] button")).click();
        browser.waitForText(activityName);
        return this;
    }

    public ActivityComponent activityNamed(String activityName) {
        return browser.waitForElements(By.className("activity-inventory__item")).stream()
            .map(ActivityComponent::new)
            .filter(activity -> activity.name().equals(activityName))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Activity not shown: " + activityName));
    }

    public DashboardPage openDashboard() {
        browser.waitForElement(By.linkText("Dashboard")).click();
        return new DashboardPage(browser).waitUntilVisible();
    }

    public WorkspaceLayout workspaceLayout() {
        return new WorkspaceLayout(
            browser.bounds(By.className("workspace-layout")),
            browser.bounds(By.className("workspace-entry")),
            browser.bounds(By.className("workspace-main")));
    }
}
