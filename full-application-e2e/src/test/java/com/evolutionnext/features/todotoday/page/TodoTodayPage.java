package com.evolutionnext.features.todotoday.page;

import com.evolutionnext.e2e.support.BrowserSupport;
import com.evolutionnext.features.todotoday.component.TodoTaskComponent;
import org.openqa.selenium.By;

public final class TodoTodayPage {
    private final BrowserSupport browser;

    public TodoTodayPage(BrowserSupport browser) {
        this.browser = browser;
    }

    public TodoTodayPage waitUntilVisible() {
        browser.waitForElement(By.name("taskName"));
        return this;
    }

    public TodoTaskComponent addTask(String taskName) {
        browser.waitForElement(By.name("taskName")).sendKeys(taskName);
        browser.waitForElement(By.cssSelector("form[action='/todo-today/task'] button")).click();
        browser.waitForText(taskName);
        return new TodoTaskComponent(browser, taskName);
    }
}
