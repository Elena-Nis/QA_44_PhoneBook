package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListener.class)

public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!")
                .clickBtnLoginPositive()
                .isElementContactPresent();
        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!----")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

    @Test
    public void loginNegativeTest_wrongPassword_Enum() {
        UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!----");
        new HomePage(getDriver());
              LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
                loginPage.typeLoginForm(user)
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage()
        ;
    }


    @Test
    public void loginNegativeTest_wrongEmailUnregUser() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_wrong@mail.com", "Qwerty123!")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

    @Test
    public void loginNegativeTest_wrongEmailUnregUser_Enum() {
        UserDto user = new UserDto("qa_wrong@mail.com", "Qwerty123!");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user)
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage()
        ;
    }

    @Test
    public void loginNegativeTest_wrongEmailWOAt() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_wrongmail.com", "Qwerty123!")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

    @Test
    public void loginNegativeTest_wrongEmailWOAt_Enum() {
        UserDto user = new UserDto(generateString(5), "Qwerty123!");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user)
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage()
        ;
    }
}