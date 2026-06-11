package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.util.Locale;

public class BaseSetup {
    private static WebDriver driver;
    public static WebDriver getDriver() {
        return driver;
    }

    //Khởi tạp trình duyệt dựa trên config
    public static WebDriver setupDriver() {
        String browser = ConfigReader.getValue("browser");
        String headless = ConfigReader.getValue("headless");

        // Initialize driver based on browser + headless flag
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless.equalsIgnoreCase("true")) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);

        } else if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (headless.equalsIgnoreCase("true")) {
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        return driver;
    }

}
