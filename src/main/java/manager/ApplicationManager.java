package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.WDListener;


public class ApplicationManager {
    private WebDriver driver;

    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "langChrome"})
    public void setUp(@Optional("chrome") String browser, @Optional("--lang=en") String langChrome) {
        //logger.info("Start testing ========================" );
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(langChrome);
            driver = new ChromeDriver();
            WebDriverListener webDriverListener = new WDListener();
            driver = new EventFiringDecorator<>(webDriverListener).decorate(driver);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addPreference("intl.accept_languages", "en");
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser value --> " + browser);
        }
        //new realization WDListener in Selenium 4
        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener).decorate(driver);

        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //logger.info("Stop testing ========================");
//        if(driver != null){
//            driver.quit();
//        }
    }
}
