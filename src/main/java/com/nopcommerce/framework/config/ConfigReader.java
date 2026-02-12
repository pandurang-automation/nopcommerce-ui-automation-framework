package com.nopcommerce.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader
 *
 * Architectural Responsibility:
 * ------------------------------
 * Centralized configuration management.
 *
 * This class:
 * - Loads configuration properties from config.properties file
 * - Provides getter methods for accessing configuration values
 * - Ensures configuration is loaded only once (Singleton pattern)
 *
 * Why Singleton?
 * --------------
 * - Prevents multiple file reads
 * - Ensures one consistent configuration state
 * - Improves performance and maintainability
 */
public class ConfigReader {

    private static ConfigReader instance;
    private Properties properties;

    // Private constructor to prevent external instantiation
    private ConfigReader() {
        loadProperties();
    }

    /**
     * Returns the single instance of ConfigReader.
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    /**
     * Loads properties from config.properties file.
     */
    private void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream =
                    new FileInputStream("src/main/resources/config/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getFrontendUrl() {
        return properties.getProperty("frontendUrl");
    }

    public String getAdminUrl() {
        return properties.getProperty("adminUrl");
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("pageLoadTimeout"));
    }
}
