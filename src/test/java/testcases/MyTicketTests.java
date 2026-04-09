package testcases;

import common.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.BookTicketPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyTicketPage;

public class MyTicketTests {

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
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");

        // 1. Open website
        HomePage homePage = new HomePage();
        homePage.open();

        // 2. Login
        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.NEW_PASSWORD);

        // 3. Book ticket
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookRandomTicket();

        // 4. Go to My Ticket
        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();

        int initialTicketCount = myTicketPage.getTicketCount();
        System.out.println("Before cancel: " + initialTicketCount);

        Assert.assertTrue(initialTicketCount ==1 , "No ticket to cancel");

        // 5. Cancel ticket
        myTicketPage.cancelFirstTicket();

        // 6. Verify
        int currentTicketCount = myTicketPage.getTicketCount();
        System.out.println("After cancel: " + currentTicketCount);

        Assert.assertEquals(
                currentTicketCount,
                initialTicketCount - 1,
                "Ticket was not canceled successfully"
        );
    }
}