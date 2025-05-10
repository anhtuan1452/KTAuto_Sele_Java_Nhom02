package Railway.pages;

import Railway.dataObjects.Enum.Enum;
import org.openqa.selenium.*;

public class BookTicketsPage extends GenetralPage {
    //Locators
    private final By pageHeader = By.xpath("//h1[contains(text(),'Book ticket')]");
    private final By dateDropdown = By.xpath("//select[@name='Date']");
    private final By departDropdown = By.xpath("//select[@name='DepartStation']");
    private final By arriveDropdown = By.xpath("//select[@name='ArriveStation']");
    private final By seatTypeDropdown = By.xpath("//select[@name='SeatType']");
    private final By ticketAmountDropdown = By.xpath("//select[@name='TicketAmount']");
    private final By bookTicketButton = By.xpath("//input[@value='Book ticket']");
    private final By successMessage = By.xpath("//h1[contains(.,'successfully!')]");
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
        String xpath = String.format("//tr[td[%s][contains(text(),'%s')]]", index,check);
        System.out.println("Generated XPath: " + xpath);
        WebElement b = getElement(By.xpath(xpath));
        scrollElement(b);
        return b != null;
    }
    public void bookTicket(String date, String depart, String arrive, String seatType, String amount) {
        try {
            pickDate(date);
            WebElement dateElement = getElement(dateDropdown);
            System.out.println("Date Dropdown is displayed: " + dateElement.isDisplayed());
            pickFrom(depart);
            pickArrive(arrive);
            pickSeatType(seatType);
            pickTicketAmount(amount);
            BookTicketButton().click();
        } catch (IllegalArgumentException e){
            System.out.println("No search element: "+ e);
        }

    }
    public void clickBookTicket(){
        getElement(bookTicketButton,true).click();
    }
    public boolean isSuccessfulTicketPurchaseNotification(){
        WebElement Mgs = getElement(successMessage);
        if(Mgs != null){return true;};
        return false;
    }
}