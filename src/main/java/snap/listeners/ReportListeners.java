/*
 * Copyright (c) 2024 Arjit Yadav
 *
 * Permission is hereby granted to use, copy, modify, and distribute this code for any purpose, with or without
 * modifications, subject to the following conditions:
 *
 * 1. This notice shall be included in all copies or substantial portions of the code.
 * 2. Suggestions and improvements are welcome and can be submitted via pull requests or issues on the GitHub repository.
 *
 * THE CODE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT,
 * OR OTHERWISE, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE CODE OR THE USE OR OTHER DEALINGS IN THE CODE.
 */

package snap.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import snap.reports.ExtentReportManager;
import snap.utilities.WebDriverMgr;

public class ReportListeners implements ITestListener {

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.setupExtentReport();
        System.out.println("Report initialized.");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushExtentReport();
        System.out.println("Extent Report flushed.");
        openReportInBrowser(ExtentReportManager.extentReportFile);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String className = result.getMethod().getRealClass().getSimpleName();
        ExtentTest test = ExtentReportManager.createTest(className + "\n>> " + methodName);
        extentTest.set(test);
        test.log(Status.INFO, "Test case '" + className + ">>" + methodName + "' execution started.");
        System.out.println("Execution of '" + className + ">>" + methodName + "' test has started.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.PASS, "Test case '" + result.getMethod().getMethodName() + "' execution passed.");
        System.out.println("Test case '" + result.getMethod().getMethodName() + "' execution passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        String methodName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        Throwable throwable = result.getThrowable();
        String failingLine = null;

        // Find the exact line where the failure occurred
        for (StackTraceElement st : throwable.getStackTrace()) {
            if (st.getClassName().contains(className)) {
                failingLine = st.toString();
                break;
            }
        }

        String[] errorTraceLines = throwable.getMessage().split("\n");
        String shortErrorMessage = String.join("<br>",
                Arrays.copyOfRange(errorTraceLines, 0, Math.min(errorTraceLines.length, 5)));
        String remainingErrorMessage = "";
        if (errorTraceLines.length > 4) {
            remainingErrorMessage = String.join("<br>", Arrays.copyOfRange(errorTraceLines, 2, errorTraceLines.length));
            remainingErrorMessage = "<details><summary>Show more...</summary>" + remainingErrorMessage + "</details>";
        }

        String logMessage = String.format("Test '%s' in class '%s' failed at %s. Error: %s%s", methodName, className,
                failingLine, shortErrorMessage, remainingErrorMessage);

        logScreenshotStep(Status.FAIL, logMessage);
        System.out.println(logMessage);
        test.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.SKIP, "Test case '" + result.getMethod().getMethodName() + "' execution skipped.");
        System.out.println("Test case '" + result.getMethod().getMethodName() + "' execution skipped.");
    }

    /**
     * Starts a new test in the Extent Report.
     *
     * @param testName the name of the test to start
     */
    public static void startTest(String testName) {
        ExtentTest test = ExtentReportManager.createTest(testName);
        extentTest.set(test);
        test.log(Status.INFO, "Test case '" + testName + "' execution started.");
        System.out.println("Execution of '" + testName + "' test has started.");
    }

    /**
     * Ends the current test in the Extent Report.
     */
    public static void endTest() {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.INFO, "Test case execution ended.");
            System.out.println("Execution of test case ended.");
        }
    }

    /**
     * Logs a step in the current test with INFO level.
     *
     * @param stepDescription the description of the step to log
     */
    public static void logStep(String stepDescription) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.INFO, stepDescription);
            System.out.println(stepDescription);
        }
    }

    /**
     * Logs a step in the current test with a specified status level.
     *
     * @param status          the status level of the log entry
     * @param stepDescription the description of the step to log
     */
    public static void logStep(Status status, String stepDescription) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(status, stepDescription);
            System.out.println(stepDescription);
        }
    }

    /**
     * Logs a step in the current test with a screenshot and INFO level.
     *
     * @param driver          the WebDriver instance used to capture the screenshot
     * @param stepDescription the description of the step to log
     */
    public static void logScreenshotStep(WebDriver driver, String stepDescription) {
        driver = WebDriverMgr.getDriver();
        try {
            ExtentTest test = extentTest.get();
            if (test != null) {
                test.log(Status.INFO, stepDescription);
                System.out.println(stepDescription);

                // Capture and log the screenshot
                captureAndLogScreenshot(driver, test);
            }
        } catch (Exception e) {
            System.out.println("Screenshot not taken: " + e);
        }
    }

    /**
     * Logs a step in the current test with a screenshot and INFO level.
     *
     * @param stepDescription the description of the step to log
     */
    public static void logScreenshotStep(String stepDescription) {
        WebDriver driver = WebDriverMgr.getDriver();
        try {
            ExtentTest test = extentTest.get();
            if (test != null) {
                test.log(Status.INFO, stepDescription);
                System.out.println(stepDescription);

                // Capture and log the screenshot
                captureAndLogScreenshot(driver, test);
            }
        } catch (Exception e) {
            System.out.println("Screenshot not taken: " + e);
        }
    }

    /**
     * Logs a step in the current test with a screenshot and a specified status level.
     *
     * @param status          the status level of the log entry
     * @param stepDescription the description of the step to log
     */
    public static void logScreenshotStep(Status status, String stepDescription) {
        WebDriver driver = WebDriverMgr.getDriver();
        try {
            ExtentTest test = extentTest.get();
            if (test != null) {
                test.log(status, stepDescription);
                System.out.println(stepDescription);

                // Capture and log the screenshot
                captureAndLogScreenshot(driver, test);
            }
        } catch (Exception e) {
            System.out.println("Screenshot not taken: " + e);
            logStep("Screenshot not taken: " + e);
        }
    }

    /**
     * Captures a screenshot and logs it in the Extent Report.
     *
     * @param driver the WebDriver instance used to capture the screenshot
     * @param test   the ExtentTest instance where the screenshot will be logged
     * @throws IOException if an error occurs while saving the screenshot
     */
    private static void captureAndLogScreenshot(WebDriver driver, ExtentTest test) throws IOException {
        // Capture screenshot
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = "Screenshot_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".png";
        String destinationPath = "./Screenshots/" + fileName; // Save in "Screenshots" folder
        FileHandler.copy(source, new File(destinationPath));

        String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

        // Log screenshot in the Extent report as base64
        test.info("Screenshot: ",
                MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());

        System.out.println("Screenshot captured: " + fileName);
    }

    /**
     * Opens the generated report in the default web browser.
     *
     * @param reportFilePath the path to the report file
     */
    private void openReportInBrowser(String reportFilePath) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            File reportFile = new File(reportFilePath);
            try {
                desktop.open(reportFile);
                System.out.println("Report opened in default web browser.");
            } catch (IOException e) {
                System.out.println("Error opening report file: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop is not supported, unable to open report automatically.");
        }
    }
}