package tests;

import data_provider.DPDeleteContact;
import dto.ContactDtoLombok;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import manager.ApplicationManager;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.RetryAnalyzer;
import utils.TestNGListener;

import java.io.IOException;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class EditContactsTests extends ApplicationManager implements BaseApi {
    TokenDto token;
    ContactPage contactPage;
    LoginPage loginPage;

    private boolean useAPI = true;

    @BeforeMethod(alwaysRun = true)
    public void setupMethod() throws InterruptedException{
        if (useAPI) {
            loginAPI();
        } else {
            loginUI();
        }
    }

    public void loginUI() {
        logger.info("start method --> login");
        new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
    }

    public void loginAPI()  throws InterruptedException{
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println("*********** token ************" + token.getToken());
            new HomePage(getDriver());
            ((JavascriptExecutor) getDriver()).executeScript("window.localStorage.setItem('token', '" + token.getToken() + "');");
        // Thread.sleep(3000);
            getDriver().navigate().refresh();
        //    Thread.sleep(3000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void editContactTest(UserDto user) {
        if (!useAPI) {
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();}
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                // If you replace 10 with 9 in the phone, then operation of  retryAnalyzer is visible.
                // There will be 8 tests instead of 2
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
    public void editContactNegativeTestNameEmpty(UserDto user) {
        if (!useAPI) {
            loginPage.typeLoginForm(user)
                    .clickBtnLoginPositive();}
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditName()
                .clickBtnEditSave()
                .isRightWindowName())
        ;

    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void editContactNegativeTestLastNameEmpty(UserDto user) {
        if (!useAPI) {
            loginPage.typeLoginForm(user)
                    .clickBtnLoginPositive();}
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .editContact()
                .fillEditLastName()
                .clickBtnEditSave()
                .isRightWindowName())
        ;

    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void editContactNegativeTestEmailEmpty(UserDto user) {
        if (!useAPI) {
            loginPage.typeLoginForm(user)
                    .clickBtnLoginPositive();}
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
    public void editContactNegativeTestEmailTwoAt(UserDto user) {
        if (!useAPI) {
            loginPage.typeLoginForm(user)
                    .clickBtnLoginPositive();}
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
