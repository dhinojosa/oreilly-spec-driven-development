package com.evolutionnext.features.activityinventory.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class ActivityComponent {
    private final WebElement element;

    public ActivityComponent(WebElement element) {
        this.element = element;
    }

    public String name() {
        return element.findElement(By.className("activity-inventory__name")).getText();
    }

    public String priorityText() {
        return element.findElement(By.className("activity-inventory__priority")).getText();
    }
}
