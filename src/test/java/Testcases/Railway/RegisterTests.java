package Testcases.Railway;

import Railway.dataObjects.Enum.Enum;
import Railway.dataObjects.User;
import Railway.pages.HomePage;
import Railway.pages.RegisterPage;
import Railway.utils.DriverFactory;
import Railway.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterTests {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUpClass() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.clickMenuItem("Register");
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void TC07() {
        RegisterPage registerPage = new RegisterPage(driver);
        String email = homePage.generateGmail();
        registerPage.registerAccount(email, homePage.pw, homePage.pw, "11111111");
        String actualMsg = "Thank you for registering your account";
        String expectedMsg = registerPage.getCompeleteMessenger();
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    @Test(description = "TC10 - User can't create account with 'Confirm password' is not the same with 'Password'")
    public void TC10() throws NoSuchMethodException {
        test = extent.createTest("TC10", this.getClass().getDeclaredMethod("TC10").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website and click 'Register' tab at: " + java.time.LocalDateTime.now());
            String email = homePage.generateGmail();
            User user = new User(email, "123456789", "987654321", "11111111");
            test.log(Status.INFO, "Enter valid information except 'Confirm password' not matching 'Password'");
            test.log(Status.INFO, "Email: " + user.getEmail());
            test.log(Status.INFO, "Password: " + user.getPassword());
            test.log(Status.INFO, "Confirm Password: " + user.getConfirmPassword());
            test.log(Status.INFO, "PID: " + user.getPid());
            registerPage.registerAccount(user.getEmail(), user.getPassword(), user.getConfirmPassword(), user.getPid());
            test.log(Status.INFO, "Click on 'Register' button at: " + java.time.LocalDateTime.now());

            String expectedMsg = "There're errors in the form. Please correct the errors and try again.";
            String actualMsg = registerPage.getMessenger();
            test.log(Status.INFO, "Expected message: " + expectedMsg);
            test.log(Status.INFO, "Actual message: " + actualMsg);
            boolean msgMatch = actualMsg.equals(expectedMsg);
            test.log(Status.INFO, "Message match result: " + msgMatch);
            Assert.assertEquals(actualMsg, expectedMsg, "Error message should match expected message");

            // Kiểm tra lỗi cụ thể cho Confirm Password
            String confirmPasswordErrorXPath = String.format("//input[@id='%s']/following-sibling::label[contains(@class, 'validation-error')]", "confirmPassword");
            test.log(Status.INFO, "Checking Confirm Password error message...");
            WebElement confirmPasswordError = driver.findElement(By.xpath(confirmPasswordErrorXPath));
            boolean confirmPasswordErrorDisplayed = confirmPasswordError.isDisplayed();
            Assert.assertTrue(confirmPasswordErrorDisplayed, "Confirm Password error message not displayed");
            test.log(Status.INFO, "Confirm Password error message displayed: " + confirmPasswordErrorDisplayed);

            test.log(Status.PASS, "Error message displayed successfully. Test completed at: " + java.time.LocalDateTime.now());
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage() + " at " + java.time.LocalDateTime.now());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC10"));
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }
    @Test(description = "TC11 - User can't create account while password and PID fields are empty")
    public void TC11() throws NoSuchMethodException {
        test = extent.createTest("TC11", this.getClass().getDeclaredMethod("TC11").getAnnotation(Test.class).description());
        try {
            test.log(Status.INFO, "Navigate to QA Railway Website and click 'Register' tab at: " + java.time.LocalDateTime.now());
            String email = homePage.generateGmail();
            User user = new User(email, "", "", "");
            test.log(Status.INFO, "Enter valid email and leave password and PID fields empty");
            test.log(Status.INFO, "Email: " + user.getEmail());
            test.log(Status.INFO, "Password: " + user.getPassword());
            test.log(Status.INFO, "Confirm Password: " + user.getConfirmPassword());
            test.log(Status.INFO, "PID: " + user.getPid());
            registerPage.registerAccount(user.getEmail(), user.getPassword(), user.getConfirmPassword(), user.getPid());
            test.log(Status.INFO, "Click on 'Register' button at: " + java.time.LocalDateTime.now());

            String expectedFormMsg = "There're errors in the form. Please correct the errors and try again.";
            String actualFormMsg = registerPage.getMessenger();
            test.log(Status.INFO, "Expected form message: " + expectedFormMsg);
            test.log(Status.INFO, "Actual form message: " + actualFormMsg);
            boolean formMsgMatch = actualFormMsg.equals(expectedFormMsg);
            test.log(Status.INFO, "Form message match result: " + formMsgMatch);
            Assert.assertEquals(actualFormMsg, expectedFormMsg, "Form error message should match");

            String expectedPasswordMsg = "Invalid password length"; // Bỏ dấu chấm để khớp với thực tế
            String passwordErrorXPath = String.format("//input[@id='%s']/following-sibling::label[contains(@class, 'validation-error')]",
                    Enum.RegistrationField.PASSWORD.getLabel().toLowerCase());
            test.log(Status.INFO, "Checking password error message...");
            WebElement passwordError = driver.findElement(By.xpath(passwordErrorXPath));
            boolean passwordErrorDisplayed = passwordError.isDisplayed();
            String passwordErrorText = passwordError.getText().trim();
            test.log(Status.INFO, "Password error message displayed: " + passwordErrorDisplayed);
            test.log(Status.INFO, "Password error message content: " + passwordErrorText);
            Assert.assertTrue(passwordErrorDisplayed, "Password error message not displayed");
            Assert.assertEquals(passwordErrorText, expectedPasswordMsg, "Password error message content mismatch");

            String expectedPidMsg = "Invalid ID length"; // Bỏ dấu chấm để khớp với thực tế
            String pidErrorXPath = String.format("//input[@id='%s']/following-sibling::label[contains(@class, 'validation-error')]",
                    Enum.RegistrationField.PID.getLabel().toLowerCase());
            test.log(Status.INFO, "Checking PID error message...");
            WebElement pidError = driver.findElement(By.xpath(pidErrorXPath));
            boolean pidErrorDisplayed = pidError.isDisplayed();
            String pidErrorText = pidError.getText().trim();
            test.log(Status.INFO, "PID error message displayed: " + pidErrorDisplayed);
            test.log(Status.INFO, "PID error message content: " + pidErrorText);
            Assert.assertTrue(pidErrorDisplayed, "PID error message not displayed");
            Assert.assertEquals(pidErrorText, expectedPidMsg, "PID error message content mismatch");

            test.log(Status.PASS, "All error messages displayed successfully. Test completed at: " + java.time.LocalDateTime.now());
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage() + " at " + java.time.LocalDateTime.now());
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC11"));
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }
    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDownClass() {
        if (extent != null) {
            extent.flush();
        }
    }
}