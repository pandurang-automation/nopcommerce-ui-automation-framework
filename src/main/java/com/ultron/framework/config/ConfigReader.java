package com.ultron.framework.config;

import com.ultron.framework.exceptions.ConfigException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigReader
 *
 * Centralized configuration management.
 *
 * Responsibilities:
 * - Loads environment-specific configuration file
 * - Provides access to configuration values
 * - Ensures single instance (Singleton pattern)
 * - Supports system property overrides (CI-friendly)
 */
public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    private static volatile ConfigReader instance;
    private Properties properties;

    private ConfigReader() {
        loadProperties();
    }

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

    private void loadProperties() {
        properties = new Properties();

        String env = System.getProperty("env");

        if (env == null || env.isBlank()) {
            env = "qa";
        }

        List<String> allowedEnvs = Arrays.asList("dev", "qa", "stage");

        if (!allowedEnvs.contains(env)) {
            throw new ConfigException(
                    "Invalid environment: " + env + ". Allowed values: dev, qa, stage"
            );
        }

        String fileName = "config/config-" + env + ".properties";

        try (InputStream inputStream =
                     Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new ConfigException("Configuration file not found: " + fileName);
            }

            properties.load(inputStream);
            logger.info("Loaded configuration for environment: {}", env);

        } catch (IOException e) {
            throw new ConfigException(
                    "Failed to load configuration file for environment: " + env, e
            );
        }
    }

    // =========================
    // Getters
    // =========================

    public String getBrowser() {
        return System.getProperty("browser",
                properties.getProperty("browser", "chrome"));
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    public String getApplication() {
        return properties.getProperty("application");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }


    public int getExplicitWait() {
        return Integer.parseInt(
                properties.getProperty("explicitWait", "10")
        );
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(
                properties.getProperty("pageLoadTimeout", "30")
        );
    }

    /**
     * Headless mode controlled via system property.
     * Example:
     * mvn clean test -Dheadless=true
     */
    public boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
    }
}
