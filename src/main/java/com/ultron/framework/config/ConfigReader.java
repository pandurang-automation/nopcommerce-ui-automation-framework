package com.ultron.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
            throw new IllegalArgumentException(
                    "Invalid environment: " + env + ". Allowed values: dev, qa, stage"
            );
        }

        String fileName = "config/config-" + env + ".properties";

        try (InputStream inputStream =
                     Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + fileName);
            }

            properties.load(inputStream);
            System.out.println("Loaded configuration for environment: " + env);

        } catch (IOException e) {
            throw new RuntimeException(
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

    public int getImplicitWait() {
        return Integer.parseInt(
                properties.getProperty("implicitWait", "10")
        );
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
