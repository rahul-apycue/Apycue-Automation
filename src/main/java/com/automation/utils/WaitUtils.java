package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class WaitUtils {
	WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Wait for page to fully load (document.readyState)
    public void waitForPageLoad() {
        Log.info("Waiting for page to load completely");
        wait.until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));
        Log.info("Page load completed");
    }

    // Wait for URL to change
    public void waitForUrlToChange(String oldUrl) {
        Log.info("Waiting for URL to change");
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
        Log.info("URL changed successfully");
    }

    // Wait until element is clickable
    public void waitForElementToBeClickable(WebElement element) {
        Log.info("Waiting for element to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Wait until element is visible
    public void waitForElementToBeVisible(WebElement element) {
        Log.info("Waiting for element to be visible");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Controlled UI wait (2–3 seconds)
    public void waitForUiStability(int seconds) {
        try {
            Log.info("Waiting for UI stability: " + seconds + " seconds");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
