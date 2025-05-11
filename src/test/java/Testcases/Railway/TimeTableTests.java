package Testcases.Railway;

import Railway.Common.Constant.Constant;
import Railway.dataObjects.User;
import Railway.pages.*;
import Railway.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TimeTableTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private TimeTablePage timeTablePage;


    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.clickMenuItem("Register");
        registerPage = new RegisterPage(driver);
        String a= registerPage.generateGmail();
        registerPage.registerAccount(a, registerPage.pw,registerPage.pw,"11111111");
        registerPage.clickMenuItem("Login");
        loginPage = new LoginPage(driver);
        User user = new User(a, Constant.PASSWORD);
        loginPage.login(user);
        homePage.clickMenuItem("Timetable");
        timeTablePage = new TimeTablePage(driver);
    }

    @Test
    public void TC15() {
        Boolean a = timeTablePage.CheckBookTicket("Huế","Sài Gòn");
        Boolean b = timeTablePage.checkInfor("Arrive Station","Sài Gòn") && timeTablePage.checkInfor("Depart Station","Huế");
        Boolean c = a && b;
        Assert.assertEquals(c,true);
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
