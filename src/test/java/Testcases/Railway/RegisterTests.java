package Testcases.Railway;

import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import Railway.pages.RegisterPage;
import Railway.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;



    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.clickMenuItem("Register");
    }
    @Test
    public void TC07(){
        RegisterPage registerPage = new RegisterPage(driver);
        String a = homePage.generateGmail();
        registerPage.registerAccount(a,homePage.pw,homePage.pw,"11111111");
        String actualMsg = "Thank you for registering your account";
        String expectedMsg= registerPage.getCompeleteMessenger();
        Assert.assertEquals(actualMsg,expectedMsg);
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
    public void teardown() {
        driver.quit();
    }
}