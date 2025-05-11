package Railway.pages;

import Railway.dataObjects.Enum.Enum;
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

    public Boolean CheckBookTicket(String From, String Arrive) {
        String xpath = String.format(LinkBockTicket, From, Arrive);
        System.out.println("Generated XPath: " + xpath);

        try {
            WebElement linkBookticket = getElement(By.xpath(xpath));
            scrollElement(linkBookticket);
            linkBookticket.click();
        } catch (Exception e) {
            System.out.println("Không tìm thấy phần tử hoặc không thể click được.");
            e.printStackTrace();
            return false;
        }
        scrollElement(getBtnBookTicket());
        getBtnBookTicket().click();
        return true;
    }
    private int getColumnIndex(String fieldName) {
        try{
            Railway.dataObjects.Enum.Enum.TableSuccessfulBooking field = Enum.TableSuccessfulBooking.fromLabel(fieldName);
            return field.getColumnIndex();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid field name: "+ fieldName);
        }
    }
    public Boolean checkInfor(String fieldName, String check){
        int index = getColumnIndex(fieldName);
        String xpath = String.format(LinkTableCheck, index,check);
        System.out.println("Generated XPath: " + xpath);
        WebElement b = getElement(By.xpath(xpath));
        return b != null;
    }


//test case
}
