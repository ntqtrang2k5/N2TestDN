package testcases;

import common.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.ChangePasswordPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;

public class ChangePasswordTests {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test  //sai dấu !
    public void TC09() {
        System.out.println("TC09 - User can change password");

        // 1. Navigate to QA Railway Website & 2. Login
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // 3. Click on "Change Password" tab
        homePage.gotoChangePassword();

        // 4. Enter valid value into all fields & 5. Click on "Change Password" button
        ChangePasswordPage changePasswordPage = new ChangePasswordPage();
        String oldPassword = Constant.PASSWORD;
        String newPassword = Constant.NEW_PASSWORD;
        changePasswordPage.changePassword(oldPassword, newPassword, newPassword);

        // Assert: Message "Your password has been updated" appears.
        String actualMsg = changePasswordPage.getSuccessMessage();
        String expectedMsg = "Your password has been updated";
        Assert.assertEquals(actualMsg, expectedMsg, "Success message is not as expected.");

        // Update the global password so subsequent tests use the new one
        Constant.PASSWORD = newPassword;
    }
}