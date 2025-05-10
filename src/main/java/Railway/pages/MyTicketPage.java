package Railway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MyTicketPage extends GenetralPage {
    // Locators
    private final By pageHeader = By.xpath("//h1[contains(text(),'Manage ticket')]");
    private final By ticketTableRows = By.xpath("//table[@class='MyTable']/tbody/tr[contains(@class,'Row')]");

    // Constructor
    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    // Methods

    public boolean cancelTicket(String ticketId) {

            String cancelButtonXPath = String.format(".//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(%s)')]", ticketId);
            WebElement cancelButton = getElement(By.xpath(cancelButtonXPath));
            scrollElement(cancelButton);
            cancelButton.click();
            if (cancelButton == null) {
                return false;
            }
            return true;
    }

    public boolean confirmCancel(String ticketId) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        String cancelButtonXPath = String.format(".//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(%s)')]", ticketId);
        WebElement cancelButton = getNotElement(By.xpath(cancelButtonXPath));
        if (cancelButton == null) {
            return true;
        }
        return false;
    }

}