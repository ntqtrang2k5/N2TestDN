package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Constant;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GeneralPage {
    // Locators
    private final By tabLogin = By.xpath("//div[@id='menu']//a[contains(@href, 'Login.cshtml')]");
    private final By tabLogout = By.xpath("//div[@id='menu']//a[contains(@href, 'Logout')]");
    private final By tabRegister = By.xpath("//div[@id='menu']//a[contains(@href, 'Register.cshtml')]");
    private final By tabTimetable = By.xpath("//div[@id='menu']//a[contains(@href, 'TrainTimeListPage.cshtml')]");
    private final By lblWelcomeMessage = By.xpath("//h1[@align='center']");
    private final By tabBookTicket = By.xpath("//div[@id='menu']//a[contains(@href, 'BookTicketPage.cshtml')]");
    private final By tabMyTicket = By.xpath("//div[@id='menu']//a[contains(@href, 'ManageTicket.cshtml')]");
    private final By tabChangePassword = By.xpath("//div[@id='menu']//a[contains(@href, 'ChangePassword.cshtml')]");


    // Elements
    protected WebElement getTabLogin() {
        return Constant.WEBDRIVER.findElement(tabLogin);
    }

    protected WebElement getTabLogout() {
        return Constant.WEBDRIVER.findElement(tabLogout);
    }
    
    protected WebElement getTabRegister() {
        return Constant.WEBDRIVER.findElement(tabRegister);
    }

    protected WebElement getTabTimetable() {
        return Constant.WEBDRIVER.findElement(tabTimetable);
    }

    protected WebElement getLblWelcomeMessage() {
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }

    protected WebElement getTabBookTicket() {
        return Constant.WEBDRIVER.findElement(tabBookTicket);
    }

    protected WebElement getTabMyTicket() {
        return Constant.WEBDRIVER.findElement(tabMyTicket);
    }

    protected WebElement getTabChangePassword() {
        return Constant.WEBDRIVER.findElement(tabChangePassword);
    }

    // Methods
    public String getWelcomeMessage() {
        return this.getLblWelcomeMessage().getText();
    }

    public LoginPage gotoLoginPage() {
        this.getTabLogin().click();
        return new LoginPage();
    }

    public RegisterPage gotoRegisterPage() {
        this.getTabRegister().click();
        return new RegisterPage();
    }

    public TimetablePage gotoTimetablePage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement timetableTab = wait.until(ExpectedConditions.presenceOfElementLocated(tabTimetable));
        
        // Scroll into view to ensure it's clickable
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", timetableTab);
        
        try {
            wait.until(ExpectedConditions.elementToBeClickable(timetableTab)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", timetableTab);
        }
        return new TimetablePage();
    }

    public BookTicketPage gotoBookTicketPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement bookTicketTab = wait.until(ExpectedConditions.elementToBeClickable(tabBookTicket));
        
        try {
            bookTicketTab.click();
        } catch (Exception e) {
            // Fallback to JS click if element is intercepted
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", bookTicketTab);
        }
        
        // Wait for the URL to be exactly the Book Ticket page URL
        wait.until(ExpectedConditions.urlContains("Page/BookTicketPage.cshtml"));
        return new BookTicketPage();
    }

    public MyTicketPage gotoMyTicketPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement myTicketTab = wait.until(ExpectedConditions.elementToBeClickable(tabMyTicket));
        
        try {
            myTicketTab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", myTicketTab);
        }
        
        wait.until(ExpectedConditions.urlContains("Page/ManageTicket.cshtml"));
        return new MyTicketPage();
    }

    public LoginPage gotoBookTicketPageWithoutLogin() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement bookTicketTab = wait.until(ExpectedConditions.elementToBeClickable(tabBookTicket));
        
        try {
            bookTicketTab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", bookTicketTab);
        }
        // After clicking Book Ticket without login, it should redirect to Login page
        wait.until(ExpectedConditions.urlContains("Account/Login.cshtml"));
        return new LoginPage();
    }

    //Check log in
    public boolean isLogoutTabDisplayed() {
        try {
            return getTabLogout().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMyTicketTabDisplayed() {
        try {
            // Wait for a longer duration to see if it becomes visible
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tabMyTicket));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isChangePasswordTabDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tabChangePassword));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBookTicketTabDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tabBookTicket));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void gotoMyTicket() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement myTicketTab = wait.until(ExpectedConditions.elementToBeClickable(tabMyTicket));
        myTicketTab.click();
    }

    public void gotoChangePassword() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement changePwdTab = wait.until(ExpectedConditions.elementToBeClickable(tabChangePassword));
        changePwdTab.click();
    }
}
