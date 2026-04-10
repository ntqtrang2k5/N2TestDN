package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Constant;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends GeneralPage {
    // Locators
    private final By _txtUsername = By.xpath("//input[@id='username']");
    private final By _txtPassword = By.id("password");
    // Trang web safe railway thường dùng button submit hoặc title 'Login', hoặc chữ l thường trong value='login'
    private final By _btnLogin = By.xpath("//input[@title='Login' or @value='login' or @value='Login' or @type='submit']");
//    private final By _lblLoginErrorMsg = By.xpath("//p[@class='message error loginForm']");
    private final By _lblLoginErrorMsg = By.cssSelector(".message.error"); // tc02
    private final By _lnkForgotPassword = By.xpath("//a[contains(text(),'Forgot Password')]");

    // Elements
    public WebElement getTxtUsername() {
        return Constant.WEBDRIVER.findElement(_txtUsername);
    }

    public WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(_txtPassword);
    }

    public WebElement getBtnLogin() {
        return Constant.WEBDRIVER.findElement(_btnLogin);
    }

    public WebElement getLblLoginErrorMsg() {
        return Constant.WEBDRIVER.findElement(_lblLoginErrorMsg);
    }

    public WebElement getForgotPasswordLink() {
        return Constant.WEBDRIVER.findElement(_lnkForgotPassword);
    }

    // Methods
    private void fillLoginForm(String username, String password) {
        this.getTxtUsername().sendKeys(username);
        this.getTxtPassword().sendKeys(password);
        WebElement btnLogin = this.getBtnLogin();
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", btnLogin);
        btnLogin.click();
    }

    public HomePage login(String username, String password) {
        fillLoginForm(username, password);
        // After clicking login, wait for the Logout tab to be visible on the HomePage
        // This indicates successful login and redirection to HomePage
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='menu']//a[@href='/Account/Logout']")));
        return new HomePage();
    }

    public LoginPage loginExpectingError(String username, String password) {
        fillLoginForm(username, password);
<<<<<<< HEAD
<<<<<<< HEAD
        // Return the current page object because we expect to stay on the login page
=======
        // Wait for error message to be visible to ensure page is stable for subsequent actions
        getErrorMessage();
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
=======
        // Wait for error message to be visible to ensure page is stable for subsequent actions
        getErrorMessage();
>>>>>>> dbd0c7e23b178ffc512c4d702894414ef4ec7c20
        return this;
    }

    public String getErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblLoginErrorMsg)).getText();
        } catch (Exception e) {
            return ""; // Return empty string if error message is not found
        }
    }
}