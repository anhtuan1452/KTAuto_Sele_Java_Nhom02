package Railway.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePage extends GenetralPage {
    // Locators
    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
        open();
    }
    public void open() {
        driver.get("http://railwayb1.somee.com");
    }
    // Elements
    public void checkLogOutTabDisplayed(ExtentTest test, HomePage homePage) {
        if(getTabLogout().isDisplayed()){
            test.pass( "\"Logout\" tabs are displayed.");
        }else{
            test.fail("\"Logout\" tabs are not displayed.");
            Assert.fail("\"Logout\" tabs are not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
        }
    }

    public void checkMyTicketTabDisplayed(ExtentTest test, HomePage homePage) {
        if(getElement(headerMyticket).isDisplayed()){
            test.log(Status.PASS, "\"My ticket\" tabs are displayed.");
        }else{
            test.fail("\"My ticket\" tabs are not displayed.");
            Assert.fail("\"My ticket\" tabs are not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
        }
    }

    public void checkChangePasswordTabDisplayed(ExtentTest test, HomePage homePage) {
        if(getElement(headerChangePassword).isDisplayed()){
            test.log(Status.PASS, "\"Change password\" tabs are displayed.");
        }else{
            test.fail("\"Change password\" tabs are not displayed.");
            Assert.fail("\"Change password\" tabs are not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
        }
    }

    public void checkMsgWelcome(ExtentTest test, HomePage homePage, String expectedMsg, String actualMsg) {
        if(expectedMsg.equals(actualMsg)){
            test.log(Status.PASS, " Welcome user message is displayed.");
        }else{
            test.fail(" Welcome user message is not displayed.");
            Assert.fail(" Welcome user message is not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC01"));
        }
    }


}