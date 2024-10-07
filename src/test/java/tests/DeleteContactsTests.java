package tests;

import data_provider.DPDeleteContact;
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

@Listeners(TestNGListener.class)

public class DeleteContactsTests extends ApplicationManager {

    ContactPage contactPage;
    LoginPage loginPage;

    @BeforeMethod
    public void login() {
        logger.info("start method --> login");
        new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
    }

    @Test(dataProvider = "addNewUserDP", dataProviderClass = DPDeleteContact.class)
    public void removeContactTest(UserDto user) {
        logger.info("start method --> removeContactTest" + " with data: " + "Email: " + user.getEmail()
                + " Password: " + user.getPassword());
        loginPage.typeLoginForm(user)
                .clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Assert.assertTrue(contactPage.selectedContact()
                .removeContact()
                .isContactDelete())
        ;
    }
}
