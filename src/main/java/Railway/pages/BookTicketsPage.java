package Railway.pages;

import Railway.dataObjects.Enum.Enum;
import Railway.dataObjects.Ticket;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookTicketsPage extends GenetralPage {
    //Locators
    private final By dateDropdown = By.xpath("//select[@name='Date']");
    private final By departDropdown = By.xpath("//select[@name='DepartStation']");
    private final By arriveDropdown = By.xpath("//select[@name='ArriveStation']");
    private final By seatTypeDropdown = By.xpath("//select[@name='SeatType']");
    private final By ticketAmountDropdown = By.xpath("//select[@name='TicketAmount']");
    private final By bookTicketButton = By.xpath("//input[@value='Book ticket']");
    private final By successMessage = By.xpath("//h1[contains(.,'successfully!')]");
    private String dynamicXpathInformationTicket = "//tr[2]/td[%s][contains(text(),'%s')]";
    private String LinkCancelBookByIDTicket = "//table[@class='MyTable']//tr[td[input[contains(@onclick,'%s')]]]/td[count(//table[@class='MyTable']//th[text()='%s']/preceding-sibling::th) + 1]";
    private String LinkOption = ".//option[contains(text(),'%s')]";

    //Contructors
    protected WebElement DateDropdown(){return getElement(dateDropdown);};
    protected WebElement DepartDropdown() {
        return getElement(departDropdown);
    }
    protected WebElement ArriveDropdown() {return getElement(arriveDropdown,true);}
    protected WebElement SeatTypeDropdown() {return getElement(seatTypeDropdown,true);}
    protected WebElement TicketAmountDropdown() {return getElement(ticketAmountDropdown,true);}
    protected WebElement BookTicketButton() {return getElement(bookTicketButton,true);}
    protected WebElement SuccessMessage() {return getElement(successMessage,true);}
    protected String getLinkCancelBookByIDTicket(){
        return LinkCancelBookByIDTicket;
    }
    protected String getDynamicXpathInformationTicket() {
        return dynamicXpathInformationTicket;
    }
    protected String getOption() {
        return LinkOption;
    }
    //Method
    public BookTicketsPage(WebDriver driver) {
        super(driver);
    }

    private void selectDropdown(WebElement dropdownElement, String value) {
        WebElement dropdown = dropdownElement;
        scrollElement(dropdown);
        dropdown.click();
        sleepSafe(1000);
        String optionXpath = String.format(getOption(), value);
        System.out.println("Option XPath: " + optionXpath);
        WebElement option = getElement(By.xpath(optionXpath),true);
        option.click();

    }

    public void pickDate(String date) {
        selectDropdown(DateDropdown(), date);
    }

    public void pickFrom(String departure) {
        selectDropdown(DepartDropdown(), departure);
    }

    public void pickArrive(String arrival) {selectDropdown(ArriveDropdown(), arrival);}

    public void pickSeatType(String seatType) {
        selectDropdown(SeatTypeDropdown(), seatType);
    }

    public void pickTicketAmount(String amount) {
        selectDropdown(TicketAmountDropdown(), amount);
    }


    private int getColumnIndex(String fieldName) {
        try{
            Enum.TableSuccessfulBooking field = Enum.TableSuccessfulBooking.fromLabel(fieldName);
            return field.getColumnIndex();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid field name: "+ fieldName);
        }
    }

    public boolean isTicketInfoCorrect(String fieldName, String check){
        int index = getColumnIndex(fieldName);
        String xpath = String.format(getDynamicXpathInformationTicket(), index,check);
        System.out.println("Generated XPath: " + xpath);
        WebElement InforElement = getElement(By.xpath(xpath));
        String text = InforElement.getText().trim();
        return text.equals(check);
    }
    public boolean checkTicketInformation(Ticket ticket, HomePage homePage, ExtentTest test) {
        boolean checkArrive = isTicketInfoCorrect("Arrive Station", ticket.getArriveStation());
        boolean checkDeparture = isTicketInfoCorrect("Depart Station", ticket.getDepartStation());
        boolean checkDate = isTicketInfoCorrect("Depart Date", ticket.getDate());
        boolean checkSeatType = isTicketInfoCorrect("Seat Type", ticket.getSeatType());
        boolean checkTicketAmount = isTicketInfoCorrect("Amount", ticket.getTicketAmount());
        if(checkArrive && checkDeparture && checkDate && checkSeatType && checkTicketAmount){
            test.log(Status.PASS, "Ticket information is correct");
            return true;
        } else {
            test.fail("Ticket information is not correct");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "BookTicketError"));
            return false;
        }

    }
    public void bookTicket(Ticket ticket,HomePage homePage, ExtentTest test) {
        int maxRetries = 5;
        boolean isBooked = false;

        for (int attempt = 1; attempt <= maxRetries && !isBooked; attempt++) {
            try {
                pickFrom(ticket.getDepartStation());
                sleepSafe(600);
                pickArrive(ticket.getArriveStation());
                sleepSafe(600);
                pickSeatType(ticket.getSeatType());
                sleepSafe(600);
                pickTicketAmount(ticket.getTicketAmount());
                sleepSafe(600);
                pickDate(ticket.getDate());
                System.out.println("Date Dropdown is displayed: " + DateDropdown().isDisplayed());
                BookTicketButton().click();
                if (isSuccessfulTicketPurchaseNotification()) {
                    isBooked = true;
                    test.pass("Book ticket successfully: " + ticket.getDate() + " - Depart: " + ticket.getDepartStation() + " - Arrive: " + ticket.getArriveStation());
                    test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "BookTicketSuccess"));
                    break;
                } else {
                    throw new Exception("Booking confirmation not found.");
                }
            } catch (Exception e) {
                System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < maxRetries) {
                    driver.navigate().refresh();
                    sleepSafe(2000);
                } else {
                    test.fail("Failed to book ticket after " + maxRetries + " retries: " + ticket.getDate() + " - Depart: " + ticket.getDepartStation() + " - Arrive: " + ticket.getArriveStation());
                    test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "BookTicketError"));
                }
            }
        }
    }

    public boolean isSuccessfulTicketPurchaseNotification(){
        if(SuccessMessage() != null){return true;};
        return false;
    }
}