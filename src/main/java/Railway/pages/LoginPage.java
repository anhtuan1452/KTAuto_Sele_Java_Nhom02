package Railway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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


}