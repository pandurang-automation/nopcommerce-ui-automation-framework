package com.ultron.framework.utils;

import com.ultron.framework.config.ConfigReader;
import com.ultron.framework.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitUtils
 * <p>
 * Architectural Responsibility:
 * -----------------------------
 * Centralized explicit wait handling for the framework.
 * <p>
 * Why This Class Exists:
 * ----------------------
 * - Avoid duplication of WebDriverWait logic
 * - Keep Page classes clean
 * - Maintain consistent timeout strategy
 * - Improve maintainability and scalability
 * <p>
 * Design Principles:
 * ------------------
 * - Separation of Concerns
 * - Single Responsibility Principle
 * - Reusable Utility Pattern
 */
public class WaitUtils {

    private WaitUtils() {
        // Prevent instantiation
    }

    private static WebDriverWait getWait() {

        int timeout = ConfigReader.getInstance().getExplicitWait();

        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeout)
        );

        wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);

        return wait;
    }
    private static WebDriverWait getWait(int timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeoutInSeconds)
        );

        wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);

        return wait;
    }

    public static WebElement waitForVisibility(By locator) {
        return getWait().until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }
    public static WebElement waitForVisibility(By locator, int timeoutInSeconds) {
        return getWait(timeoutInSeconds).until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public static WebElement waitForClickability(By locator) {
        return getWait().until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    public static boolean waitForUrlContains(String partialUrl) {
        return getWait().until(
                ExpectedConditions.urlContains(partialUrl)
        );
    }
    public static WebElement waitForPresence(By locator) {
        return getWait().until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }
    public static boolean waitForInvisibility(By locator) {
        return getWait().until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }
    public static boolean waitForTextToBePresent(By locator, String expectedText) {
        return getWait().until(
                ExpectedConditions.textToBePresentInElementLocated(locator, expectedText)
        );
    }
}
