package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class BookTicketPage extends GeneralPage {

    // Locators
    private final By _ddlDepartDate = By.name("Date");
    private final By _ddlDepartFrom = By.name("DepartStation");
    private final By _ddlArriveAt = By.name("ArriveStation");
    private final By _ddlSeatType = By.name("SeatType");
    private final By _ddlTicketAmount = By.name("TicketAmount");
    private final By _btnBookTicket = By.xpath("//input[@value='Book ticket']");
    private final By _lblSuccessMessage = By.xpath("//div[@id='content']//h1[contains(text(), 'booked successfully')]");
    private final By _tblTicketInfo = By.xpath("//table[contains(@class, 'DataGrid') or contains(@class, 'MyTable')]");

    // Elements
    public Select getDdlDepartDate() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(_ddlDepartDate));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
        return new Select(element);
    }

    public Select getDdlDepartFrom() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(_ddlDepartFrom));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
        return new Select(element);
    }

    public Select getDdlArriveAt() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(_ddlArriveAt));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
        return new Select(element);
    }

    public Select getDdlSeatType() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(_ddlSeatType));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
        return new Select(element);
    }

    public Select getDdlTicketAmount() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(_ddlTicketAmount));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
        return new Select(element);
    }

    public WebElement getBtnBookTicket() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(_btnBookTicket));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    // Methods
    public void bookTicket(String departDate, String departFrom, String arriveAt, String seatType, String ticketAmount) {
        if (departDate != null) selectOptionByText(getDdlDepartDate(), departDate);
        if (departFrom != null) selectOptionByText(getDdlDepartFrom(), departFrom);
        if (arriveAt != null) selectOptionByText(getDdlArriveAt(), arriveAt);
        if (seatType != null) selectOptionByText(getDdlSeatType(), seatType);
        if (ticketAmount != null) selectOptionByText(getDdlTicketAmount(), ticketAmount);
        getBtnBookTicket().click();
    }

    private void selectOptionByText(Select select, String text) {
        try {
            select.selectByVisibleText(text);
        } catch (Exception e) {
            // Fallback if exact text not found (common in dynamic dropdowns)
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().contains(text)) {
                    select.selectByVisibleText(option.getText());
                    return;
                }
            }
            // If still not found, select first valid option
            if (options.size() > 1) select.selectByIndex(1);
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblSuccessMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblSuccessMessage)).getText();
    }

    public String getTicketInfoText() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_tblTicketInfo)).getText();
    }

    public String getFutureDate(int daysToAdd) {
        LocalDate futureDate = LocalDate.now().plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        // Safe Railway often uses "4/10/2026" or "4/10/2026" (single digit M/d)
        return futureDate.format(formatter);
    }

    // Simplified for TC14
    public void selectDepartDate(String date) { selectOptionByText(getDdlDepartDate(), date); }
    public void selectDepartStation(String station) { selectOptionByText(getDdlDepartFrom(), station); }
    public void selectArriveStation(String station) { selectOptionByText(getDdlArriveAt(), station); }
    public void selectSeatType(String type) { selectOptionByText(getDdlSeatType(), type); }
    public void enterTicketAmount(String amount) { selectOptionByText(getDdlTicketAmount(), amount); }
    public void clickBookTicketButton() { getBtnBookTicket().click(); }

    public BookInfo bookRandomTicket() {
        String date = selectRandomOption(getDdlDepartDate());
        String from = selectRandomOption(getDdlDepartFrom());
        String to = selectRandomOption(getDdlArriveAt());
        String seat = selectRandomOption(getDdlSeatType());
        String amount = selectRandomOption(getDdlTicketAmount());
        getBtnBookTicket().click();
        return new BookInfo(date, from, to, seat, amount);
    }

    private String selectRandomOption(Select select) {
        List<WebElement> options = select.getOptions();
        Random rand = new Random();
        int index = options.size() > 1 ? rand.nextInt(options.size() - 1) + 1 : 0;
        select.selectByIndex(index);
        return select.getFirstSelectedOption().getText();
    }

    public static class BookInfo {
        private String date, from, to, seat, amount;
        public BookInfo(String date, String from, String to, String seat, String amount) {
            this.date = date; this.from = from; this.to = to; this.seat = seat; this.amount = amount;
        }
        public String getDate() { return date; }
        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getSeat() { return seat; }
        public String getAmount() { return amount; }
    }
}
