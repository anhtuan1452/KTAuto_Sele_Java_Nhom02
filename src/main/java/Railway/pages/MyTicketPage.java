package Railway.pages;

import Railway.Common.Constant.Constant;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MyTicketPage extends GenetralPage {
    // Locators
    private final By ticketTableRows = By.xpath("//table[@class='MyTable']/tbody/tr[contains(@class,'Row')]");
    private final By pageTitle = By.xpath("//h1[contains(text(), 'Manage ticket')]");
    // Constructor
    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    // Methods
    public void checkMyTicketPageDisplayed(ExtentTest test,HomePage homePage) {
        if (getElement(pageTitle).isDisplayed()) {
            test.log(Status.PASS, "My Ticket page is displayed");
        } else {
            test.fail("My Ticket page is not displayed");
            Assert.fail("My Ticket page is not displayed");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "MyTicketPage"));
        }
    }
    public boolean cancelTicket(String ticketId, ExtentTest test, HomePage homePage) {
            String cancelButtonXPath = String.format(".//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(%s)')]", ticketId);
            WebElement cancelButton = getElement(By.xpath(cancelButtonXPath));
            scrollElement(cancelButton);
            cancelButton.click();
            if (cancelButton != null) {
                test.log(Status.INFO, "Cancel button is displayed");
                return true;
            } else {
                test.fail("Cancel button is not displayed");
                Assert.fail("Cancel button is not displayed");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "MyTicketPage"));
                return false;
            }

    }

    public boolean confirmCancel(String ticketId, ExtentTest test, HomePage homePage) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        String cancelButtonXPath = String.format(".//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(%s)')]", ticketId);
        WebElement cancelButton = getNotElement(By.xpath(cancelButtonXPath));
        if (cancelButton == null) {
            test.log(Status.PASS, "The canceled ticket is disappeared.");
            return true;
        } else {
            test.fail("The canceled ticket is not disappeared.");
            Assert.fail("The canceled ticket is not disappeared.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "MyTicketPage"));
            return false;
        }
    }



}