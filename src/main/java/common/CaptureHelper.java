package common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureHelper {

    public static String captureScreenshot(String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String folderPath = System.getProperty("user.dir")
                + File.separator + "test-output"
                + File.separator + "screenshots";

        String fileName = testName + "_" + timeStamp + ".png";
        String fullPath = folderPath + File.separator + fileName;

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File srcFile = ((TakesScreenshot) Constant.WEBDRIVER)
                    .getScreenshotAs(OutputType.FILE);

            File destFile = new File(fullPath);
            FileUtils.copyFile(srcFile, destFile);

            return fullPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}