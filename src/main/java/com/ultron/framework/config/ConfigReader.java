package com.ultron.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * ConfigReader
 * <p>
 * Architectural Responsibility:
 * ------------------------------
 * Centralized configuration management.
 * <p>
 * This class:
 * - Loads configuration properties from environment-specific config file.
 * - Provides getter methods for accessing configuration values
 * - Ensures configuration is loaded only once (Singleton pattern)
 * <p>
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
     * Loads configuration properties from environment-specific config file.
     */
    private void loadProperties() {
        properties = new Properties();

        // Read environment from system property
        String env = System.getProperty("env");

        // Default to QA if not provided
        if (env == null || env.isBlank()) {
            env = "qa";
        }

        // Allowed environments
        List<String> allowedEnvs = Arrays.asList("dev", "qa", "stage");

        if (!allowedEnvs.contains(env)) {
            throw new IllegalArgumentException("Invalid environment: " + env + ". Allowed values: dev, qa, stage");
        }

        String fileName = "config/config-" + env + ".properties";

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + fileName);
            }

            properties.load(inputStream);
            System.out.println("Loaded configuration for environment: " + env);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file for environment: " + env, e);
        }
    }


    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getTestUserEmail() {
        return properties.getProperty("testUserEmail");
    }

    public String getTestUserPassword() {
        return properties.getProperty("testUserPassword");
    }

    public String getFrontendUrl() {
        return properties.getProperty("frontendUrl");
    }

    public String getAdminUrl() {
        return properties.getProperty("adminUrl");
    }

    public String getAdminEmail() {
        return properties.getProperty("adminEmail");
    }

    public String getAdminPassword() {
        return properties.getProperty("adminPassword");
    }


    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("pageLoadTimeout"));
    }
}
