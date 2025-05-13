package Railway.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class GenetralPage {
    //Locator
    public final String menuItems = "//div[@id='menu']//span[contains(text(),'%s')]";
    public final By tabLogin = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml'");
    public final By tabLogout = By.xpath(String.format(menuItems,"Log out"));
    public final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");
    public final By h1 = By.xpath("//*[@id=\"content\"]/h1");

    public final By headerMyticket = By.xpath(String.format(menuItems,"My ticket"));
    public final By headerChangePassword = By.xpath(String.format(menuItems,"Change password"));


    public String pw="tuan41tuan41";
    public WebDriver driver ;
    public WebDriverWait wait ;


    //Elements
    protected WebElement getTabLogin(){
        return getElement(tabLogin,true);
    }
    protected WebElement getTabLogout(){
        return getElement(tabLogout,true);
    }
    protected WebElement getLblWelcomeMessage(){
        return getElement(lblWelcomeMessage);
    }
    protected String getMenuItems(){return menuItems;}

    //method
    public GenetralPage(WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public String getWelcomeMessage() {
        WebElement welcomeElement = getLblWelcomeMessage();
        if (welcomeElement == null || !welcomeElement.isDisplayed()) {
            throw new NoSuchElementException("Welcome message element not found or not displayed");
        }
        return welcomeElement.getText();
    }
    public String generateGmail() {
        Random random = new Random();
        StringBuilder account = new StringBuilder("test");

        // Tạo 10 chữ số ngẫu nhiên
        for (int i = 0; i < 10; i++) {
            account.append(random.nextInt(10));
        }

        account.append("@gmail.com");
        return account.toString();
    }
    public void clickMenuItem(String menuText) {
        String XpathMenuItem = String.format(getMenuItems(),menuText);
        getElement(By.xpath(XpathMenuItem),true).click();
    }

    public void scrollElement(WebElement key) {
        if (key != null) {
            try {
                // Đảm bảo phần tử đã hiển thị
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(key));

                // Cuộn phần tử vào view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", key);
            } catch (Exception e) {
                System.out.println("Error while scrolling: " + e.getMessage());
            }
        } else {
            System.out.println("Element is null, cannot scroll.");
        }
    }

    public WebElement getElement(By key, boolean requireClickable) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            if (requireClickable) {
                return wait.until(ExpectedConditions.elementToBeClickable(key));
            } else {
                return wait.until(ExpectedConditions.presenceOfElementLocated(key));
            }
        } catch (TimeoutException e) {
            throw e;
        }
    }
    public WebElement getNotElement(By key) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.elementToBeClickable(key));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public WebElement getElement(By key) {
        return getElement(key, false);
    }
    public void sleepSafe(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // tốt hơn
        }
    }

    public String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";
        File directory = new File(System.getProperty("user.dir") + "/screenshots/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }


    public void checkLogOutTabDisplayed(ExtentTest test, HomePage homePage) {
        if(getTabLogout().isDisplayed()){
            test.log(Status.PASS, "\"Logout\" tabs are displayed.");
        }else{
            test.fail("\"Logout\" tabs are not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
        }
    }

    public void checkMyTicketTabDisplayed(ExtentTest test, HomePage homePage) {
        if(getElement(headerMyticket).isDisplayed()){
            test.log(Status.PASS, "\"My ticket\" tabs are displayed.");
        }else{
            test.fail("\"My ticket\" tabs are not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
        }
    }

    public void checkChangePasswordTabDisplayed(ExtentTest test, HomePage homePage) {
        if(getElement(headerChangePassword).isDisplayed()){
            test.log(Status.PASS, "\"Change password\" tabs are displayed.");
        }else{
            test.fail("\"Change password\" tabs are not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC06"));
        }
    }

    public void checkMsgWelcome(ExtentTest test, HomePage homePage, String expectedMsg, String actualMsg) {
        if(expectedMsg.equals(actualMsg)){
            test.log(Status.PASS, " Welcome user message is displayed.");
        }else{
            test.fail(" Welcome user message is not displayed.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC01"));
        }
    }

}
