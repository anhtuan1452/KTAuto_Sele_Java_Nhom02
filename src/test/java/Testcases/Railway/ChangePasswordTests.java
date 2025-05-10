package Testcases.Railway;

import Railway.pages.*;
import Railway.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ChangePasswordTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;



    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.clickMenuItem("Register");
        RegisterPage registerPage = new RegisterPage(driver);
        String a = homePage.generateGmail();
        registerPage.registerAccount(a, homePage.pw, homePage.pw, "11111111");
        homePage.clickMenuItem("Login");
        loginPage = new LoginPage(driver);
        loginPage.login(a, homePage.pw);
    }

    @Test
    public void TC09(){
        homePage.clickMenuItem("Change password");
        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        changePasswordPage.changePassword(homePage.pw, "12345678","12345678");
        String expectedWarning = "Your password has been updated!";
        String successMessage = changePasswordPage.getSuccessMessage();
        Assert.assertEquals(successMessage, expectedWarning);
    }


    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}