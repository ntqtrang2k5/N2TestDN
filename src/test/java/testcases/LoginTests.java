package testcases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import common.Constant;
import common.LogUtils;
import pageobjects.HomePage;
import pageobjects.LoginPage;

import java.time.Duration;

public class LoginTests {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }

    @Test
    public void TC01() {
        LogUtils.info("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        LogUtils.info("3. Enter valid Email and Password");
        LogUtils.info("4. Click on 'Login' button");
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        String expectedWelcomeMessage = "Welcome " + Constant.USERNAME;
        String actualWelcomeMessage = homePage.getWelcomeMessage();

        LogUtils.info("Verify welcome message is displayed");
        Assert.assertEquals(actualWelcomeMessage, expectedWelcomeMessage, "Welcome message is not displayed as expected");
        
        LogUtils.info("Verify Book Ticket, My Ticket, and Change Password tabs are displayed");
        Assert.assertTrue(homePage.isBookTicketTabDisplayed(), "Book Ticket tab is not displayed");
        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My Ticket tab is not displayed");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change Password tab is not displayed");
    }

    @Test
    public void TC02() {
        LogUtils.info("TC02 - User can't login with blank Username textbox");
        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        LogUtils.info("3. Leave Username blank and enter valid Password");
        loginPage.loginExpectingError("", Constant.PASSWORD);
        
        LogUtils.info("Verify error message for blank username");
        String actualErrorMsg = loginPage.getErrorMessage();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form. ";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }


    @Test
    public void TC03() {
        LogUtils.info("TC03 - User cannot log into Railway with invalid password");

        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();

        LogUtils.info("3. Enter valid username and invalid password");
        loginPage.loginExpectingError(Constant.USERNAME, "invalidpassword");

        LogUtils.info("Verify error message for invalid password");
        String actualMsg = loginPage.getErrorMessage();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form. ";

        Assert.assertEquals(actualMsg, expectedMsg,
                "Error message is not displayed as expected");
    }

    @Test
    public void TC05() {
        LogUtils.info("TC05 - System shows message when user enters wrong password several times");

        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();

        LogUtils.info("3. Repeat login with wrong password 4 times");
        for (int i = 0; i < 4; i++) {
            LogUtils.info("Attempt " + (i + 1) + ": Logging in with wrong password");
            loginPage.loginExpectingError(Constant.USERNAME, "wrongpassword");
            
            // Re-instantiate loginPage to refresh element locators if needed
            loginPage = new LoginPage();
            
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        LogUtils.info("Verify error message for multiple failed login attempts");
        String actualMsg = loginPage.getErrorMessage();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        Assert.assertEquals(actualMsg, expectedMsg, "The warning message for multiple failed login attempts is not as expected.");
    }

    @Test
    public void TC06() {
        LogUtils.info("TC06 - Additional pages display once user logged in");

        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        LogUtils.info("3. Login with valid credentials");
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        LogUtils.info("Verify login is successful (Logout tab displayed)");
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Login was not successful, Logout tab is not displayed.");

        LogUtils.info("Verify My Ticket and Change Password tabs are displayed");
        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My Ticket tab should be displayed.");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change Password tab should be displayed.");

        LogUtils.info("4. Click on 'My Ticket' tab");
        homePage.gotoMyTicket();

        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("ManageTicket.cshtml"));

        LogUtils.info("Verify navigation to My Ticket page");
        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("ManageTicket.cshtml"), "Did not navigate to My Ticket page correctly.");

        Constant.WEBDRIVER.navigate().back();

        LogUtils.info("5. Click on 'Change Password' tab");
        homePage.gotoChangePassword();

        wait.until(ExpectedConditions.urlContains("ChangePassword.cshtml"));

        LogUtils.info("Verify navigation to Change Password page");
        currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("ChangePassword.cshtml"), "Did not navigate to Change Password page correctly.");
    }

    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account hasn't been activated");

        // Khởi tạo data (Tài khoản chưa kích hoạt)
        String email = "your_unactivated_email@example.com";
        String password = "your_password";

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        // Thực hiện login (Sử dụng loginExpectingError để tránh việc đợi Logout tab xuất hiện)
        loginPage.loginExpectingError(email, password);

        // Lấy thông báo lỗi thực tế và so sánh
        String actualMsg = loginPage.getErrorMessage();
        String expectedMsg = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Thông báo lỗi hiển thị không đúng.");
    }
}
