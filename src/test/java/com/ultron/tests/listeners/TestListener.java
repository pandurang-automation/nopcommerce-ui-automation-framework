package com.ultron.tests.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.ultron.framework.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ultron.framework.utils.ReportManager;
import com.ultron.tests.retry.RetryAnalyzer;

public class TestListener implements ITestListener, IAnnotationTransformer {

    private static ExtentReports extent = ReportManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();


    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

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
