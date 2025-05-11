package Railway.pages;

import Railway.Common.Constant.Constant;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChangePasswordPage extends GenetralPage {
    // Locators
    private final By currentPasswordField = By.id("currentPassword");
    private final By newPasswordField = By.id("newPassword");
    private final By confirmPasswordField = By.id("confirmPassword");
    private final By changePasswordButton = By.xpath("//*[@id='ChangePW']/fieldset/p/input");
    private final By sMessage = By.xpath("//*[@id=\"ChangePW\"]/fieldset/p[1]");
    private final By errorMessage = By.xpath("//div[@id='ChangePW']/fieldset/p[@class='message error']");
    private final By pageTitle = By.xpath("//h1[contains(text(), 'Change password')]");
    // Constructor
    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    // Elements
    protected WebElement CurrentPasswordField() {
        return getElement(currentPasswordField,true);
    }

    protected WebElement NewPasswordField() {
        return getElement(newPasswordField,true);
    }

    protected WebElement ConfirmPasswordField() {
        return getElement(confirmPasswordField,true);
    }

    protected WebElement ChangePasswordButton() {
        return getElement(changePasswordButton,true);
    }
    protected WebElement SMsg(){
        return getElement(sMessage);
    }
    protected WebElement ErrorMessage() {
        return getElement(errorMessage);
    }

    // Methods
    public void checkChangePasswordPageDisplayed(ExtentTest test, HomePage homePage) {
        if(getElement(pageTitle).isDisplayed()) {
            test.log(Status.PASS, "Change Password page is displayed");
        } else {
            test.fail("Change Password page is not displayed");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "ChangePasswordPage"));
        }
    }
    public void checkMsgCorrect(ExtentTest test, HomePage homePage, String expectedMsg, String actualMsg) {
        if(expectedMsg.equals(actualMsg)) {
            test.log(Status.PASS, "Message \"Your password has been updated\" appears.");
        } else {
            test.fail("Message \"Your password has been updated\" not appears.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "ChangePasswordPage"));
        }
    }
    public void changePassword(String currentPw, String newPw, String confirmPw) {
        CurrentPasswordField().sendKeys(currentPw);
        NewPasswordField().sendKeys(newPw);
        ConfirmPasswordField().sendKeys(confirmPw);
        sleepSafe(300);
        scrollElement(ChangePasswordButton());
        ChangePasswordButton().click();
    }


    public String getSuccessMessage() {
        sleepSafe(500);
        return SMsg().getText();
    }
}
