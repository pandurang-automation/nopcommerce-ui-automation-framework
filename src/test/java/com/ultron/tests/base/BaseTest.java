package com.ultron.tests.base;

import com.ultron.framework.config.ConfigReader;
import com.ultron.framework.driver.DriverFactory;
import com.ultron.framework.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.ITestResult;
import com.ultron.framework.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ultron.tests.assertions.SoftAssertManager;

/**
 * BaseTest
 * <p>
 * Architectural Responsibility:
 * -----------------------------
 * Acts as the lifecycle controller for all test classes.
 * <p>
 * Ensures:
 * - Centralized driver initialization
 * - Thread-safe driver management
 * - Clean teardown
 * - No driver logic inside test classes
 * <p>
 * Design Principles Applied:
 * --------------------------
 * 1. Single Responsibility Principle
 * 2. Separation of Concerns
 * 3. Parallel Execution Ready (ThreadLocal)
 * <p>
 * All test classes must extend this class.
 */
public abstract class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    /**
     * Initializes WebDriver before each test method.
     * <p>
     * Why @BeforeMethod?
     * ------------------
     * - Ensures fresh browser per test
     * - Prevents state leakage
     * - Fully compatible with parallel execution
     */
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {

        ConfigReader config = ConfigReader.getInstance();

        // Prefer TestNG parameter if provided, else fallback to config file
        String browserName = (browser != null && !browser.isBlank()) ? browser : config.getBrowser();

        // Create driver using Factory Pattern
        WebDriver driver = DriverFactory.createDriver(browserName);

        // Store driver in ThreadLocal
        DriverManager.setDriver(driver);
        SoftAssertManager.init();

    }

    /**
     * Quits driver after each test method.
     * <p>
     * alwaysRun = true ensures cleanup
     * even if test fails or is skipped.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        SoftAssertManager.assertAll();

        DriverManager.quitDriver();
    }

}
