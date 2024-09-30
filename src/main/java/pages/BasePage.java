package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.HeaderMenuItem;

import java.time.Duration;
import java.util.List;

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
    public boolean urlContains(String urlPart, int time){
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.urlContains(urlPart));
    }

    public void clickWait(WebElement element, int time){
        new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void clickWaitVisibility (WebElement element, int time){
        new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.visibilityOf(element)).click();
    }

    public void elementWaitVisibility (WebElement element, int time){
        new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void clickWait(By locator, int time){
        new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void elementsListWaitInDOM (By locator, int initialCount){
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(driver -> driver.findElements(locator).size() < initialCount);
    }
}