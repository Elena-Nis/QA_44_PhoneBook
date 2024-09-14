package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

import java.util.Random;

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        String email = "asd" + i + "@qwe.com";
        String password = "Privet$12345789" + i;
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(email, password)
                .clickBtnRegistrationPositive()
                .isElementContactPresent());

    }

    @Test
    public void registrationNegativeTestEmail(){
                Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("Oly.com", "Oly12$123457890")
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage());

    }

    @Test
    public void registrationNegativeTestPassword(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("Oly@qwe.com", "Oly12123457890")
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage());

    }

    @Test
    public void registrationNegativeTestEmailPassword(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("Oly.com", "Oly12123457890")
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage());

    }

    @Test
    public void registrationNegativeUserRegistered(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("asd@qwe.com", "Privet$123457890")
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage());

    }
}

