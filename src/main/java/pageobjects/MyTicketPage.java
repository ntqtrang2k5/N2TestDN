package pageobjects;

import common.Constant;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class MyTicketPage {

    WebDriver driver = Constant.WEBDRIVER;

    // Locator
    private By tblTickets = By.xpath("//table");
    private By btnCancelFirst = By.xpath("//table//tr[2]//input[@value='Cancel']");
    private By allRows = By.xpath("//table//tr[position()>1]");

    // =========================
    // Get number of tickets
    // =========================
    public int getTicketCount() {
        List<WebElement> rows = driver.findElements(allRows);
        return rows.size();
    }

    // =========================
    // Cancel first ticket
    // =========================
    public void cancelFirstTicket() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        int beforeCount = getTicketCount();

        // Click Cancel button
        wait.until(ExpectedConditions.elementToBeClickable(btnCancelFirst)).click();
        System.out.println("Clicked Cancel");

        // Wait alert xuất hiện
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        System.out.println("Accepted Alert");

        // Wait số lượng ticket giảm
        wait.until(driver ->
                getTicketCount() < beforeCount
        );

        System.out.println("Ticket canceled successfully");
    }
}