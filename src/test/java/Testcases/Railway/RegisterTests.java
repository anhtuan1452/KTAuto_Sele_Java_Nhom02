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

public class RegisterTests {
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
    public void setup() {
        driver = DriverFactory.getDriver();

    }

    @Test(description = "TC07 - User can create new account")
    public void TC07() throws NoSuchMethodException{
        test = extent.createTest("TC07", this.getClass().getDeclaredMethod("TC07").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage = new HomePage(driver);
            homePage.open();

            test.log(Status.INFO, "Click on \"Register\" tab");
            homePage.clickMenuItem("Register");
            RegisterPage registerPage = new RegisterPage(driver);

            test.log(Status.INFO, "Enter valid information into all fields");
            String email = homePage.generateGmail();
            registerPage.registerAccount(email,homePage.pw,homePage.pw,"11111111");

            test.info("Click on \"Send Instructions\" button");

            String actualMsg = "Thank you for registering your account";
            String expectedMsg= registerPage.getCompeleteMessenger();

            boolean check = actualMsg.equals(expectedMsg);
            if(check){
                test.log(Status.PASS, "New account is created and message \"Thank you for registering your account\" appears.");
            }else{
                test.fail("The success message is not as expected.");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC07"));
            }
            Assert.assertEquals(actualMsg, expectedMsg);

        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: The success message is not as expected.. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC07"));
            throw new RuntimeException(e);
        }

    }

    @Test
    public void TC10(){
        RegisterPage registerPage = new RegisterPage(driver);
        String a = homePage.generateGmail();
        registerPage.registerAccount(a,homePage.pw,"12345678","11111111");
        String actualMsg = "There're errors in the form. Please correct the errors and try again.";
        String expectedMsg= registerPage.getMessenger();
        Assert.assertEquals(actualMsg,expectedMsg);
    }
    @Test
    public void TC11(){
        RegisterPage registerPage = new RegisterPage(driver);
        String a = homePage.generateGmail();
        registerPage.registerAccount(a,"","","");
        boolean ans = registerPage.MessageBlankPib();
        Assert.assertEquals(ans,true);
    }


//    @Test //tc12
//    public void testFogetPassWord() { // TC01
//        loginPage = new LoginPage(driver);
//        loginPage.clickFogetPassWord();
//        loginPage.enterCredentials("tuan41@gmail.com", "tuan41tuan41");
//        loginPage.clickLogin();
//        Assert.assertEquals(loginPage.getWelcomeMessage(), "Welcome to Safe Railway");
//    }
//
//    @Test
//    public void testLoginWithBlankUsername() { // TC02
//        homePage.open();
//        homePage.clickMenuItem("Login");
//        loginPage.enterCredentials("", "password123");
//        loginPage.clickLogin();
//        Assert.assertEquals(loginPage.getErrorMessage(), "There was a problem with your login and/or errors exist in your form.");
//    }


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