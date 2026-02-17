package com.ultron.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

/**
 * DriverFactory is responsible for creating WebDriver instances
 * based on the browser type provided.
 *
 * Responsibilities:
 * - Setup browser driver binaries using WebDriverManager
 * - Apply browser-specific options
 * - Return a ready-to-use WebDriver instance
 *
 * This design ensures:
 * - Clean separation of concerns
 * - Easy multi-browser support
 * - Scalability for CI/CD and parallel execution
 */
public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browser) {

        WebDriver driver;

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                // Window
                chromeOptions.addArguments("--start-maximized");

                // Disable browser popups & password manager
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-save-password-bubble");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-features=PasswordLeakDetection");

                chromeOptions.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false,
                        "profile.password_manager_leak_detection", false,
                        "profile.default_content_setting_values.notifications", 2
                ));

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
