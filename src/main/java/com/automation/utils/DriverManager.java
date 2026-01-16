package com.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private DriverManager() {
		// Private constructor to prevent instantiation
	}
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }
    
    public static void initDriver(String browserName) {
        WebDriver driverInstance = null;
        
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driverInstance = new ChromeDriver();
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driverInstance = new FirefoxDriver();
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driverInstance = new EdgeDriver();
                break;
                
            default:
                Log.info("Invalid browser name provided: " + browserName + ". Defaulting to Chrome.");
            	WebDriverManager.chromedriver().setup();
                driverInstance = new ChromeDriver();
        }
        
        driverInstance.manage().window().maximize();
        driverInstance.manage().deleteAllCookies();
        setDriver(driverInstance);
    }
    
    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}