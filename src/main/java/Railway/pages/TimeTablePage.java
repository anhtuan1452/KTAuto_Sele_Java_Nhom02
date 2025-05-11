package Railway.pages;

import Railway.dataObjects.Enum.Enum;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TimeTablePage extends GenetralPage {
    private By btnBookTicket = By.xpath("//div[@id='content']//input[contains(@value,'Book ticket')]");
    private String LinkBockTicket = "//tr[td[2][contains(text(),'%s')] and td[3][contains(text(),'%s')]]//a[text()='book ticket']";
    private String LinkTableCheck = "//tr[td[%s][contains(text(),'%s')]]";

    //Contructor
    protected WebElement getBtnBookTicket() {
        return getElement(btnBookTicket);
    }
    public TimeTablePage(WebDriver driver){
        super(driver);
    }

    public void clickBookTicket(String From, String Arrive, HomePage homePage, ExtentTest test) {
        int maxRetries = 10;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            String xpath = String.format(LinkBockTicket, From, Arrive);
            System.out.println("Generated XPath (attempt " + attempt + "): " + xpath);
            WebElement linkBookticket = getNotElement(By.xpath(xpath));
            if (linkBookticket != null) {
                scrollElement(linkBookticket);
                linkBookticket.click();
                scrollElement(getBtnBookTicket());
                sleepSafe(300);
                getBtnBookTicket().click();
                return;
            } else {
                System.out.println("Element not found, attempt " + attempt);
                if (attempt < maxRetries) {
                    driver.navigate().refresh(); // load lại trang
                    sleepSafe(1000); // chờ một chút cho trang load lại
                } else {
                    test.fail("Book ticket link not found after "+ maxRetries + " retry. From: " + From + " - Arrive: " + Arrive);
                    test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "BookTicketError"));
                }
            }
        }
    }

    private int getColumnIndex(String fieldName) {
        try{
            Railway.dataObjects.Enum.Enum.TableSuccessfulBooking field = Enum.TableSuccessfulBooking.fromLabel(fieldName);
            return field.getColumnIndex();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid field name: "+ fieldName);
        }
    }
    public Boolean checkInfor(String fieldName, String stringCheck){
        int index = getColumnIndex(fieldName);
        String xpath = String.format(LinkTableCheck, index,stringCheck);
        System.out.println("Generated XPath: " + xpath);
        WebElement b = getElement(By.xpath(xpath));
        return b != null;
    }
    public void checkInforArrivalDeparture(String Departure, String Arrive, HomePage homePage, ExtentTest test){
        boolean checkArrive = checkInfor("Arrive Station", Arrive);
        boolean checkDeparture = checkInfor("Depart Station", Departure);
        if (checkArrive && checkDeparture) {
            System.out.println("Arrival and Departure information is correct.");
            test.log(Status.PASS,"'\"Book ticket\" page is loaded with correct  \"Depart from\" and \"Arrive at\" values.");
        } else {
            System.out.println("Arrival and/or Departure information is incorrect.");
            test.fail("Book ticket page is not loaded with correct \"Depart from\" and \"Arrive at\" values.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "BookTicketError"));
        }
    }


//test case
}
