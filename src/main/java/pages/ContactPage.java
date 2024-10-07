package pages;

import dto.ContactDtoLombok;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ContactPage extends BasePage {
    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    int numberContactsBeforeEditDeletion = 0;
    int numberContactsAfterDeletion = 0;

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContact;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h3")
    WebElement lastPhoneInList;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h2")
    WebElement nameInList;
    @FindBy(xpath = "//button[text()='Remove']")
    WebElement removeContact;
    @FindBy(xpath = "//button[text()='Edit']")
    WebElement editContact;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']")
    List<WebElement> contactDivs;

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement editName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement editLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement editPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement editEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement editAddress;
    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnEditSave;
    @FindBy(xpath = "//div[@class='contact-page_rightdiv__1NgZC']/div")
    WebElement actualContact;

    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']//h2")
    WebElement rightWindowContact;
//   @FindBy(xpath ="//img[@alt='media']/following-sibling::text()[2]")
//   WebElement rightWindowEmail;
   @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
   WebElement rightWindowEmail;

    public boolean isElementContactPresent() {
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone) {
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

    public boolean urlContainsAdd() {
        return urlContains("add", 20);
    }

    public ContactPage removeContact() {
        clickWaitVisibility(removeContact, 5);
        elementsListWaitInDOM(By.xpath("//div[@class='contact-item_card__2SOIM']"), numberContactsBeforeEditDeletion);
        numberContactsAfterDeletion = contactDivs.size();
        System.out.println("******************** Number contacts after deletion: " + numberContactsAfterDeletion);
        return this;
    }

    public ContactPage selectedContact() {
        numberContactsBeforeEditDeletion = contactDivs.size();
        System.out.println("********************* Number contacts before EditDeletion: " + numberContactsBeforeEditDeletion);
        Random random = new Random();
        int randomIndex = random.nextInt(0, numberContactsBeforeEditDeletion);
        System.out.println("********************* randomIndex = " + randomIndex);
        WebElement selectedContact = driver.findElement(By.xpath("//div[@class='contact-page_leftdiv__yhyke']" +
                "/div/div[" + randomIndex + "]"));
        WebElement contactContainer = driver.findElement(By.xpath("//div[@class='contact-page_leftdiv__yhyke']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(contactContainer).perform();
        actions.moveToElement(selectedContact).perform();
        clickWait(selectedContact, 5);
        return this;
    }

    public boolean isContactDelete() {
        return ((numberContactsBeforeEditDeletion - numberContactsAfterDeletion) == 1);
    }

    public ContactPage editContact() {
        clickWaitVisibility(editContact, 5);
        return this;
    }

    public ContactPage fillEditContactForm(ContactDtoLombok contact) {
        elementWaitVisibility(btnEditSave, 5);
        editName.clear();
        editName.sendKeys(contact.getName());
        editLastName.clear();
        editLastName.sendKeys(contact.getLastName());
        editPhone.clear();
        editPhone.sendKeys(contact.getPhone());
        editEmail.clear();
        editEmail.sendKeys(contact.getEmail());
        editAddress.clear();
        editAddress.sendKeys(contact.getAddress());
        //pause(3);
        return this;
    }

    public ContactPage clickBtnEditSave() {
        clickWait(btnEditSave, 5);
        return this;
    }

    public boolean isEditContact(ContactDtoLombok contact) {
        pause(5);
        String expectedFullName = contact.getName() + " " + contact.getLastName();
        String fullText = actualContact.getText();
        String[] splitText = fullText.split("\n");
        return (expectedFullName.equals(splitText[0]) &&
                contact.getPhone().equals(splitText[1]) &&
                contact.getEmail().equals(splitText[2]) &&
                contact.getAddress().equals(splitText[3])
        );
    }

    public ContactPage fillEditName() {
        elementWaitVisibility(btnEditSave, 5);
        editName.clear();
        pause(3);
        return this;
    }

    public ContactPage fillEditLastName() {
        elementWaitVisibility(btnEditSave, 5);
        editLastName.clear();
        pause(3);
        return this;
    }

    public boolean isRightWindowName() {
        String fullText = rightWindowContact.getText();
        String[] splitText = fullText.split(" ");
        return !splitText[1].equals("");
    }


    public ContactPage fillEditEmailEmpty() {
        elementWaitVisibility(btnEditSave, 5);
        editEmail.clear();
        editEmail.sendKeys(" ");
        editEmail.sendKeys(Keys.BACK_SPACE);
        pause(3);
        return this;
    }

    public boolean isRightWindowEmailEmpty() {
        boolean flag = false;
        try {
            driver.findElement(By.xpath("//img[@alt='media']/following-sibling::*[1][self::br]"));

        } catch (NoSuchElementException e) {
            flag = true;
        }
        return flag;

    }
String emailTwoAt = "a@sd@mail.com";
    public ContactPage fillEditEmailTwoAt() {
        elementWaitVisibility(btnEditSave, 5);
        editEmail.clear();
        editEmail.sendKeys(emailTwoAt);
        pause(3);
        return this;
    }

    public boolean isRightWindowEmailTwoAt() {
        String fullText =rightWindowEmail.getText();
        String[] splitText = fullText.split("\n");
        boolean flag = false;
        if(splitText[2]!="") {
          if (!emailTwoAt.equals(splitText[2])) {
                flag = true;
            }
        }
        return flag;
    }
}