package com.ultron.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultron.framework.config.ConfigReader;
import org.openqa.selenium.By;

import java.io.InputStream;

public class LocatorUtil {

    private static JsonNode rootNode;

    static {
        loadLocators();
    }

    private static void loadLocators() {
        try {
            String application = ConfigReader.getInstance().getApplication();

            if (application == null || application.isBlank()) {
                throw new RuntimeException("Application not defined in config file.");
            }

            String filePath = "applications/" + application + "/locators.json";

            InputStream inputStream =
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new RuntimeException("Locator file not found: " + filePath);
            }

            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readTree(inputStream);

            System.out.println("Loaded locators for application: " + application);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load locator JSON file", e);
        }
    }

    public static By getLocator(String pageName, String elementName) {

        JsonNode pageNode = rootNode.get(pageName);

        if (pageNode == null) {
            throw new RuntimeException("Page not found in locators: " + pageName);
        }

        JsonNode elementNode = pageNode.get(elementName);

        if (elementNode == null) {
            throw new RuntimeException(
                    "Element not found: " + pageName + " -> " + elementName
            );
        }

        String type = elementNode.get("type").asText();
        String value = elementNode.get("value").asText();

        return switch (type.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "xpath" -> By.xpath(value);
            case "classname" -> By.className(value);
            case "css" -> By.cssSelector(value);
            default -> throw new RuntimeException("Invalid locator type: " + type);
        };
    }
}
