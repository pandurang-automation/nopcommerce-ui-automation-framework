package com.ultron.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;

import java.io.InputStream;

public class LocatorUtil {

    private static JsonNode rootNode;
    private static final String FILE_NAME = "locators.json";

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    LocatorUtil.class.getClassLoader()
                            .getResourceAsStream(FILE_NAME);

            if (inputStream == null) {
                throw new RuntimeException("Locator file not found: " + FILE_NAME);
            }

            rootNode = mapper.readTree(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load locator JSON file", e);
        }
    }

    public static By getLocator(String pageName, String elementName) {

        JsonNode elementNode = rootNode.get(pageName).get(elementName);

        if (elementNode == null) {
            throw new RuntimeException(
                    "Element not found: " + pageName + " -> " + elementName);
        }

        String type = elementNode.get("type").asText();
        String value = elementNode.get("value").asText();

        switch (type.toLowerCase()) {
            case "id":
                return By.id(value);
            case "name":
                return By.name(value);
            case "xpath":
                return By.xpath(value);
            case "classname":
                return By.className(value);
            case "css":
                return By.cssSelector(value);
            default:
                throw new RuntimeException("Invalid locator type: " + type);
        }
    }


}
