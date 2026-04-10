package testcases;

import common.Constant;
import common.Utilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.RegisterPage;

public class RegisterTests {

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

    @Test
    public void TC07() {
        System.out.println("TC07 - User can create new account");

        // 1. Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // 2. Click on "Register" tab
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // 3. Enter valid information into all fields
        String email = Utilities.generateRandomEmail();
        String password = "password123";
        String pid = "123456789";

        // 4. Click on "Register" button
        registerPage.register(email, password, password, pid);

        // Verify new account is created and message "Thank you for registering your account" appears.
        String actualMsg = registerPage.getSuccessMessage();
        String expectedMsg = "Thank you for registering your account";

        Assert.assertEquals(actualMsg, expectedMsg, "Success message is not as expected.");
    }

    @Test
    public void TC10() {
        System.out.println("TC10 - User can't create account with 'Confirm password' is not the same with 'Password'");

        // 1. Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // 2. Click on "Register" tab
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // 3. Enter valid information into all fields except "Confirm password" is not the same with "Password"
        String email = Utilities.generateRandomEmail();
        String password = "password123";
        String confirmPassword = "differentpassword";
        String pid = "123456789";

        // 4. Click on "Register" button
        registerPage.register(email, password, confirmPassword, pid);

        // Verify message "There're errors in the form. Please correct the errors and try again." appears.
        String actualMsg = registerPage.getRegisterErrorMessage();
        String expectedMsg = "There're errors in the form. Please correct the errors and try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message for password mismatch is not as expected.");
    }

    @Test
    public void TC11() {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();
        String email = Utilities.generateRandomEmail();
        registerPage.register(email, "", "", "");

        // Verify error messages
        String expectedGeneralError = "There're errors in the form. Please correct the errors and try again.";
        String expectedPasswordError = "Invalid password length.";
        String expectedPidError = "Invalid ID length.";

        Assert.assertEquals(registerPage.getRegisterErrorMessage(), expectedGeneralError, "General error message is not as expected.");
        Assert.assertEquals(registerPage.getPasswordErrorMessage(), expectedPasswordError, "Password error message is not as expected.");
        Assert.assertEquals(registerPage.getPidErrorMessage(), expectedPidError, "PID error message is not as expected.");
    }
}