package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.HeaderMenuItem;

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
        WebElement element  = driver.findElement(By.xpath(headerMenuItem.getLocator()));
        element.click();
        switch (headerMenuItem){
            case HOME:
                return (T) new HomePage(driver);
//            case ABOUT:
//                return (T) new AboutPage(driver);
            case LOGIN:
                return (T) new LoginPage(driver);

            default:
                throw new IllegalArgumentException("invalid parametr headerMenuItem");
        }
    }
}