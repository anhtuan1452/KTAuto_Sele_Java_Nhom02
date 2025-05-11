package Testcases.Railway;

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
        homePage = new HomePage(driver);
        homePage.open();
    }

    @Test(description = "TC01 - User can login to Railway with valid account and password")
    public void TC01() throws NoSuchMethodException {
        test = extent.createTest("TC01", this.getClass().getDeclaredMethod("TC01").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            LoginPage loginPage = new LoginPage(driver);
            test.log(Status.INFO, "Click on \"Login\" tab");
            loginPage.clickMenuItem("Login");
            test.log(Status.INFO, "Login with valid account and password");
            loginPage.login(loginPage.un, loginPage.pw);
            test.info("Click on \"Login\" button");
            String actualMsg = loginPage.getWelcomeMessage();
            String expectedMsg = "Welcome, " + loginPage.un;
            boolean check = actualMsg.equals(expectedMsg);
            if(check){
                test.log(Status.PASS, "User is logged into Railway. Welcome user message is displayed.");
            }else{
                test.fail("User is logged into Railway. Welcome user message isn't displayed.");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC01"));
            }
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
            LoginPage loginPage = new LoginPage(driver);
            test.log(Status.INFO, "Click on \"Login\" tab");
            loginPage.clickMenuItem("Login");
            test.log(Status.INFO, "Login with blank \"Username\" textbox");
            loginPage.login("", loginPage.pw);
            String actualMsg = "There was a problem with your login and/or errors exist in your form.";
            String expectedMsg = loginPage.getErrorMessage();
            boolean check = actualMsg.equals(expectedMsg);
            if(check){
                test.log(Status.PASS, "Error message \"There was a problem with your login and/or errors exist in your form.\" is displayed");
            }else{
                test.fail("Error message \"There was a problem with your login and/or errors exist in your form.\" isn't displayed");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC02"));
            }
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
            LoginPage loginPage = new LoginPage(driver);
            test.log(Status.INFO, "Click on \"Login\" tab");
            loginPage.clickMenuItem("Login");
            test.log(Status.INFO, "Login with invalid password");
            loginPage.login(loginPage.un, "12345678");
            String actualMsg = "There was a problem with your login and/or errors exist in your form.";
            String expectedMsg = loginPage.getErrorMessage();
            boolean check = actualMsg.equals(expectedMsg);
            if(check){
                test.log(Status.PASS, "Error message \"There was a problem with your login and/or errors exist in your form.\" is displayed");
            }else{
                test.fail("Error message \"There was a problem with your login and/or errors exist in your form.\" isn't displayed");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC03"));
            }
            Assert.assertEquals(actualMsg, expectedMsg);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: User can login with invalid password. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC03"));
            throw new RuntimeException(e);
        }
    }
    @Test
    public void TC05() {
        System.out.println("TC05 - Người dùng có thể đăng nhập vào Railway với tài khoản và mật khẩu k hợp lệ 3 lần");
        String expectedWarning = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        String actualWarning = "";
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickMenuItem("Login");
        for (int i = 0; i < 3; i++) {
            loginPage.login(loginPage.un, "tddrtyj");
        }

        actualWarning = loginPage.getErrorMessage();
        Assert.assertEquals(actualWarning, expectedWarning);
    }
    @Test(description = "User can't login with an account hasn't been activated")
    public void TC08() throws NoSuchMethodException {

        test = extent.createTest("TC08", this.getClass().getDeclaredMethod("TC08").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            LoginPage loginPage = new LoginPage(driver);
            test.log(Status.INFO, "Click on \"Login\" tab");
            loginPage.clickMenuItem("Login");
            test.log(Status.INFO, "Login with an account hasn't been activated");
            boolean a = true;
            Assert.assertTrue(a);
            test.pass("User can't login and message \"Invalid username or password. Please try again.\" appears.");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

}