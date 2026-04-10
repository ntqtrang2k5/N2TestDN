package testcases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import common.Constant;
<<<<<<< HEAD
=======
import common.LogUtils;
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
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
<<<<<<< HEAD
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
=======
        LogUtils.info("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        LogUtils.info("3. Enter valid Email and Password");
        LogUtils.info("4. Click on 'Login' button");
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        String expectedWelcomeMessage = "Welcome " + Constant.USERNAME;
        String actualWelcomeMessage = homePage.getWelcomeMessage();

<<<<<<< HEAD
        Assert.assertEquals(actualWelcomeMessage, expectedWelcomeMessage, "Welcome message is not displayed as expected");
=======
        LogUtils.info("Verify welcome message is displayed");
        Assert.assertEquals(actualWelcomeMessage, expectedWelcomeMessage, "Welcome message is not displayed as expected");
        
        LogUtils.info("Verify Book Ticket, My Ticket, and Change Password tabs are displayed");
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        Assert.assertTrue(homePage.isBookTicketTabDisplayed(), "Book Ticket tab is not displayed");
        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My Ticket tab is not displayed");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change Password tab is not displayed");
    }

    @Test
    public void TC02() {
<<<<<<< HEAD
        System.out.println("TC02 - User can't login with blank Username textbox");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.loginExpectingError("", Constant.PASSWORD);
=======
        LogUtils.info("TC02 - User can't login with blank Username textbox");
        HomePage homePage = new HomePage();
        LogUtils.info("1. Navigate to Railway website");
        homePage.open();

        LogUtils.info("2. Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        LogUtils.info("3. Leave Username blank and enter valid Password");
        loginPage.loginExpectingError("", Constant.PASSWORD);
        
        LogUtils.info("Verify error message for blank username");
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        String actualErrorMsg = loginPage.getErrorMessage();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form. ";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }


    @Test
    public void TC03() {
<<<<<<< HEAD
        System.out.println("TC03 - User cannot log into Railway with invalid password");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        // Nhập username đúng, password sai
        loginPage.loginExpectingError(Constant.USERNAME, "invalidpassword");

        String actualMsg = loginPage.getErrorMessage();

=======
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
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        String expectedMsg = "There was a problem with your login and/or errors exist in your form. ";

        Assert.assertEquals(actualMsg, expectedMsg,
                "Error message is not displayed as expected");
    }

    @Test
    public void TC05() {
<<<<<<< HEAD
        System.out.println("TC05 - System shows message when user enters wrong password several times");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        // Repeat login with wrong password 4 times
        for (int i = 0; i < 4; i++) {
            loginPage.loginExpectingError(Constant.USERNAME, "wrongpassword");
        }

=======
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
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        String actualMsg = loginPage.getErrorMessage();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        Assert.assertEquals(actualMsg, expectedMsg, "The warning message for multiple failed login attempts is not as expected.");
    }

    @Test
    public void TC06() {
<<<<<<< HEAD
        System.out.println("TC06 - Additional pages display once user logged in");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Verify login is successful before proceeding
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Login was not successful, Logout tab is not displayed.");

        // Verify tabs are displayed
        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My Ticket tab should be displayed.");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change Password tab should be displayed.");

        // Verify navigation to My Ticket page
        homePage.gotoMyTicket();

        // Wait for URL to change before asserting
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("ManageTicket.cshtml"));

=======
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
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("ManageTicket.cshtml"), "Did not navigate to My Ticket page correctly.");

<<<<<<< HEAD
        // Go back to home page to click the next tab
        Constant.WEBDRIVER.navigate().back();

        // Verify navigation to Change Password page
        homePage.gotoChangePassword();

        // Wait for URL to change before asserting
        wait.until(ExpectedConditions.urlContains("ChangePassword.cshtml"));

=======
        Constant.WEBDRIVER.navigate().back();

        LogUtils.info("5. Click on 'Change Password' tab");
        homePage.gotoChangePassword();

        wait.until(ExpectedConditions.urlContains("ChangePassword.cshtml"));

        LogUtils.info("Verify navigation to Change Password page");
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
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
