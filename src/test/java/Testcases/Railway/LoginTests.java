package Testcases.Railway;

import Railway.Common.Constant.Constant;
import Railway.dataObjects.User;
import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {
    public WebDriver driver;
    public HomePage homePage;
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

    @Test(description = "TC01 - User can login to Railway with valid account and password")
    public void TC01() throws NoSuchMethodException {
        test = extent.createTest("TC01", this.getClass().getDeclaredMethod("TC01").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);

            test.log(Status.INFO, "Login with valid account and password");
            User user = new User();
            loginPage.login(user);

            test.info("Click on \"Login\" button");

            String actualMsg = loginPage.getWelcomeMessage();
            String expectedMsg = "Welcome " + Constant.USERNAME;
            homePage.checkMsgWelcome(test, homePage, actualMsg, expectedMsg);
            Assert.assertEquals(actualMsg, expectedMsg);

        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: Welcome message is not displayed. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC01"));
            throw new RuntimeException(e);
        }
    }
    @Test(description = "TC02 - User can't login with blank \"Username\" textbox")
    public void TC02() throws NoSuchMethodException {
        test = extent.createTest("TC02", this.getClass().getDeclaredMethod("TC02").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);

            test.log(Status.INFO, "Login with blank \"Username\" textbox");
            User user = new User("",Constant.PASSWORD);
            loginPage.login(user);
            test.info("Click on \"Login\" button");
            String actualMsg = "There was a problem with your login and/or errors exist in your form.";
            String expectedMsg = loginPage.getErrorMessage();
            loginPage.checkMsgLoginFailed(test, homePage, actualMsg, expectedMsg);

            Assert.assertEquals(actualMsg, expectedMsg);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: User can login with blank \"Username\" textbox. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC02"));
            throw new RuntimeException(e);
        }
    }
    @Test(description = "TC03 - User can't login with invalid password")
    public void TC03() throws NoSuchMethodException {
        test= extent.createTest("TC03", this.getClass().getDeclaredMethod("TC03").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);

            test.log(Status.INFO, "Login with invalid password");
            User user = new User(Constant.USERNAME, "12345678");
            loginPage.login(user);

            test.info("Click on \"Login\" button");
            String actualMsg = "There was a problem with your login and/or errors exist in your form.";
            String expectedMsg = loginPage.getErrorMessage();
            loginPage.checkMsgLoginFailed(test, homePage, actualMsg, expectedMsg);

            Assert.assertEquals(actualMsg, expectedMsg);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: User can login with invalid password. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC03"));
            throw new RuntimeException(e);
        }
    }
    @Test(description = "TC05 - System shows message when user enters wrong password several times")
    public void TC05() throws NoSuchMethodException {
        test= extent.createTest("TC05", this.getClass().getDeclaredMethod("TC05").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);

            test.log(Status.INFO, "Login with invalid password");
            User user = new User(Constant.USERNAME, "12345678");
            for (int i = 0; i < 5; i++) {
                loginPage.login(user);
            }

            test.info("Click on \"Login\" button");
            String actualMsg = "You have used 5 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
            String expectedMsg = loginPage.getErrorMessage();
            loginPage.checkMsgWrongPass(test, homePage, actualMsg, expectedMsg);

            Assert.assertEquals(actualMsg, expectedMsg);
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: User can login with invalid password. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC05"));
        }

    }

    @Test(description = "TC08 - User can't login with an account hasn't been activated")
    public void TC08() throws NoSuchMethodException {
        test = extent.createTest("TC08", this.getClass().getDeclaredMethod("TC08").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Login\" tab");
            homePage.clickMenuItem("Login");
            LoginPage loginPage = new LoginPage(driver);

            test.log(Status.INFO, "Enter username and password of account hasn't been activated.");
            User user = new User("qwert1234", "12345678");
            loginPage.login(user);
            test.info("Click on \"Login\" button");
            String actualMsg = "Invalid username or password. Please try again.";
            String expectedMsg = loginPage.getErrorMessage();
            loginPage.checkMsgLoginFailed2(test, homePage, actualMsg, expectedMsg);
            Assert.assertEquals(actualMsg, expectedMsg);
        }
        catch (Exception e){
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: User can't login with an account hasn't been activated. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC08"));
        }
    }


    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

}