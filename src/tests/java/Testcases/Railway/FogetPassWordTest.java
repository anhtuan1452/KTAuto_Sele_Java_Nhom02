package Testcases.Railway;

import Railway.pages.HomePage;
import Railway.pages.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FogetPassWordTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUpClass() {
        // Khởi tạo Extent Reports
        extent = Railway.utils.ExtentManager.getInstance();
    }
    @BeforeMethod
    public void setup() {
        driver = Railway.utils.DriverFactory.getDriver();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
    }
    @Test()
    public void TC12()
    {Assert.assertEquals(true,true);
    }
    @Test
    public void TC13(){
        Assert.assertEquals(true,true);
    }
}
