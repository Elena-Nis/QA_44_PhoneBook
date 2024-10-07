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

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("my_qa_email5@mail.com", "Password12345!")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());
    }
    @Test
    public void registrationNegativeTest_wrongEmail(){
        String email = generateString(10);
        UserDto user = new UserDto(email, "Qwerty123!");
        new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage("Registration failed with code 400")
        ;
    }
    @Test
    public void registrationNegativeTest_wrongEmail_Enum() {
        UserDto user = new UserDto(generateString(5), "Qwerty123!");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user)
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage("Registration failed with code 400")
        ;
    }

}