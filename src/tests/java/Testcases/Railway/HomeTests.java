package Testcases.Railway;

import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import Railway.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class HomeTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        String Username ="tuan41@gmail.com";
        String Password = "tuan41tuan41";
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.open();
        homePage.clickMenuItem("Login");
        loginPage = new LoginPage(driver);
        loginPage.login(Username, Password);
    }

    @Test
    public void TC06() {
        boolean cp = homePage.displayChangePasswordTab() && homePage.displayMyTicket() && homePage.displayLogOut();
        Assert.assertEquals(cp,true);

    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}