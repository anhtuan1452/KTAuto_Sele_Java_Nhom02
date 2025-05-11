package Railway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage extends GenetralPage{
    //    locators
    private final By txtEmailAddress = By.id("email");
    private final By btnSendIns =By.xpath("//input[@type='submit' and @value='Send Instructions']");
    private final By errorPage =By.xpath("//h1[contains(text(), 'Server Error')]");

    //    elements
    protected WebElement getTxtEmailAddress() {
        return getElement(txtEmailAddress);
    }
    protected WebElement getBtnSendIns() {
        sleepSafe(300);
        return getElement(btnSendIns);
    }
    protected WebElement getErrorPage() {
        return getElement(errorPage);
    }

    // Constructor
    public ForgotPasswordPage(WebDriver driver){
        super(driver);
    }

    //    methods
    public void getPassword(String email){
        getTxtEmailAddress().sendKeys(email);
        scrollElement(getBtnSendIns());
        getBtnSendIns().click();
    }

    public boolean isErrorPageDisplayed() {
        return getErrorPage().isDisplayed();
    }


}
