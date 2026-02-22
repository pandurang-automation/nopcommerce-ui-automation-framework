package com.ultron.tests.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ultron.framework.utils.ReportManager;
import com.ultron.framework.utils.ScreenshotUtils;
import com.ultron.tests.retry.RetryAnalyzer;

public class TestListener implements ITestListener, IAnnotationTransformer {

    private static final ExtentReports extent =
            ReportManager.getInstance();

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    @Override
    public void onTestStart(ITestResult result) {

        String methodName = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();

        String testName = methodName;

        if (parameters != null && parameters.length > 0) {
            testName = methodName + " - " + parameters[0];
        }

        ExtentTest extentTest = extent.createTest(testName);

        // 🔥 Assign TestNG groups as Extent categories
        String[] groups = result.getMethod().getGroups();
        if (groups != null) {
            for (String group : groups) {
                extentTest.assignCategory(group);
            }
        }

        test.set(extentTest);
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        boolean isRetry = result.getAttribute("retry") != null;

        if (isRetry) {

            test.get().warning("Test failed. Retrying...");

        } else {

            test.get().fail(result.getThrowable());

            try {

                String methodName = result.getMethod().getMethodName();

                Object[] parameters = result.getParameters();
                String dataInfo = null;

                if (parameters != null && parameters.length > 0) {
                    dataInfo = String.valueOf(parameters[0]);
                }

                int retryCount = result.getMethod().getCurrentInvocationCount();

                String screenshotPath =
                        ScreenshotUtils.captureScreenshot(
                                methodName,
                                dataInfo,
                                retryCount
                        );

                test.get().addScreenCaptureFromPath(screenshotPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}