package Testcases.Railway;

import Railway.Common.Constant.Constant;
import Railway.dataObjects.User;
import Railway.pages.*;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TimeTableTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private TimeTablePage timeTablePage;
    private String generatedEmail;
    private ExtentReports extent;
    private ExtentTest test;
    @BeforeMethod
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
        generatedEmail = registerPage.generateGmail();
        User user = new User(generatedEmail, Constant.PASSWORD,Constant.PASSWORD,"11111111");
        registerPage.registerAccount(user);
        loginPage = new LoginPage(driver);
    }

    @Test(description = "User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page")
    public void TC15() {
        test = extent.createTest("TC15", "User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page");
        try {
            test.info("Navigate to QA Railway Website");
            homePage.open();

            test.info("Click on \"Login\" tab");
            homePage.clickMenuItem("Login");

            test.info("Login with valid account");
            User user = new User(generatedEmail,Constant.PASSWORD);
            loginPage.login(user);

            test.info("Click on \"Timetable\" tab");
            homePage.clickMenuItem("Timetable");

            test.info("Click on \"book ticket\" link of the route from \"Huế\" to \"Sài Gòn\"");
            timeTablePage = new TimeTablePage(driver);
            timeTablePage.clickBookTicket("Huế","Sài Gòn",homePage,test);
            timeTablePage.checkInforArrivalDeparture("Huế","Sài Gòn",homePage,test);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            test.fail("Test failed: Unable to open \"Book ticket\" page. Error: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC15"));
            throw new RuntimeException(e);
        }
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @AfterClass
    public void cleanUp() {
        if (extent != null) {
            extent.flush();
        }
    }

}
