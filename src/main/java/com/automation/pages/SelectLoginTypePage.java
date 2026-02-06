package com.automation.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;

public class SelectLoginTypePage extends BasePage {

	public SelectLoginTypePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[contains(.,'Back Office Login')]")
	private WebElement backOfficeLoginBtn;

	@FindBy(xpath = "//button[contains(.,'Hotel Login')]")
	private WebElement hotelLoginBtn;

	@FindBy(xpath = "//input[@placeholder=\"Search by name, code, or city...\"]")
	private WebElement hotelSearchInput;

	@FindBy(xpath = "//div[@class=\"space-y-2 max-h-96 overflow-y-auto\"]")
	private List<WebElement> hotelSearchResult;

	@FindBy(xpath = "//span[contains(text(),'Logout')]")
	private WebElement logOutBtn;

	@FindBy(xpath = "//button[contains(.,\"logout\")]")
	private WebElement logOutBtn2;

	@FindBy(xpath = "//button[@data-slot='hover-card-trigger']")
	private WebElement profileBtn;

	@FindBy(xpath = "//p[normalize-space()='No hotels found matching your search']")
	private WebElement noResultsFoundMessage;

	public void clickBackOfficeLogin() {
		click(backOfficeLoginBtn);
	}

	public void clickHotelLogin() {
		click(hotelLoginBtn);
	}

	public void clickLogOutFromSelectLoginPage() {
		Log.info("Clicking Logout button on Select Login Type page");
		click(logOutBtn);

	}

	public void clickLogOutFromBackOfficeDashboardPage() {

		Log.info("Clicking on the Logout button on Back Office Dashboard page");
		click(logOutBtn2);

	}

	public void hoverOnProfile() {
		ExtentReportManager.getTest().info("Waiting for profile button to be visible");
		Log.info("Waiting for profile button to be visible");
		wait.until(ExpectedConditions.visibilityOf(profileBtn));

		Actions actions = new Actions(driver);
		ExtentReportManager.getTest().info("Hovering over profile button");
		Log.info("Hovering over profile button");
		actions.moveToElement(profileBtn).perform();

		ExtentReportManager.getTest().info("Clicking Logout button on Back Office Dashboard page");
		Log.info("Clicking Logout button on Back Office Dashboard page");
		click(logOutBtn2);

	}

	public boolean isOnSelectLoginTypePage() {
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl == null) {
			Log.error("Current URL is null; cannot verify if on Select Login Type page.");
			return false;
		}

		return currentUrl.contains("/login-choice");
	}

	public void searchAndSelectHotelUsingArrowKeys(String searchHotelName) {

		Log.info("Entering hotel keyword in search input: " + searchHotelName);
		hotelSearchInput.clear();
		hotelSearchInput.sendKeys(searchHotelName);

		Log.info("Waiting for search results to load");
		wait.until(ExpectedConditions.visibilityOfAllElements(hotelSearchResult));
		wait.withTimeout(Duration.ofSeconds(10));

		for (WebElement result : hotelSearchResult) {
			String resultText = result.getText().trim();

			Log.info("Found search result: " + resultText);

			if (resultText.equalsIgnoreCase(searchHotelName)) {
				Log.info("Search result matched. Clicking on: " + resultText);
				result.click();
				return;
			}
		}
		Log.info("Hotel selected successfully using keyboard navigation");
	}

	public void searchHotel(String keyword) {
		Log.info("Searching hotel with keyword: " + keyword);
		hotelSearchInput.sendKeys(keyword);
		wait.until(ExpectedConditions.visibilityOfAllElements(noResultsFoundMessage));
	}

	public void clearSearch() {
		Log.info("Clearing hotel search input");
		hotelSearchInput.clear();
	}

	public List<String> getSearchResultsText() {
		List<String> resultsText = new ArrayList<>();
		for (WebElement result : hotelSearchResult) {
			resultsText.add(result.getText());
		}
		return resultsText;
	}
}
