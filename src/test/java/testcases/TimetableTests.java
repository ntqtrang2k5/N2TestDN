package testcases;

import common.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.BookTicketPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.TimetablePage;

public class TimetableTests {

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
    public void TC15() {
        System.out.println("TC15 - User can open 'Book ticket' page by clicking on 'Book ticket' link in 'Train timetable' page");

        // 1. Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // 2. Login with a valid account
        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.NEW_PASSWORD);
        
        // Verify login is successful
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Login failed");

        // 3. Click on "Timetable" tab
        TimetablePage timetablePage = homePage.gotoTimetablePage();

        // 4. HYBRID LOGIC: Find specific route or fallback to random
        TimetablePage.RouteInfo route = timetablePage.getRouteOrRandom("Huế", "Sài Gòn");

        // 5. Click on "book ticket" link
        BookTicketPage bookTicketPage = timetablePage.clickBookTicket(route);

        // Assert: "Book ticket" page is loaded with correct "Depart from" and "Arrive at" values.
        String actualDepartFrom = bookTicketPage.getDdlDepartFrom().getFirstSelectedOption().getText();
        String actualArriveAt = bookTicketPage.getDdlArriveAt().getFirstSelectedOption().getText();

        Assert.assertEquals(actualDepartFrom, route.getDepart(), "Depart From station is not pre-filled correctly.");
        Assert.assertEquals(actualArriveAt, route.getArrive(), "Arrive At station is not pre-filled correctly.");
    }
}
