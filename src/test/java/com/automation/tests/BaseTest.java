package com.automation.tests;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ScreenshotUtil;

public class BaseTest {
    
    protected WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        ScreenshotUtil.resetCounter(); // Reset counter for each test
        
        String browser = ConfigReader.getProperty("browser");
        DriverManager.initDriver(browser);
        driver = DriverManager.getDriver();
        
        String implicitWait = ConfigReader.getProperty("implicit.wait");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(implicitWait)));
        
        String url = ConfigReader.getProperty("url");
        driver.get(url);
        
        ScreenshotUtil.captureScreenshot(driver, "InitialPageLoad");
    }
    
    @AfterMethod
    public void tearDown() {
        ScreenshotUtil.captureScreenshot(driver, "TestCompleted");
        DriverManager.quitDriver();
    }
}