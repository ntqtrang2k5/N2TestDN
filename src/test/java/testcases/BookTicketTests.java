package testcases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import common.Constant;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.BookTicketPage;

public class BookTicketTests {

    @BeforeMethod
    public void setUp() {
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }

    @Test
    public void TC04() {

        System.out.println("TC04 - Login page displays when un-logged User clicks on Book ticket tab");

        HomePage homePage = new HomePage();
        homePage.open();

        homePage.gotoBookTicketPageWithoutLogin();

        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("Account/Login.cshtml") || currentUrl.contains("Login"), "Expected to be redirected to the Login page.");

        // Optionally, check if the login form elements (like the login button) are displayed
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.getBtnLogin().isDisplayed(), "Login button should be displayed on the redirected page.");
    }


        @Test(priority = 2)
        public void TC14_UserCanBookOneTicket() {
            System.out.println("=== Starting TC14 - User can book 1 ticket ===");

            HomePage homePage = new HomePage();
            homePage.open();
            System.out.println("Current PASSWORD: " + Constant.NEW_PASSWORD);

            LoginPage loginPage = homePage.gotoLoginPage();

            // 2. Login
            homePage = loginPage.login(Constant.USERNAME, Constant.NEW_PASSWORD);

            // ✅ Check login kỹ hơn (tránh fail mù)
            Assert.assertTrue(homePage.isLogoutTabDisplayed(),
                    "Login failed - Logout tab not displayed");

            // 3. Go to Book Ticket
            BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

            // 4. Book ticket random
            BookTicketPage.BookInfo info = bookTicketPage.bookRandomTicket();

            // 5. Verify success message
            String successMessage = bookTicketPage.getSuccessMessage();
            Assert.assertEquals(successMessage.trim(),
                    "Ticket booked successfully!",
                    "Success message mismatch");

            // 6. Verify ticket info
            String ticketInfo = bookTicketPage.getTicketInfoText();

            Assert.assertTrue(ticketInfo.contains(info.getFrom()),
                    "Depart station mismatch");

            Assert.assertTrue(ticketInfo.contains(info.getTo()),
                    "Arrive station mismatch");

            Assert.assertTrue(ticketInfo.contains(info.getSeat()),
                    "Seat type mismatch");

            Assert.assertTrue(ticketInfo.contains(String.valueOf(info.getAmount())),
                    "Ticket amount mismatch");

            System.out.println("=== TC14 PASSED ===");
        }

}
