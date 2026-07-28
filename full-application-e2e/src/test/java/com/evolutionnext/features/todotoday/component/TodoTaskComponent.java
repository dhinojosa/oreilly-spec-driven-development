package com.evolutionnext.features.todotoday.component;

import com.evolutionnext.e2e.support.BrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class TodoTaskComponent {
    private final BrowserSupport browser;
    private final String taskName;

    public TodoTaskComponent(BrowserSupport browser, String taskName) {
        this.browser = browser;
        this.taskName = taskName;
    }

    public TodoTaskComponent setEstimate(int estimatedPomodoros) {
        var form = task().findElement(By.cssSelector("form[action='/todo-today/task/estimate']"));
        var input = form.findElement(By.name("estimatedPomodoros"));
        input.clear();
        input.sendKeys(Integer.toString(estimatedPomodoros));
        form.findElement(By.tagName("button")).click();
        browser.waitForInputValue(
            "form[action='/todo-today/task/estimate'] input[name='estimatedPomodoros']",
            Integer.toString(estimatedPomodoros));
        return this;
    }

    public TodoTaskComponent setCompletedPomodoros(int completedPomodoros) {
        var form = task().findElement(By.cssSelector(
            "form[action='/todo-today/task/completed-pomodoros']"));
        var input = form.findElement(By.name("completedPomodoros"));
        input.clear();
        input.sendKeys(Integer.toString(completedPomodoros));
        form.findElement(By.tagName("button")).click();
        browser.waitForInputValue(
            "form[action='/todo-today/task/completed-pomodoros'] input[name='completedPomodoros']",
            Integer.toString(completedPomodoros));
        return this;
    }

    public TodoTaskComponent complete() {
        task()
            .findElement(By.cssSelector("form[action='/todo-today/task/complete'] button"))
            .click();
        browser.waitForText("Completed");
        return this;
    }

    public boolean hasLargeTaskWarning() {
        return text().contains("Large task warning");
    }

    public boolean isComplete() {
        return task().getAttribute("class").contains("todo-task--complete");
    }

    public String text() {
        return task().getText();
    }

    public boolean isStruckThrough() {
        return textDecorations(task()).stream().anyMatch(value -> value.contains("line-through"));
    }

    private WebElement task() {
        return browser.waitForElements(By.cssSelector(".todo-task")).stream()
            .filter(element -> element.getText().contains(taskName))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Todo task is not visible: " + taskName));
    }

    private static List<String> textDecorations(WebElement element) {
        return List.of(
            element.getCssValue("text-decoration"),
            element.getCssValue("text-decoration-line"));
    }
}
