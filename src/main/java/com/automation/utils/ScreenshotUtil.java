package com.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
    
    private static int screenshotCounter = 1;
    
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = String.format("	%03d_%s_%s.png", screenshotCounter++, screenshotName, timestamp);
        
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/screenshots/" + fileName;
        File finalDestination = new File(destination);
        
        try {
            FileUtils.copyFile(source, finalDestination);
            System.out.println("Screenshot saved: " + fileName);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
        
        return destination;
    }
    
    public static void resetCounter() {
        screenshotCounter = 1;
    }

	public static void setTestName(String testName) {
		// TODO Auto-generated method stub
		
	}
}