package Testcases.Railway;

import Railway.Common.Constant.Constant;
import Railway.pages.*;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class FogetPassWordTest {
    private WebDriver driver;
    private HomePage homePage;
    private String email_created;
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
        homePage = new HomePage(driver);
        homePage.open();
        homePage.clickMenuItem("Register");
        RegisterPage registerPage = new RegisterPage(driver);
        String email = homePage.generateGmail();
        email_created = email;
        registerPage.registerAccount(email_created,homePage.pw,homePage.pw,"11111111");
    }

//    @Test
//    public void TC12(){
//        Assert.assertEquals(true,true);
//    }

    @Test(description = "TC013 - Errors display if password and confirm password don't match when resetting password")
    public void TC13() throws NoSuchMethodException{
        test = extent.createTest("TC13", this.getClass().getDeclaredMethod("TC13").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Login page");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);

            test.log(Status.INFO, "Click on \"Forgot Password page\" link");
            loginPage.gotoForgotPasswordPage();
            ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

            test.log(Status.INFO, "Enter the email address of the created account in Pre-condition");
            forgotPasswordPage.getPassword(email_created);

            test.info("Click on \"Send Instructions\" button");

            test.log(Status.INFO, "Verifying the outcome of the action.");
            boolean isErrorDisplayed = forgotPasswordPage.isErrorPageDisplayed();
            Assert.assertFalse(isErrorDisplayed, "The error page was displayed unexpectedly.");
            test.pass("Test completed successfully.");
        }
        catch (Throwable e) {
            test.fail("Test failed: " + e.getMessage());
            try {
                String screenshotName = "TC13" + System.currentTimeMillis();
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, screenshotName));
            } catch (Exception screenshotEx) {
                test.log(Status.WARNING, "Failed to capture screenshot: " + screenshotEx.getMessage());
            }
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
