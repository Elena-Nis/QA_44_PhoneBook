package pages;

import dto.ContactDtoLombok;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddPage extends BasePage {
    public AddPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;
    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;
    @FindBy(xpath = "//button/b")
    WebElement btnSaveContact;

    public AddPage fillContactForm(ContactDtoLombok contact) {
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
        return this;
    }
    public ContactPage clickBtnSaveContactPositive(){
        btnSaveContact.click();
        return new ContactPage(driver);
    }

    String expectedUrl="";
    String actualURL="";
    public AddPage clickBtnSaveContactNegative() {

        expectedUrl = driver.getCurrentUrl();
        btnSaveContact.click();

        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            pause(3);
            alert.accept();
        } catch (TimeoutException e) {
            System.out.println("Exception");
        }

        actualURL = driver.getCurrentUrl();
        return this;
    }

    public boolean isURLUnchanged (){
        return expectedUrl.equals(actualURL);
    }

    public boolean isFormPresent() {
       return btnSaveContact.isDisplayed() &&
        inputName.isDisplayed() &&
        inputEmail.isDisplayed() &&
        inputPhone.isDisplayed();

    }




}
