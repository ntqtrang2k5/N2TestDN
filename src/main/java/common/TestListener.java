package common;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        String screenshotPath = CaptureHelper.captureScreenshot(testName);

        if (screenshotPath != null) {
            File file = new File(screenshotPath);
            String path = file.getAbsolutePath().replace("\\", "/");

            Reporter.log("<br><b>FAILED: " + testName + "</b><br>");
            Reporter.log("<a href='file:///" + path + "' target='_blank'>");
            Reporter.log("<img src='file:///" + path + "' height='200' width='300'/></a><br>");
        }
    }
}