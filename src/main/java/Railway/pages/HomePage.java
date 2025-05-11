package Railway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends GenetralPage {
    // Locators
    private By menu = By.id("menu");
    private final By welcomeMessage = By.xpath("//div[@class='account']/strong");

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


}