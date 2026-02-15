package com.nopcommerce.tests.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import com.nopcommerce.framework.driver.DriverManager;
import com.nopcommerce.framework.utils.ScreenshotUtils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.nopcommerce.framework.utils.ReportManager;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ReportManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        try {

            String screenshotPath = ScreenshotUtils.captureScreenshot(result.getMethod().getMethodName());

            test.get().addScreenCaptureFromPath(screenshotPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
