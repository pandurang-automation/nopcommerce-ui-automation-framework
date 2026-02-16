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
        int timeout = ConfigReader.getInstance().getImplicitWait();
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeout)
        );
    }

    public static WebElement waitForVisibility(By locator) {
        return getWait().until(
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
}
