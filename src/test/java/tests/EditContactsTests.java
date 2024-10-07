package tests;

import data_provider.DPDeleteContact;
import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListener.class)

public class EditContactsTests extends ApplicationManager {
    ContactPage contactPage;
    LoginPage loginPage;

    @BeforeMethod
    public void login() {
        logger.info("start method --> login");
        new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void editContactTest(UserDto user) {
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(20))
                .build();
        logger.info("start method --> editContactTest" + " with data: " + contact.toString());
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditContactForm(contact)
                .clickBtnEditSave()
                .isEditContact(contact))
        ;

    }

    //NEGATIVE CONTACT EDITING TESTS
    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void  editContactNegativeTestNameEmpty (UserDto user) {
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditName()
                .clickBtnEditSave()
                .isRightWindowName())
        ;

    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void  editContactNegativeTestLastNameEmpty (UserDto user) {
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditLastName()
                .clickBtnEditSave()
                .isRightWindowName())
        ;

    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void  editContactNegativeTestEmailEmpty (UserDto user) {
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditEmailEmpty()
                .clickBtnEditSave()
                .isRightWindowEmailEmpty())
        ;
    //Bug: email cannot be empty
    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void  editContactNegativeTestEmailTwoAt (UserDto user) {
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditEmailTwoAt()
                .clickBtnEditSave()
                .isRightWindowEmailTwoAt())
        ;
//Bug is possible if mail is initially empty
    }

}
