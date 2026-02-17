package com.ultron.framework.driver;

import com.ultron.framework.config.ConfigReader;
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
 * DriverFactory
 *
 * Creates WebDriver instances based on browser type.
 * Supports:
 * - Multi-browser
 * - Headless mode (CI ready)
 * - Linux-safe execution
 */
public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browser) {

        WebDriver driver;
        boolean isHeadless = ConfigReader.getInstance().isHeadless();

        switch (browser.toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                // Window handling
                chromeOptions.addArguments("--start-maximized");

                // Disable unwanted browser popups
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-features=PasswordLeakDetection");

                chromeOptions.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false,
                        "profile.password_manager_leak_detection", false,
                        "profile.default_content_setting_values.notifications", 2
                ));

                // 🔥 CI-safe headless support
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }

                driver = new ChromeDriver(chromeOptions);
                break;

            case "edge":

                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");

                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                }

                driver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        return driver;
    }
}
