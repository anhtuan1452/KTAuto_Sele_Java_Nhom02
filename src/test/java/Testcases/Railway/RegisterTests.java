package Testcases.Railway;


import Railway.Common.Constant.Constant;
import Railway.pages.*;
import Railway.dataObjects.Enum.Enum;
import Railway.dataObjects.User;
import Railway.pages.HomePage;
import Railway.pages.RegisterPage;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterTests {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUpClass() {
        // Khởi tạo Extent Reports
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.clickMenuItem("Register");
        registerPage = new RegisterPage(driver);
    }


    @Test(description = "TC07 - User can create new account")
    public void TC07() throws NoSuchMethodException{
        test = extent.createTest("TC07", this.getClass().getDeclaredMethod("TC07").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage.open();

            test.log(Status.INFO, "Click on \"Register\" tab");
            homePage.clickMenuItem("Register");
            registerPage = new RegisterPage(driver);

            test.log(Status.INFO, "Enter valid information into all fields");
            String email = homePage.generateGmail();
            User user = new User(email, "123456789", "123456789", "11111111");
            registerPage.registerAccount(user);

            test.info("Click on \"Register\" button");
            String  expectedMsg= "Thank you for registering your account";
            String actualMsg= registerPage.getMsgSuccessCreate();
            registerPage.checkMsgCorrect(test,homePage,actualMsg,expectedMsg);
            Assert.assertEquals(actualMsg, expectedMsg);
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: The success message is not as expected.. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC07"));
            throw new RuntimeException(e);
        }

    }

    @Test(description = "TC10 - User can't create account with 'Confirm password' is not the same with 'Password'")
    public void TC10() throws NoSuchMethodException {
        test = extent.createTest("TC10", this.getClass().getDeclaredMethod("TC10").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website at: " + java.time.LocalDateTime.now());
            homePage.open();

            test.log(Status.INFO, "Click on \"Register\" tab at: " + java.time.LocalDateTime.now());
            homePage.clickMenuItem("Register");

            test.log(Status.INFO, "Enter valid information except Confirm password not matching Password at: " + java.time.LocalDateTime.now());
            String email = homePage.generateGmail();
            User user = new User(email, "123456789", "987654321", "11111111");
            registerPage.registerAccount(user);

            test.log(Status.INFO, "Click on \"Register\" button at: " + java.time.LocalDateTime.now());
            registerPage.clickBtnRegister();

            String expectedFormMsg = "There're errors in the form. Please correct the errors and try again.";
            registerPage.checkFormErrorMessageDisplayed(expectedFormMsg, homePage, test);

        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage() + " at " + java.time.LocalDateTime.now());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC10"));
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }
    @Test(description = "TC11 - User can't create account while password and PID fields are empty")
    public void TC11() throws NoSuchMethodException {
        test = extent.createTest("TC11", this.getClass().getDeclaredMethod("TC11").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website at: " + java.time.LocalDateTime.now());
            homePage.open();

            test.log(Status.INFO, "Click on \"Register\" tab at: " + java.time.LocalDateTime.now());
            homePage.clickMenuItem("Register");

            test.log(Status.INFO, "Enter valid email and leave password and PID fields empty at: " + java.time.LocalDateTime.now());
            String email = homePage.generateGmail();
            User user = new User(email, "", "", "");
            registerPage.registerAccount(user);

            test.log(Status.INFO, "Click on \"Register\" button at: " + java.time.LocalDateTime.now());
            registerPage.clickBtnRegister();

            String expectedFormMsg = "There're errors in the form. Please correct the errors and try again.";
            String expectedPasswordMsg = "Invalid password length";
            String expectedPIDMsg = "Invalid ID length";
            registerPage.checkFormErrorMessageDisplayed(expectedFormMsg, homePage,test);
            registerPage.checkMessageBlankPasswordDisplayed(expectedPasswordMsg, homePage, test);
            registerPage.checkMessageBlankPidDisplayed(expectedPIDMsg,homePage, test);

        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage() + " at " + java.time.LocalDateTime.now());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC11"));
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }
    @AfterMethod
    public void teardown() {
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