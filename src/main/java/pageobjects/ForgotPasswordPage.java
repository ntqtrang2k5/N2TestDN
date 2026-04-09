package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ForgotPasswordPage {

    private final By _txtEmail = By.id("email");
    private final By _btnSend = By.xpath("//input[@value='Send Instructions']");

    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(_txtEmail);
    }

    public WebElement getBtnSend() {
        return Constant.WEBDRIVER.findElement(_btnSend);
    }

    public void sendResetInstructions(String email) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        // Đợi ô email xuất hiện rồi điền
        WebElement emailBox = wait.until(ExpectedConditions.visibilityOfElementLocated(_txtEmail));
        emailBox.sendKeys(email);

        // Lấy ra nút bấm
        WebElement btn = Constant.WEBDRIVER.findElement(_btnSend);

        // DÙNG JAVASCRIPT ĐỂ CLICK (Xuyên qua mọi thẻ div đang che khuất)
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", btn);
    }
}