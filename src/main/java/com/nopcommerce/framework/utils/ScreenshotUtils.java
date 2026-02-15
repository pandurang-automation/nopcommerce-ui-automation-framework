package com.nopcommerce.framework.utils;

import com.nopcommerce.framework.driver.DriverManager;
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

    public static String captureScreenshot(String testName) {

        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        String screenshotDir = "target/screenshots";
        String fileName = testName + "_" + timestamp + ".png";
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

        return "screenshots/" + fileName;   //
    }

}
