package com.ultron.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverFactory is responsible for creating WebDriver instances
 * based on the browser type provided.
 * <p>
 * Responsibilities:
 * - Setup browser driver binaries using WebDriverManager
 * - Apply browser-specific options
 * - Return a ready-to-use WebDriver instance
 * <p>
 * It does NOT:
 * - Store WebDriver (DriverManager handles that)
 * - Quit WebDriver (DriverManager handles lifecycle)
 * <p>
 * This design ensures:
 * - Clean separation of concerns
 * - Easy multi-browser support
 * - Scalability for CI/CD and parallel execution
 */
public class DriverFactory {

    // Prevent instantiation (Utility class pattern)
    private DriverFactory() {
    }

    /**
     * Creates a WebDriver instance based on browser name.
     *
     * @param browser Browser type (chrome, edge, firefox)
     * @return WebDriver instance
     */
    public static WebDriver createDriver(String browser) {

        WebDriver driver;

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");

                driver = new ChromeDriver(chromeOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");

                driver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        return driver;
    }
}
