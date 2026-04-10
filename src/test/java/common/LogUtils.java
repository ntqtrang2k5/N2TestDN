package common;

import com.aventstack.extentreports.Status;

public class LogUtils {
    public static void info(String message) {
        System.out.println("INFO: " + message);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.INFO, message);
        }
    }

    public static void pass(String message) {
        System.out.println("PASS: " + message);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.PASS, message);
        }
    }

    public static void fail(String message) {
        System.out.println("FAIL: " + message);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.FAIL, message);
        }
    }

    public static void skip(String message) {
        System.out.println("SKIP: " + message);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.SKIP, message);
        }
    }
}
