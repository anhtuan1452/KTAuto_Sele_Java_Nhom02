package Railway.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends GenetralPage {
    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//input[@type='submit' and @value='login']");
    private final By forgotPasswordLink = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");
    private final By pageTitle = By.xpath("//h1[contains(text(), 'Login page')]");
    private final By errorMessage = By.xpath("//*[@id=\"content\"]/p");

    //Element
    protected WebElement UsernameField(){
        sleepSafe(300);
        return getElement(usernameField);
    }
    protected WebElement PasswordField(){
        return getElement(passwordField);
    }
    protected WebElement LoginButton(){
        sleepSafe(300);
        return getElement(loginButton);
    }
    protected WebElement ForgotPasswordLink(){
        return getElement(forgotPasswordLink);
    }
    protected WebElement ErrorMessage(){
        return getElement(errorMessage);
    }
    // Constructor
    public LoginPage(WebDriver driver){
        super(driver);
    }

    //Method
    public boolean isLoginPageDisplayed() {
        return getElement(pageTitle).isDisplayed();
    }

    public void checkLoginPageDisplayed(ExtentTest test, HomePage homePage) {
        if (getElement(pageTitle).isDisplayed()) {
            test.log(Status.PASS, "Login page displays instead of Book ticket page");
        } else {
            test.fail("Login page not displays instead of Book ticket page");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "MyTicketPage"));
        }
    }

    public void checkMsgLoginFailed(ExtentTest test, HomePage homePage, String expectedMsg, String actualMsg) {
        if (expectedMsg.equals(actualMsg)) {
            test.log(Status.PASS, "Error message \"There was a problem with your login and/or errors exist in your form.\" is displayed");
        } else {
            test.fail("Error message \"There was a problem with your login and/or errors not exist in your form.\" is displayed");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "LoginPage"));
        }
    }

    public void checkMsgLoginFailed2(ExtentTest test, HomePage homePage, String expectedMsg, String actualMsg) {
        if (expectedMsg.equals(actualMsg)) {
            test.log(Status.PASS, "Error message \"Invalid username or password. Please try again\" is displayed");
        } else {
            test.fail("Error message \"Invalid username or password. Please try again\" is not displayed");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "LoginPage"));
        }
    }

    public void checkMsgWrongPass(ExtentTest test, HomePage homePage, String expectedMsg, String actualMsg) {
        if (expectedMsg.equals(actualMsg)) {
            test.log(Status.PASS, "Message \"You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.\" appears.");
        } else {
            test.fail("Message \"You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.\" not appears.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "LoginPage"));
        }
    }

    public void login(String username, String password) {
        UsernameField().sendKeys(username);
        PasswordField().sendKeys(password);
        scrollElement(LoginButton());
        LoginButton().click();
    }

    public String getErrorMessage() {
        if(ErrorMessage() == null || !ErrorMessage().isDisplayed()) {
            throw new NoSuchElementException("Welcome message element not found or not displayed");
        }
        return ErrorMessage().getText();
    }
    public void gotoForgotPasswordPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(ForgotPasswordLink()));
        scrollElement(forgotPasswordLink);
        forgotPasswordLink.click();
    }

}