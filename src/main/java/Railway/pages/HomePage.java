package Railway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends GenetralPage {
    // Locators
    private By menu = By.id("menu");
    private final By welcomeMessage = By.xpath("//div[@class='account']/strong");
    private final By myTicketTab = By.xpath("//*[@id=\"menu\"]/ul/li[7]");
    private final By changePasswordTab = By.xpath("//*[@id=\"menu\"]/ul/li[8]");
    private final By logoutTab = By.xpath("//*[@id=\"menu\"]/ul/li[9]");
    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
        open();
    }
    public void open() {
        driver.get("http://railwayb1.somee.com");
    }

    protected String getTitle() {
        return driver.getTitle();
    }



    //Method
    public Boolean displayMyTicket() {
        WebElement myTicketElement = driver.findElement(myTicketTab); // Use findElement to locate the element
        return myTicketElement.isDisplayed();
    }
    public Boolean displayLogOut() {
        WebElement LogOut = driver.findElement(logoutTab); // Use findElement to locate the element
        return LogOut.isDisplayed();
    }
    public Boolean displayChangePasswordTab() {
        WebElement myTicketElement = driver.findElement(changePasswordTab); // Use findElement to locate the element
        return myTicketElement.isDisplayed();
    }

}