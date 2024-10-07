package tests;

import data_provider.DPAddContact;
import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)


public class AddContactsTests extends ApplicationManager {

  //  UserDto user = new UserDto("asd@qwe.com", "Privet$123457890");
    UserDto user = new UserDto("anton@mail.com", "Anton$123457890");
    AddPage addPage;

    @BeforeMethod
    public void login(){
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isLastPhoneEquals(contact.getPhone()))
        ;
    }

    @Test
    public void addNewContactNegativeTestNameEmpty(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(0))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                       .clickBtnSaveContactNegative()
                       .isURLUnchanged() &&
                        addPage.isFormPresent())
        ;
    }

    @Test
    public void addNewContactNegativeTestLastNameEmpty(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(0))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())
        ;
    }

    @Test
    public void addNewContactNegativeTestEmailEmpty(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(0))
                .address(generateString(20))
                .description(generateString(10))
                .build();
         Assert.assertTrue(
         addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isNameEquals(contact.getName()))
        ;
        //Bug: email cannot be empty according to the requirements
    }



    @Test
    public void addNewContactNegativeTestEmailTwoAt(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12) + "@")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
               addPage.isFormPresent());

        ;

    }

    @Test
    public void addNewContactNegativeTestEmailWithoutAt(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())

        ;
    }

    @Test
    public void addNewContactNegativeTestEmailNoCharBeforeAt(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(0))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent());

        ;

    }

    @Test
    public void addNewContactNegativeTestEmailNoCharAfterAt(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(5) + "@")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent());

        ;

    }

    @Test
    public void addNewContactNegativeTestEmailWithCyrLet(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12) + "Ф")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent());
        //Bug: email cannot be with Russian letters according to the requirements
        ;

    }

    @Test
    public void addNewContactNegativeTestEmailAlreadyExists(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("grom@net.com")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent());
        //Bug: email cannot be same as an already created one according to the requirements
        ;

    }

    @Test
    public void addNewContactNegativeAddressEmpty(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(0))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())
        ;
    }

    @Test
    public void addNewContactNegativePhoneEmpty(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(0))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())
        ;
    }

    @Test
    public void addNewContactNegativePhoneNotNum(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generateString(11))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())
        ;
    }

    @Test
    public void addNewContactNegativePhoneTooShort(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(9))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())
        ;
    }

    @Test
    public void addNewContactNegativePhoneTooLong(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(16))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged() &&
                addPage.isFormPresent())
        ;
    }
    @Test
    public void addNewContactNegativeTest_nameIsEmpty() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .urlContainsAdd())
        ;
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(4))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }


//Testing using Alexey's methods clickBtnSaveContactPositive, isAlertPresent
    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDP(ContactDtoLombok contact) {
        System.out.println("--> " + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }

    //**********************************************************************
    //**********************************************************************

   //WITH FILE Tests using my methods clickBtnSaveContactNegative, isURLUnchanged, isFormPresent
    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongDataDPFile(ContactDtoLombok contact) {
        System.out.println("--> " + contact);
                Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged()
                        &&
                        addPage.isFormPresent())

        ;
    }

// WITH FILE Tests using Alexey's methods clickBtnSaveContactPositive, isAlertPresent, urlContainsAdd
        @Test(dataProvider = "addNewContactDPFile", dataProviderClass = DPAddContact.class)
        public void addNewContactNegativeTest_wrongDataDPFile_AlertInContact(ContactDtoLombok contact) {
            System.out.println("--> " + contact);
            ContactPage contactPage =
            addPage.fillContactForm(contact)
                   .clickBtnSaveContactPositive();
            Assert.assertTrue(contactPage.isAlertPresent(3) || contactPage.urlContainsAdd());

    }

  // WITHOUT FILE Tests using Alexey's methods clickBtnSaveContactPositive, isAlertPresent, urlContainsAdd
    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongDataDP_AlertInContact(ContactDtoLombok contact) {
        System.out.println("--> " + contact);
        ContactPage contactPage =
                addPage.fillContactForm(contact)
                        .clickBtnSaveContactPositive();
        Assert.assertTrue(contactPage.isAlertPresent(3) || contactPage.urlContainsAdd());
    }


    // WITHOUT FILE Tests using my methods clickBtnSaveContactNegative, isURLUnchanged, isFormPresent
    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongDataDP(ContactDtoLombok contact) {
        System.out.println("--> " + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isURLUnchanged()
                &&
                addPage.isFormPresent())
        ;
    }



}