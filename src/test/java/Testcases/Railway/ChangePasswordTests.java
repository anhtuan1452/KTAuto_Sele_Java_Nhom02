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

public class ChangePasswordTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private ExtentReports extent;
    private ExtentTest test;
    private RegisterPage registerPage;
    String generatedEmail;
    @BeforeClass
    public void setUpClass() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);

        registerPage = new RegisterPage(driver);
        homePage.clickMenuItem("Register");
        generatedEmail= registerPage.generateGmail();
        registerPage.registerAccount(generatedEmail, Constant.PASSWORD,Constant.PASSWORD,"11111111");
        registerPage.clickMenuItem("Login");
    }

    @Test(description = "TC09 - User can change password")
    public void TC09() throws NoSuchMethodException {
        test = extent.createTest("TC09", this.getClass().getDeclaredMethod("TC09").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");

            test.log(Status.INFO, "Login with valid account and password");
            loginPage = new LoginPage(driver);
            loginPage.login(generatedEmail, Constant.PASSWORD);

            test.log(Status.INFO, "Click on \"Change Password\" tab");
            homePage.clickMenuItem("Change password");
            ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);

            test.log(Status.INFO, "Enter valid value into all fields");
            changePasswordPage.changePassword(Constant.PASSWORD, "12345678","12345678");

            test.log(Status.INFO, "Click on \"Change Password\" button");
            String expectedWarning = "Your password has been updated";
            String successMessage = changePasswordPage.getSuccessMessage();
            changePasswordPage.checkMsgCorrect(test,homePage,expectedWarning,successMessage);
            Assert.assertEquals(successMessage, expectedWarning,"Success message is not displayed as expected.");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: Welcome message is not displayed. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC09"));
        }
    }


    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}