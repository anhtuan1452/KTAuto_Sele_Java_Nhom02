package Railway.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;


public class RegisterPage extends GenetralPage {
    // Locators
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By confirmpasswordField = By.id("confirmPassword");
    private final By pidField = By.id("pid");
    private final By btnResgiter = By.xpath("//*[@id=\"RegisterForm\"]/fieldset/p/input");
    private final By messageBlankPassword = By.xpath("//*[@id=\"RegisterForm\"]/fieldset/ol/li[2]/label[2]");
    private final By formErrorMessage = By.xpath("//div[@id=\"content\"]/p[2]");
    private final By messageBlankPid = By.xpath("/html/body/div[1]/div[2]/form/fieldset/ol/li[4]/label[2]");


    //Element
    protected WebElement EmailField(){
        return getElement(emailField);
    }
    protected WebElement PasswordField(){
        return getElement(passwordField);
    }
    protected WebElement ConfirmpasswordField(){
        return getElement(confirmpasswordField);
    }
    protected WebElement PidField(){
        return getElement(pidField);
    }
    protected WebElement BtnResgiter(){
        return getElement(btnResgiter);
    }
    protected WebElement Messeger(){
        return getElement(formErrorMessage);
    }
    protected WebElement getMessegerError() {return getElement(messageBlankPassword);}

    // method
    public RegisterPage(WebDriver driver){
        super(driver);
    }
    public String getCompeleteMessenger(){
        if(Messeger()== null){return " ";}
        return Messeger().getText();
    }
    public String getMessenger(){
        if(getMessegerError()== null){return " ";}
        return getMessegerError().getText();
    }
    public void registerAccount(String a, String pw, String rpw, String pid) {
        EmailField().sendKeys(a);
        PasswordField().sendKeys(pw);
        ConfirmpasswordField().sendKeys(rpw);
        PidField().sendKeys(pid);
        clickRegister();
        sleepSafe(600);
    }
    public String RegisterAccountSaveGmail() {
        String a = generateGmail();
        clickRegister();
        return a;
    }

    public void clickRegister(){
        WebElement registerButton = getElement(By.xpath("//*[@id='RegisterForm']/fieldset/p/input"));
        scrollElement(registerButton);
        registerButton.click();
    }
    // Kiểm tra thông báo lỗi trên form
    public void checkFormErrorMessageDisplayed(String expectedMessage, HomePage homePage, ExtentTest test) {
        WebElement formMessage = getElement(formErrorMessage);
        String errorMessage = formMessage.getText().trim();
        System.out.println("Form Error message text: " + errorMessage);
        if (errorMessage.contains(expectedMessage)) {
            test.log(Status.PASS, "Message \"" + expectedMessage + "\" appears above the form.");
        } else {
            test.fail("Message \"" + expectedMessage + "\" does not appear above the form.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC11"));
        }

    }

    // Kiểm tra thông báo lỗi PID
    public void checkMessageBlankPidDisplayed(String expectedMessage, HomePage homePage, ExtentTest test) {
        WebElement label = getElement(messageBlankPid);
        WebElement pidInput = getElement(By.id("pid"));
        String errorMessage = label.getText().trim();
        System.out.println("PID Error message text: " + errorMessage);
        if (errorMessage.contains(expectedMessage) &&
                label.getRect().getX() > pidInput.getRect().getX()) {
        } else{
            test.fail("Error message \"Invalid PID length\" does not appear next to PID field.");
            test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC11"));
        }
    }

    // Kiểm tra thông báo lỗi Password
    public void checkMessageBlankPasswordDisplayed(String expectedMessage, HomePage homePage, ExtentTest test) {
            WebElement label = getElement(messageBlankPassword);
            WebElement passwordInput = getElement(By.id("password"));
            String errorMessage = label.getText().trim();
            System.out.println("Password Error message text: " + errorMessage);
            if (errorMessage.contains(expectedMessage) &&
                    label.getRect().getX() > passwordInput.getRect().getX()) {

            } else {
                test.fail("Error message \"Invalid password length\" does not appear next to password field.");
                test.addScreenCaptureFromPath(homePage.takeScreenshot(driver, "TC11"));
            }
    }







}