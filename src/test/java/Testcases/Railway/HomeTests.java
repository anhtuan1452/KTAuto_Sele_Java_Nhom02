package Testcases.Railway;

import Railway.Common.Constant.Constant;
import Railway.pages.ChangePasswordPage;
import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import Railway.pages.MyTicketPage;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class HomeTests {
    private WebDriver driver;
    private HomePage homePage;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUpClass() {
        // Khởi tạo Extent Reports
        extent = ExtentManager.getInstance();
    }
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Test(description = "TC06 - Additional pages display once user logged in")
    public void TC06() throws NoSuchMethodException{
        test = extent.createTest("TC06", this.getClass().getDeclaredMethod("TC06").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);


            test.log(Status.INFO, "Login with valid account");
            loginPage.login(Constant.USERNAME, Constant.PASSWORD);

            test.log(Status.INFO, " Verify that \"My ticket\", \"Change password\" and \"Logout\" tabs are displayed.");
            boolean checkDisplay = homePage.isLogOutTabDisplayed()
                    && homePage.isMyTicketTabDisplayed()
                    && homePage.isChangePasswordTabDisplayed();
            if(checkDisplay){
                test.log(Status.PASS, "\"My ticket\", \"Change password\" and \"Logout\" tabs are displayed.");
            }else{
                test.fail("\"My ticket\", \"Change password\" or \"Logout\" tabs are not displayed.");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
            }

            test.log(Status.INFO, "Click 'My ticket' tab");
            homePage.clickMenuItem("My ticket");
            MyTicketPage myTicketPage = new MyTicketPage(driver);
            if (myTicketPage.isMyTicketPageDisplayed()) {
                test.log(Status.PASS, "User is directed to the My ticket page");
            } else {
                test.fail("User isn't directed to the My ticket page");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
            }

            test.log(Status.INFO, "Click 'Change password' tab");
            homePage.clickMenuItem("Change password");
            ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
            if (changePasswordPage.isChangePasswordPageDisplayed()) {
                test.log(Status.PASS, "User is directed to the Change password page");
            } else {
                test.fail("User isn't directed to the Change password page");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
                Assert.fail("User isn't directed to the Change password page");
            }

        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: Additional pages aren't displayed once user logged in. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
            throw new RuntimeException(e);
        }

    }

    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
    @AfterClass
    public void tearDownClass() {
        // Đóng Extent Reports
        if (extent != null) {
            extent.flush();
        }
    }
}