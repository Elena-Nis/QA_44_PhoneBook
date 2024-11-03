package restassered;

import data_provider.DPDeleteContact;
import dto.ContactDtoLombok;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import manager.ApplicationManager;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import java.io.IOException;

import static interfaces.BaseApi.BASE_URL;
import static io.restassured.RestAssured.given;
import static pages.BasePage.clickButtonsOnHeader;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class EditContactsTests extends ApplicationManager implements BaseApi {
    ContactPage contactPage;
    LoginPage loginPage;
    private boolean useAPI = true;
    UserDto user;

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
         user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        Response response = requestRegLogin(user,LOGIN_PATH);
        String token = response.jsonPath().getString("token");
            System.out.println("*********** token ************" + token);
            new HomePage(getDriver());
            ((JavascriptExecutor) getDriver()).executeScript("window.localStorage.setItem('token', '" + token + "');");
            getDriver().navigate().refresh();

    }

public Response requestRegLogin(UserDto user, String url) {
    return given()
            .body(user)
            .contentType(ContentType.JSON)
            .when()
            .post(BASE_URL+url)
            .thenReturn();
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
