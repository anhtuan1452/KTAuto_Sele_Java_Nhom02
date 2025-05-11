package Testcases.Railway;

import Railway.dataObjects.User;
import Railway.pages.BookTicketsPage;
import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import Railway.pages.MyTicketPage;
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

        } catch (Exception e) {
            test.log(Status.FAIL, "Test thất bại: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC14"));
            throw new RuntimeException(e);
        }
        LocalDate departureDate = LocalDate.now().plusDays(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = departureDate.format(formatter);
        String departStation = "Nha Trang";
        String arriveStation = "Sài Gòn";
        String seatType = "Soft bed with air conditioner";
        String ticketAmount = "1";
        try {
            test.log(Status.INFO, "Book ticket with departure date: " + formattedDate + ", departure station: "+ departStation +
                            ", arrival station: " + arriveStation + ", seat type: " + seatType + ", ticket amount: " + ticketAmount);
            bookTicketsPage.bookTicket(formattedDate, departStation, arriveStation, seatType, ticketAmount, test, homePage);

        } catch (Exception e) {
            test.log(Status.FAIL, "Booking cancel: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC14"));
            throw new RuntimeException(e);
        }
        boolean ans= false;
        try {
            test.log(Status.INFO, "Check ticket information");
            ans = bookTicketsPage.isTicketInfoCorrect("Depart Date", formattedDate)
                    && bookTicketsPage.isTicketInfoCorrect("Depart Station", departStation)
                    && bookTicketsPage.isTicketInfoCorrect("Arrive Station", arriveStation)
                    && bookTicketsPage.isTicketInfoCorrect("Seat Type", seatType)
                    && bookTicketsPage.isTicketInfoCorrect("Amount", ticketAmount)
                    && bookTicketsPage.isSuccessfulTicketPurchaseNotification();
            homePage.clickMenuItem("Log out");
            Assert.assertTrue(ans, "Ticket information not True");
            test.log(Status.PASS, "Booking success and Message \"Ticket booked successfully!\" displays.");
        } catch (Exception e) {
            test.log(Status.FAIL, "Check ticket cancel: " + e.getMessage());
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
            loginPage.clickMenuItem("Login");
            test.log(Status.INFO, "Login with a valid account");
            User user = new User();
            loginPage.login(user);
            test.log(Status.INFO, "Click on \"Book ticket\" tab");
            homePage.clickMenuItem("Book ticket");
        } catch (Exception e) {
            test.log(Status.FAIL, "Not -> Book ticket: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC16"));
            throw new RuntimeException(e);
        }

        LocalDate departureDate = LocalDate.now().plusDays(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = departureDate.format(formatter);
        String departStation = "Quảng Ngãi";
        String arriveStation = "Sài Gòn";
        String seatType = "Hard seat";
        String ticketAmount = "2";
        try{
            bookTicketsPage.bookTicket(formattedDate, departStation, arriveStation, seatType, ticketAmount, test, homePage);
        } catch (Exception e) {
            test.log(Status.FAIL, "Booking cancel: " + e.getMessage());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC16"));
            throw new RuntimeException(e);
        }
        String currentUrl = driver.getCurrentUrl();
        String id = currentUrl.split("id=")[1];
        try {
            test.log(Status.INFO, "Click on \"My ticket\" tab");
            homePage.clickMenuItem("My ticket");
            test.log(Status.INFO, "Click on \"Cancel\" button of ticket which user want to cancel");
            boolean ans = myTicketPage.cancelTicket(id) && myTicketPage.confirmCancel(id);
            test.log(Status.INFO, "Click on \"OK\" button on Confirmation message \"Are you sure?\"");
            Assert.assertTrue(ans, "Cancel ticket failed");
            test.log(Status.PASS, "Cancel ticket success");
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