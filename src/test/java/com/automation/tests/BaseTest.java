package com.automation.tests;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;
import com.automation.utils.ScreenshotUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class BaseTest {
    
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    
    @BeforeMethod
    public void setupReport()
    {
    	extent = ExtentReportManager.getReportInstance();	
    }
    
    @AfterMethod
    public void tearDownReport()
    {
    	extent.flush();
    }
    
    @BeforeSuite
	public void resetScreenshotCounter() {
		ScreenshotUtil.resetCounter();
	}
    
    @BeforeMethod
    public void setUp() {
        Log.info("Setting up the test environment");
    	//ScreenshotUtil.resetCounter(); // Reset counter for each test
        
    	Log.info("Browser is opening");
        String browser = ConfigReader.getProperty("browser");
        DriverManager.initDriver(browser);
        driver = DriverManager.getDriver();
        
        String implicitWait = ConfigReader.getProperty("implicit.wait");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(implicitWait)));
    
        Log.info("Url is launching");
        String url = ConfigReader.getProperty("url");
        driver.get(url);
        
        ScreenshotUtil.captureScreenshot(driver, "InitialPageLoad");
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
    	
		if (result.getStatus() == ITestResult.FAILURE) {
			Log.error("Test Failed: " + result.getName());
			test.fail("Test Failed...   Check ScreenShot",
					MediaEntityBuilder.createScreenCaptureFromPath(
									ScreenshotUtil.captureScreenshot(driver, "TestFailure_" + result.getName()))
							.build());
			
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			Log.info("Test Passed: " + result.getName());
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			Log.warn("Test Skipped: " + result.getName());
		}
		
		if (driver != null) {
	        Log.info("Browser is closing");
	        ScreenshotUtil.captureScreenshot(driver, "TestCompleted");
	        DriverManager.quitDriver();
	    } else {
	        Log.warn("WebDriver instance was null during teardown.");
	    }

    }
}