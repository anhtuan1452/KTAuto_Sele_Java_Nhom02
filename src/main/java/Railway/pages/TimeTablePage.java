package Railway.pages;

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
        String lowerField = fieldName.toLowerCase();

        if (lowerField.equals("depart station")) {
            return 1;
        } else if (lowerField.equals("arrive station")) {
            return 2;
        } else if (lowerField.equals("seat type")) {
            return 3;
        } else if (lowerField.equals("expired date")) {
            return 4;
        } else if (lowerField.equals("amount")) {
            return 7;
        }
        throw new IllegalArgumentException("Invalid field name: " + fieldName);
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
