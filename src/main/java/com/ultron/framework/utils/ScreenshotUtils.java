package com.ultron.framework.utils;

import com.ultron.framework.driver.DriverManager;
import com.ultron.framework.config.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private ScreenshotUtils() {
        // Prevent instantiation
    }

    public static String captureScreenshot(String testName,
                                           String dataInfo,
                                           int retryCount) {

        TakesScreenshot ts =
                (TakesScreenshot) DriverManager.getDriver();

        File source = ts.getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        String browser = ConfigReader.getInstance().getBrowser();

        String screenshotDir = "target/screenshots";

        String fileName = testName
                + (dataInfo != null ? "_" + dataInfo : "")
                + "_" + browser
                + "_retry" + retryCount
                + "_" + timestamp
                + ".png";

        String filePath = screenshotDir + "/" + fileName;

        try {
            File directory = new File(screenshotDir);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new RuntimeException("Failed to create screenshot directory");
            }

            FileUtils.copyFile(source, new File(filePath));

        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot", e);
        }

        return "screenshots/" + fileName;
    }
}