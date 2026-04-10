package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegisterPage extends GeneralPage {

    // Locators
    private final By _txtEmail = By.id("email");
    private final By _txtPassword = By.id("password");
    private final By _txtConfirmPassword = By.id("confirmPassword");
    private final By _txtPid = By.id("pid");
    private final By _btnRegister = By.xpath("//input[@value='Register']");
    private final By _lblSuccessMessage = By.xpath("//div[@id='content']/p");
    private final By _lblRegisterErrorMsg = By.xpath("//p[@class='message error']");
    private final By _lblPasswordErrorMsg = By.xpath("//label[@for='password' and @class='validation-error']");
    private final By _lblPidErrorMsg = By.xpath("//label[@for='pid' and @class='validation-error']");

    // Elements
    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(_txtEmail);
    }

    public WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(_txtPassword);
    }

    public WebElement getTxtConfirmPassword() {
        return Constant.WEBDRIVER.findElement(_txtConfirmPassword);
    }

    public WebElement getTxtPid() {
        return Constant.WEBDRIVER.findElement(_txtPid);
    }

    public WebElement getBtnRegister() {
        return Constant.WEBDRIVER.findElement(_btnRegister);
    }

    public WebElement getLblSuccessMessage() {
        return Constant.WEBDRIVER.findElement(_lblSuccessMessage);
    }

    public WebElement getLblRegisterErrorMsg() {
        return Constant.WEBDRIVER.findElement(_lblRegisterErrorMsg);
    }

    public WebElement getLblPasswordErrorMsg() {
        return Constant.WEBDRIVER.findElement(_lblPasswordErrorMsg);
    }

    public WebElement getLblPidErrorMsg() {
        return Constant.WEBDRIVER.findElement(_lblPidErrorMsg);
    }

    // Methods
    public void register(String email, String password, String confirmPassword, String pid) {
        getTxtEmail().sendKeys(email);
        getTxtPassword().sendKeys(password);
        getTxtConfirmPassword().sendKeys(confirmPassword);
        getTxtPid().sendKeys(pid);
        getBtnRegister().click();
    }

    public String getSuccessMessage() {
        return getLblSuccessMessage().getText();
    }

    public String getRegisterErrorMessage() {
        return getLblRegisterErrorMsg().getText();
    }

    public String getPasswordErrorMessage() {
        return getLblPasswordErrorMsg().getText();
    }

    public String getPidErrorMessage() {
        return getLblPidErrorMsg().getText();
    }
}