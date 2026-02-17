package com.ultron.framework.base;

import com.ultron.framework.driver.DriverManager;
import com.ultron.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * BasePage
 *
 * Parent class for all Page Objects.
 * Provides common interaction methods with centralized wait handling.
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

    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtils.waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
