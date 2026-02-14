package com.nopcommerce.framework.pages;

import com.nopcommerce.framework.driver.DriverManager;
import com.nopcommerce.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * BasePage
 *
 * Architectural Responsibility:
 * -----------------------------
 * Serves as the parent class for all Page Objects.
 *
 * Provides:
 * - Access to WebDriver
 * - Common UI interaction methods
 * - Centralized wait handling
 *
 * Why This Exists:
 * ----------------
 * - Avoid duplication across pages
 * - Keep page classes clean and readable
 * - Enforce consistent interaction strategy
 *
 * Design Principles:
 * ------------------
 * - Single Responsibility Principle
 * - DRY (Don't Repeat Yourself)
 * - Separation of Concerns
 */
public abstract class BasePage {

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WebElement find(By locator) {
        return WaitUtils.waitForVisibility(locator);
    }

    protected void click(By locator) {
        WaitUtils.waitForClickability(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = WaitUtils.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtils.waitForVisibility(locator).getText();
    }
}
