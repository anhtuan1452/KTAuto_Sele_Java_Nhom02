package Testcases.Railway;

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

    @BeforeClass
    public void setUpClass() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
    }

    @Test(description = "TC09 - User can change password")
    public void TC09() throws NoSuchMethodException {

        test = extent.createTest("TC09", this.getClass().getDeclaredMethod("TC09").getAnnotation(Test.class).description());
        try {
            registerPage = new RegisterPage(driver);
            String a= registerPage.generateGmail();
            registerPage.registerAccount(a, registerPage.pw,registerPage.pw,"11111111");
            registerPage.clickMenuItem("Login");
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage.open();
            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            test.log(Status.INFO, "Login with valid account and password");
            loginPage = new LoginPage(driver);
            loginPage.login(a, homePage.pw);
            test.log(Status.INFO, "Click on \"Change Password\" tab");
            homePage.clickMenuItem("Change password");
            ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
            test.log(Status.INFO, "Enter valid value into all fields");
            changePasswordPage.changePassword(homePage.pw, "12345678","12345678");
            test.log(Status.INFO, "Click on \"Change Password\" button");
            String expectedWarning = "Your password has been updated!";
            String successMessage = changePasswordPage.getSuccessMessage();
            boolean check = successMessage.equals(expectedWarning);
            if (check) {
                test.log(Status.PASS, "User can change password successfully.");
            } else {
                test.fail("User can't change password.");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC09"));
            }
            Assert.assertEquals(successMessage, expectedWarning);
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