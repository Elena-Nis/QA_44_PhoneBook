package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage extends BasePage{
    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContact;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h3")
    WebElement lastPhoneInList;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h2")
    WebElement nameInList;

    public boolean isElementContactPresent(){
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone){
        return lastPhoneInList.getText().equals(phone);
    }


    public boolean isNameEquals(String name) {
        return nameInList.getText().equals(name);
    }



    public boolean isAlertPresent(int time) {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean urlContainsAdd(){
        return urlContains("add", 20);
    }
}