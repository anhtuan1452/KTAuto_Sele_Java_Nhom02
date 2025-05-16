package Testcases.Railway;

import Railway.dataObjects.Ticket;
import Railway.dataObjects.User;
import Railway.pages.BookTicketsPage;
import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import Railway.pages.MyTicketPage;
import Railway.Common.Constant.Constant;
import Railway.pages.GenetralPage;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookTicketsTests {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private BookTicketsPage bookTicketsPage;
    private MyTicketPage myTicketPage;
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
        loginPage = new LoginPage(driver);
        bookTicketsPage = new BookTicketsPage(driver);
        myTicketPage = new MyTicketPage(driver);
    }

    @Test(description = "TC04 - Login page displays when un-logged User clicks on \"Book ticket\" tab")
    public void TC04() throws NoSuchMethodException {
        test = extent.createTest("TC04", this.getClass().getDeclaredMethod("TC04").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage.open();

            test.log(Status.INFO, "Click on \"Book ticket\" tab");
            homePage.clickMenuItem("Book ticket");
            loginPage.checkLoginPageDisplayed(test, homePage);
        } catch (Exception e) {
            test.log(Status.FAIL, "Test thất bại: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC04"));
            throw new RuntimeException(e);
        }
    }

    @Test(description = "TC14 - User can book 1 ticket at a time")
    public void TC14() throws NoSuchMethodException {
        test = extent.createTest("TC14", this.getClass().getDeclaredMethod("TC14").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage.open();
            homePage.clickMenuItem("Login");

            test.log(Status.INFO, "Login with a valid account");
            User user = new User();
            loginPage.login(user);

            test.log(Status.INFO, "Click on \"Book ticket\" tab");
            homePage.clickMenuItem("Book ticket");


            LocalDate departureDate = LocalDate.now().plusDays(10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            String formattedDate = departureDate.format(formatter);
            Ticket ticket = new Ticket(formattedDate,"Nha Trang", "Sài Gòn", "Soft bed with air conditioner", "1");

            test.log(Status.INFO, "Book ticket");
            bookTicketsPage.bookTicket(ticket, homePage, test);

            test.log(Status.INFO, "Check ticket information");
            bookTicketsPage.checkTicketInformation(ticket, homePage, test);

            homePage.clickMenuItem("Log out");
        } catch (Exception e) {
            test.log(Status.FAIL, "Test fail: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC14"));
            throw new RuntimeException(e);
        }
    }

    @Test(description = "TC16 - User can cancel a ticket")
    public void TC16() throws NoSuchMethodException {
        test = extent.createTest("TC16", this.getClass().getDeclaredMethod("TC16").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website");
            homePage.open();

            test.log(Status.INFO, "Login with a valid account");
            loginPage.clickMenuItem("Login");
            User user = new User();
            loginPage.login(user);

            test.log(Status.INFO, "Click on \"Book ticket\" tab");
            homePage.clickMenuItem("Book ticket");

            LocalDate departureDate = LocalDate.now().plusDays(10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            String formattedDate = departureDate.format(formatter);
            Ticket ticketDefault = new Ticket(formattedDate);
            bookTicketsPage.bookTicket(ticketDefault, homePage, test);

            String currentUrl = driver.getCurrentUrl();
            String id = currentUrl.split("id=")[1];

            test.log(Status.INFO, "Click on \"My ticket\" tab");
            homePage.clickMenuItem("My ticket");

            test.log(Status.INFO, "Click on \"Cancel\" button of ticket which user want to cancel");
            boolean ans = myTicketPage.cancelTicket(id,test,homePage) && myTicketPage.confirmCancel(id,test,homePage);
            Assert.assertTrue(ans, "Cancel ticket successfully");
        } catch (Exception e) {
            test.log(Status.FAIL, "Cancel ticket fail: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC16"));
            throw new RuntimeException(e);
        }

    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}