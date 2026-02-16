package com.ultron.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

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

            extent.setSystemInfo("Project", "NopCommerce");
            extent.setSystemInfo("Tester", System.getProperty("user.name"));
            extent.setSystemInfo("Environment", "Stage");
        }

        return extent;
    }
}
