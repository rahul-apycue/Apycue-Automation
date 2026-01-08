package com.automation.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.automation.utils.ScreenshotUtil;

public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ScreenshotUtil.captureScreenshot(driver, "BeforeClick");
        element.click();
        ScreenshotUtil.captureScreenshot(driver, "AfterClick");
    }
    
    public void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ScreenshotUtil.captureScreenshot(driver, "BeforeEnteringText");
        element.clear();
        element.sendKeys(text);
        ScreenshotUtil.captureScreenshot(driver, "AfterEnteringText_" + text);
    }
    
    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ScreenshotUtil.captureScreenshot(driver, "GetText");
        return element.getText();
    }
    
    public boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            ScreenshotUtil.captureScreenshot(driver, "ElementCheck");
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void navigateToUrl(String url) {
        driver.get(url);
        ScreenshotUtil.captureScreenshot(driver, "PageLoaded_" + url.replaceAll("[^a-zA-Z0-9]", "_"));
    }
}