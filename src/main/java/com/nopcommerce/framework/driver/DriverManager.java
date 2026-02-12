package com.nopcommerce.framework.driver;

import org.openqa.selenium.WebDriver;

/**
 * DriverManager is responsible for managing WebDriver instances
 * in a thread-safe manner using ThreadLocal.
 * <p>
 * Why ThreadLocal?
 * ----------------
 * When tests run in parallel, each test thread must have its own
 * independent WebDriver instance. If we use a static WebDriver,
 * multiple threads will overwrite the same driver reference,
 * leading to flaky and unstable test execution.
 * <p>
 * Responsibilities:
 * -----------------
 * - Store WebDriver instance per thread
 * - Provide access to the current thread's WebDriver
 * - Quit and clean up WebDriver safely
 * <p>
 * It does NOT:
 * -------------
 * - Create WebDriver (DriverFactory handles creation)
 * - Decide browser type
 * - Handle test lifecycle
 * <p>
 * Design Principles Applied:
 * ---------------------------
 * - Single Responsibility Principle (SRP)
 * - Thread-safe driver handling
 * - Utility class pattern (private constructor + static methods)
 */
public class DriverManager {

    /**
     * ThreadLocal container to store WebDriver per test thread.
     * Each thread will have its own isolated WebDriver instance.
     */
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Private constructor to prevent instantiation.
     * This class should not be instantiated.
     */
    private DriverManager() {
    }

    /**
     * Returns the WebDriver instance associated with the current thread.
     *
     * @return WebDriver for the current thread
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Stores the WebDriver instance for the current thread.
     *
     * @param webDriver WebDriver instance to associate with thread
     */
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    /**
     * Quits the WebDriver associated with the current thread
     * and removes it from ThreadLocal storage.
     * <p>
     * Why remove()?
     * -------------
     * Prevents memory leaks by clearing the ThreadLocal reference
     * after the driver is quit.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
