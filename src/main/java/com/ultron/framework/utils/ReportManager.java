package com.ultron.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ultron.framework.config.ConfigReader;

public class ReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            String reportPath = System.getProperty("user.dir")
                    + "/target/ExtentReport.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("UI Test Execution");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            ConfigReader config = ConfigReader.getInstance();

            extent.setSystemInfo("Project", config.getApplication());
            extent.setSystemInfo("Environment", System.getProperty("env", "qa"));
            extent.setSystemInfo("Browser", config.getBrowser());
            extent.setSystemInfo("Headless", String.valueOf(config.isHeadless()));
            extent.setSystemInfo("Tester", System.getProperty("user.name"));
        }

        return extent;
    }
}
