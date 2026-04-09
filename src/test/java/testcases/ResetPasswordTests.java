package testcases;

import common.Constant;
import common.Utilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.ForgotPasswordPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.ResetPasswordPage;

public class ResetPasswordTests {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL + "Account/PasswordReset.cshtml?token=dummytoken");
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage();

        resetPasswordPage.clearResetToken();
        String newPassword = "newpassword123";
        resetPasswordPage.resetPassword(newPassword, newPassword);

        // 7. Assert error messages
        String expectedTopError = "The password reset token is incorrect or may be expired. Visit the forgot password page to generate a new one.";
        String expectedTokenError = "The password reset token is invalid.";

        Assert.assertEquals(resetPasswordPage.getTopError(), expectedTopError, "Top error message is not as expected.");
        Assert.assertEquals(resetPasswordPage.getTokenError(), expectedTokenError, "Token field error message is not as expected.");
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");

        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        
        // 2. Click on "Forgot Password page" link
        loginPage.getForgotPasswordLink().click();
        
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();
        
        // 3. Enter the email address of the created account in Pre-condition
        // Use the constant username as it's an existing account
        String resetEmail = Constant.USERNAME; 
        
        // 4. Click on "Send Instructions" button
        forgotPasswordPage.sendResetInstructions(resetEmail);
        
        // 5. Open mailbox and click on reset password link 
        // -> "Password Change Form" page displays
        // Since we can't open an actual email directly in this test without an API like Mailosaur/Mailtrap,
        // we simulate the redirect to the reset link.
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL + "Account/PasswordReset.cshtml");
        
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage();
        
        // 6. Enter different values for password fields
        // 7. Click "Reset Password" button
        resetPasswordPage.resetPassword("NewPassword123", "DifferentPassword456");
        
        // Verify Error message "Could not reset password. Please correct the errors and try again." displays above the form.
        String expectedTopError = "Could not reset password. Please correct the errors and try again.";
        Assert.assertEquals(resetPasswordPage.getTopError(), expectedTopError, "Top error message is not displayed as expected.");
        
        // Verify Error message "The password confirmation did not match the new password." displays next to the confirm password field.
        String expectedConfirmError = "The password confirmation did not match the new password.";
        Assert.assertTrue(resetPasswordPage.getConfirmError().contains(expectedConfirmError), "Confirm password error message is not displayed as expected.");
    }
}