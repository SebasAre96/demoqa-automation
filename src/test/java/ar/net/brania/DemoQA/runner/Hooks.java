package ar.net.brania.DemoQA.runner;

import ar.net.brania.DemoQA.config.ConfigReader;
import ar.net.brania.DemoQA.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Hooks {

    @Before
    public void setUp() {
        String browser = System.getProperty("browser",
                ConfigReader.getBrowser()).toLowerCase();

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless",
                        String.valueOf(ConfigReader.isHeadless()))
        );

        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) ffOptions.addArguments("--headless");
                ffOptions.addArguments("--width=1920", "--height=1080");
                driver = new FirefoxDriver(ffOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chOptions = new ChromeOptions();
                chOptions.addArguments("--start-maximized");
                chOptions.addArguments("--window-size=1920,1080");
                chOptions.addArguments("--disable-notifications");
                chOptions.addArguments("--disable-popup-blocking");
                // Opciones necesarias para Docker/Jenkins:
                chOptions.addArguments("--no-sandbox");
                chOptions.addArguments("--disable-dev-shm-usage");
                if (headless) chOptions.addArguments("--headless=new");
                driver = new ChromeDriver(chOptions);
                break;
        }

        // Guarda el driver en ThreadLocal — thread-safe
        DriverManager.setDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png",
                    "Screenshot - " + scenario.getName());
        }

        // Cierra y limpia el ThreadLocal
        DriverManager.quitDriver();
    }
}