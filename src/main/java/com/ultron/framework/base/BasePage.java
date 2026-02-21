package com.ultron.framework.base;

import com.ultron.framework.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger = LogManager.getLogger(BasePage.class);


    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WebElement find(By locator) {
        return WaitUtils.waitForVisibility(locator);
    }
    protected WebElement find(By locator, int timeoutInSeconds) {
        return WaitUtils.waitForVisibility(locator, timeoutInSeconds);
    }

    protected void click(By locator) {

        logger.info("Clicking on element: {}", locator);

        try {
            WaitUtils.waitForClickability(locator).click();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {

            logger.warn("Stale element detected. Retrying click for locator: {}", locator);

            WaitUtils.waitForClickability(locator).click();
        }
    }


    protected void type(By locator, String text) {

        logger.info("Typing into element: {}", locator);

        try {
            WebElement element = WaitUtils.waitForVisibility(locator);
            element.clear();
            element.sendKeys(text);

        } catch (org.openqa.selenium.StaleElementReferenceException e) {

            logger.warn("Stale element detected while typing. Retrying for locator: {}", locator);

            WebElement element = WaitUtils.waitForVisibility(locator);
            element.clear();
            element.sendKeys(text);
        }
    }


    protected String getText(By locator) {
        logger.info("Getting text from element: {}", locator);
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
