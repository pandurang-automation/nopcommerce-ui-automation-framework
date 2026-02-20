package com.ultron.framework.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultron.framework.config.ConfigReader;
import com.ultron.framework.exceptions.DataException;

import java.io.InputStream;
import java.util.Iterator;

public class JsonDataReader {

    public static Object[][] getLoginData(String dataSetName) {

        try {
            String application = ConfigReader.getInstance().getApplication();
            String filePath = "applications/" + application + "/login-data.json";

            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new DataException("Test data file not found: " + filePath);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inputStream);

            JsonNode dataArray = rootNode.get(dataSetName);

            if (dataArray == null || !dataArray.isArray()) {
                throw new DataException("Dataset not found: " + dataSetName);
            }

            Object[][] data = new Object[dataArray.size()][2];

            int index = 0;
            Iterator<JsonNode> elements = dataArray.elements();

            while (elements.hasNext()) {
                JsonNode node = elements.next();

                data[index][0] = node.get("username").asText();
                data[index][1] = node.get("password").asText();

                index++;
            }

            return data;

        } catch (Exception e) {
            throw new DataException("Failed to read login test data", e);
        }
    }
}
