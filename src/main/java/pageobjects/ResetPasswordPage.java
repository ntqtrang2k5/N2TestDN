package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResetPasswordPage {

    private final By txtPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtResetToken = By.id("resetToken"); // Assuming this ID is correct
    private final By btnReset = By.xpath("//input[@value='Reset Password']");

    // TOP ERROR
    private final By lblErrorTop = By.cssSelector(".message.error");

    // FIELD-SPECIFIC ERRORS
    private final By lblErrorConfirm = By.xpath("//label[@for='confirmPassword' and @class='validation-error']");
    private final By lblErrorToken = By.xpath("//label[@for='resetToken' and @class='validation-error']");


    public void resetPassword(String password, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtConfirmPassword)).sendKeys(confirmPassword);
        
        WebElement resetBtn = wait.until(ExpectedConditions.elementToBeClickable(btnReset));
        // Scroll into view to ensure it is clickable and bypass interception
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", resetBtn);
        // Click using JavaScript executor
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", resetBtn);
    }

    public void clearResetToken() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement tokenField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtResetToken));
        tokenField.clear();
    }

    public String getTopError() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorTop)).getText();
    }

    public String getConfirmError() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorConfirm)).getText();
    }

    public String getTokenError() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorToken)).getText();
    }
}