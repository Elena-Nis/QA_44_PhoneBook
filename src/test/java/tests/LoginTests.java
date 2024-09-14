package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("asd@qwe.com", "Privet$123457890")
                .clickBtnLoginPositive()
                .isElementContactPresent();
        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("asd@qwe.com", "Privet123457890")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("asd1@qwe.com", "Privet$123457890")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }


}