package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.HeaderMenuItem;

import java.time.Duration;

public class BasePage {

    static WebDriver driver;

    public static void setDriver(WebDriver wd){
        driver = wd;
    }
    public void pause(int time){
        try {
            Thread.sleep(time*1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isElementPresent(WebElement element, String text){
        return element.getText().contains(text);
    }
    public static <T extends BasePage> T clickButtonsOnHeader(HeaderMenuItem headerMenuItem){
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(headerMenuItem.getLocator())));
            element.click();
        }catch (TimeoutException exception){
            exception.printStackTrace();
            System.out.println("created exception");
        }
        switch (headerMenuItem){
            case LOGIN -> {
                return (T) new LoginPage(driver);
            }
            case HOME -> {
                return (T) new HomePage(driver);
            }
            case ABOUT -> {
                return (T) new AboutPage(driver);
            }
            case ADD ->
            {
                return (T) new AddPage(driver);
            }
            case CONTACTS ->
            {
                return (T) new ContactPage(driver);
            }
            default -> throw new IllegalArgumentException("Invalid parametr header menu");
        }

    }
}