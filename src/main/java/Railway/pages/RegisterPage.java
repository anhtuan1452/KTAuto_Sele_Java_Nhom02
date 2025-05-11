package Railway.pages;

import org.openqa.selenium.*;


public class RegisterPage extends GenetralPage {
    // Locators
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By confirmpasswordField = By.id("confirmPassword");
    private final By pidField = By.id("pid");
    private final By btnResgiter = By.xpath("//*[@id=\"RegisterForm\"]/fieldset/p/input");
    private final By messegerError = By.xpath("//div[@id=\"content\"]/p[contains(@class,'message error')]");
    private final By messeger = By.xpath("//div[@id=\"content\"]/p");
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
        return getElement(messeger);
    }
    protected WebElement getMessegerError() {return getElement(messegerError);}

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


    public Boolean MessageBlankPib() {
        try {
            WebElement label = getElement(messageBlankPid);
            WebElement pidInput = getElement(By.id("pid"));

            String errorMessage = label.getText().trim();
            System.out.println("Error message text: " + errorMessage);
            if (errorMessage.contains("Invalid ID length") &&
                    label.getRect().getX() > pidInput.getRect().getX()) {
                return true;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error elements not found: " + e.getMessage());
            return false;
        }
        return false;
    }



}