package tests;

import base.BaseSetup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.ConfigReader;
import utils.DriverManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected static final String SAUCEDEMO_URL = ConfigReader.getValue("url");

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        String headlessProp = System.getProperty("headless", "false");
        boolean headless = Boolean.parseBoolean(headlessProp);
        WebDriver driver = DriverManager.createInstance(browser, headless);
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void closeDriver(){DriverManager.quitDriver();
    }

    public void captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            System.err.println("Driver is null, cannot capture screenshot for " + testName);
            return;
        }

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File destDir = new File("target/screenshots");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir, testName + "_" + timestamp + ".png");
        try {
            FileHandler.copy(srcFile, destFile);
            System.out.println("Screenshot captured for failed test: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
