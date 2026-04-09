package common;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        LogUtils.info("Test Suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LogUtils.info("Test Suite finished: " + context.getName());
        ExtentReportManager.getExtentReports().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Starting test: " + result.getMethod().getMethodName());
        ExtentTestManager.startTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.pass("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.fail("Test failed: " + result.getMethod().getMethodName());
        LogUtils.fail(result.getThrowable().toString());

        if (Constant.WEBDRIVER != null) {
            try {
                String base64Screenshot = ((TakesScreenshot) Constant.WEBDRIVER).getScreenshotAs(OutputType.BASE64);
                ExtentTestManager.getTest().fail("Screenshot on failure", 
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } catch (Exception e) {
                LogUtils.info("Could not capture screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.skip("Test skipped: " + result.getMethod().getMethodName());
    }
}
