package pages;

import dto.UserDto;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }
    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;
    @FindBy(xpath = "//button[@name='login']")
    WebElement btnLoginSubmit;   //qa_mail@mail.com   Qwerty123!
    @FindBy(xpath = "//button[@name='registration']")
    WebElement btnRegistration;
    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMessageLogin;

    public LoginPage typeLoginForm(String email, String password){
        //inputEmail.clear();
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        return this;
    }
    public LoginPage typeLoginForm(UserDto user){
        //inputEmail.clear();
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
        return this;
    }

    public ContactPage clickBtnLoginPositive(){
        btnLoginSubmit.click();
        return new ContactPage(driver);
    }

    public ContactPage clickBtnRegistrationPositive(){
        btnRegistration.click();
        return  new ContactPage(driver);
    }

    public LoginPage clickBtnLoginNegative() {
        btnLoginSubmit.click();
        return this;
    }
    public LoginPage closeAlert() {
        pause(3);
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.alertIsPresent());
        System.out.println(alert.getText());
        alert.accept();
        return new LoginPage(driver);
    }
    public boolean isTextInElementPresent_errorMessage(){
        return isElementPresent(errorMessageLogin, "Login Failed with code 401");
    }
    public boolean isTextInElementPresent_errorMessage(String text){
        return isElementPresent(errorMessageLogin, text);
    }

    public LoginPage clickBtnRegistrationNegative() {
        btnRegistration.click();
        return this;
    }
}