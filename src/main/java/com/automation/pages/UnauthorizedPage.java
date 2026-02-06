package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import com.automation.utils.Log;

public class UnauthorizedPage extends BasePage {

	@FindBy(xpath = "//h1[contains(text(), 'Access Denied')]")
	private WebElement unauthorizedHeading;

	@FindBy(xpath = "//p[contains(text(), 'access')] | //div[contains(@class, 'error-message')] | //p[contains(text(), 'permission')]")
	private WebElement errorMessage;

	@FindBy(xpath = "//button[contains(text(), 'Back')] | //a[contains(text(), 'Go Back')]")
	private WebElement backButton;

	public UnauthorizedPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Check if unauthorized page is displayed
	 */
	public boolean isUnauthorizedPageDisplayed() {
		Log.info("Checking if unauthorized page is displayed");
		try {
			Log.info("Waiting for unauthorized heading to be visible");
			wait.until(ExpectedConditions.visibilityOf(unauthorizedHeading));

			String headingText = unauthorizedHeading.getText();
			Log.info("Unauthorized heading text: '" + headingText + "'");

			Assert.assertNotNull(headingText, "Unauthorized heading is null");
			Assert.assertFalse(headingText.trim().isEmpty(), "Unauthorized heading is empty");

			boolean isDisplayed = unauthorizedHeading.isDisplayed();
			Log.info("Unauthorized heading displayed: " + isDisplayed);

			Log.info("✓ Unauthorized page is displayed");
			return true;
		} catch (Exception e) {
			Log.error("✗ Unauthorized page not displayed: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Get error message text
	 */
	public String getErrorMessage() {
		Log.info("Fetching error message");
		try {
			wait.until(ExpectedConditions.visibilityOf(errorMessage));
			Log.info("Error message element is visible");

			String message = errorMessage.getText();
			Log.info("Error message text: '" + message + "'");

			Assert.assertNotNull(message, "Error message is null");
			Assert.assertFalse(message.trim().isEmpty(), "Error message is empty");

			Log.info("Successfully retrieved error message");
			return message;
		} catch (Exception e) {
			Log.error("Failed to get error message: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Verify URL contains expected unauthorized indicator
	 */
	public boolean isUnauthorizedUrl() {
		Log.info("Checking if current URL indicates unauthorized access");
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl == null) {
			Log.error("Current URL is null, cannot verify authorization status");
			return false;
		}
		Log.info("Current URL: " + currentUrl);
		boolean isUnauthorized = currentUrl.contains("unauthorized");

		if (isUnauthorized) {
			Log.info("✓ URL indicates unauthorized access");
		} else {
			Log.info("✗ URL does NOT indicate unauthorized access");
		}

		return isUnauthorized;
	}

	/**
	 * Click back button if available
	 */
	public void clickBackButton() {
		Log.info("Attempting to click back button on unauthorized page");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(backButton));
			Log.info("Back button is clickable");

			// 1. Capture current URL and handle potential null
			String urlBefore = driver.getCurrentUrl();
			Log.info("Current URL before clicking back: " + urlBefore);
			if (urlBefore == null) {
				urlBefore = ""; // Default to empty string to avoid passing null to urlToBe
				Log.warn("Current URL was null before clicking back; using empty string for comparison.");
			}
			Log.info("Current URL before clicking back: " + urlBefore);

			backButton.click();
			Log.info("Clicked back button");

			// 2. Safely use the non-null urlBefore variable
			wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBefore)));
			String urlAfter = driver.getCurrentUrl();
			Log.info("URL after clicking back: " + urlAfter);

			Assert.assertNotEquals(urlAfter, urlBefore, "URL did not change after clicking back");
			Log.info("Successfully navigated back");

		} catch (Exception e) {
			Log.error("Failed to click back button: " + e.getMessage());
			throw e;
		}
	}
}