package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import com.automation.utils.Log;
import com.automation.utils.WaitUtils;

import java.util.List;
import java.util.ArrayList;

public class DashboardPage extends BasePage {
	private WaitUtils waitUtils;

	@FindBy(xpath = "//ul[@data-sidebar='menu']//a[@data-sidebar='menu-button']//span[contains(@class,'font-inter')]")
	private List<WebElement> sidebarMenuItems;

	@FindBy(xpath = "//ul[@data-sidebar='menu']//span")
	private List<WebElement> sidebarMenuLabels;

	@FindBy(xpath = "//div[@class='flex-col gap-2 h-[61px] m-0 p-0 flex items-center justify-center']")
	private WebElement sidebarToggleBtn;

	@FindBy(xpath = "//h1[@class=\"text-2xl font-bold text-foreground\"]")
	private WebElement welcomeMessage;

	@FindBy(xpath = "//div[@class='p-4 border-t border-sidebar-border mt-auto']")
	private WebElement backToLoginChoiceBtn;

	@FindBy(xpath = "//span[normalize-space()='Create Hotel']")
	private WebElement createHotelTab;

	@FindBy(xpath = "//span[normalize-space()='Manage Hotels']")
	private WebElement manageHotelTab;

	@FindBy(xpath = "//span[normalize-space()='White Label Partner']")
	private WebElement whitelLabelPartnerTab;

	@FindBy(xpath = "//span[normalize-space()='Manage Reseller']")
	private WebElement manageResellerTab;

	@FindBy(xpath = "//span[normalize-space()='View Hotel']")
	private WebElement viewHotelTab;

	@FindBy(xpath = "//span[normalize-space()='Manage Media']")
	private WebElement manageMediaTab;

	@FindBy(xpath = "//span[normalize-space()='View Status']")
	private WebElement viewStatusTab;

	@FindBy(xpath = "//div[@class='flex min-h-0 flex-1 flex-col gap-2 overflow-auto overscroll-none p-4']")
	private WebElement sidebar;

	public DashboardPage(WebDriver driver) {
		super(driver);
		this.waitUtils = new WaitUtils(driver);
	}

	/// Verify dashboard is loaded
	public boolean isDashboardLoaded() {
		Log.info("Verifying if dashboard is loaded");
		try {
			Log.info("Checking if URL contains '/backoffice/dashboard'");
			wait.until(ExpectedConditions.urlContains("/backoffice/dashboard"));
			String currentUrl = driver.getCurrentUrl();
			// Fix: Check for null before calling .contains()
			if (currentUrl == null) {
				Log.error("Current URL is null, dashboard verification failed");
				return false;
			}
			Log.info("Current URL: " + currentUrl);

			Assert.assertTrue(currentUrl.contains("/backoffice/dashboard"),
					"URL does not contain '/backoffice/dashboard'");

			Log.info("Checking if sidebar is visible");
			wait.until(ExpectedConditions.visibilityOf(sidebar));

			Assert.assertTrue(isDisplayed(sidebar), "Sidebar is not displayed");

			Log.info("✓ Dashboard loaded successfully");
			return true;
		} catch (Exception e) {
			Log.error("✗ Dashboard not loaded: " + e.getMessage());
			Log.error("Current URL: " + driver.getCurrentUrl());
			return false;
		}
	}

	// Get count of visible sidebar menu items
	public int getSidebarMenuCount() {
		Log.info("Getting sidebar menu count");
		List<WebElement> visibleMenus = getSidebarMenuItems();
		int count = visibleMenus.size();
		Log.info("Total sidebar menus found: " + count);
		Assert.assertTrue(count > 0, "No sidebar menu items found");
		return count;
	}

	// Get all visible sidebar menu items
	public List<WebElement> getSidebarMenuItems() {
		Log.info("Fetching all sidebar menu items");
		try {
			wait.until(ExpectedConditions.visibilityOfAllElements(sidebarMenuItems));
			Log.info("Successfully fetched " + sidebarMenuItems.size() + " sidebar menu items");
			Assert.assertFalse(sidebarMenuItems.isEmpty(), "Sidebar menu items list is empty");
			return sidebarMenuItems;
		} catch (Exception e) {
			Log.error("Failed to fetch sidebar menu items: " + e.getMessage());
			throw e;
		}
	}

	// Click all sidebar menus one by one and verify navigation
	public void clickingOnAllSidebarMenus() {

		Log.info("Getting all sidebar menu names list");
		List<String> menuNames = getMenuItemNames(); // Store the all menu names

		Log.info("Starting to click all sidebar menus sequentially");
		int totalMenus = menuNames.size(); // Count of Total number of menus

		Log.info("Total menus to be clicked: " + totalMenus);
		int successCount = 0;
		int failureCount = 0;
		List<String> failedMenus = new ArrayList<>();

		for (int i = 0; i < menuNames.size(); i++) {
			String menuName = menuNames.get(i);

			Log.info("===== Clicking Menu " + (i + 1) + "/" + totalMenus + ": '" + menuName + "' =====");

			try {
				String dashboardUrl = driver.getCurrentUrl();
				if (dashboardUrl == null) {
					throw new IllegalStateException("Dashboard URL is null; cannot proceed with menu clicks.");
				}
				Log.info("Dashboard URL before clicking: " + dashboardUrl);

				clickSidebarMenu(menuName);
				successCount++;
				Log.info("Successfully clicked menu " + (i + 1) + ": '" + menuName + "'");

				String currentUrl = driver.getCurrentUrl();
				// Fix 2: Guard against null before calling .contains()
				if (currentUrl == null) {
					throw new IllegalStateException("Current URL is null after clicking menu: " + menuName);
				}
				Log.info("Current URL after clicking: " + currentUrl);
				Assert.assertTrue(currentUrl.contains("/backoffice/"),
						"Not on backoffice page after clicking: " + menuName);
				Log.info("Navigation verified for menu: '" + menuName + "'");
				// Return to dashboard
				Log.info("Navigating back to dashboard");
				driver.navigate().back();
				waitUtils.waitForPageLoad();
				waitUtils.waitForUiStability(1);
				wait.until(ExpectedConditions.urlToBe(dashboardUrl));
				Log.info("Successfully returned to dashboard");

			} catch (Exception e) {
				failureCount++;
				failedMenus.add(menuName);
				Log.error("Failed to click menu '" + menuName + "': " + e.getMessage());
			}
		}

		Log.info("===== Sidebar Menu Click Summary =====");
		Log.info("Total Menus: " + totalMenus);
		Log.info("Successfully Clicked: " + successCount);
		Log.info("Failed: " + failureCount);

		if (failureCount > 0) {
			Log.warn("Failed Menus: " + failedMenus);
		}
		Assert.assertEquals(failureCount, 0, "Failed to click " + failureCount + " menu(s): " + failedMenus);
	}

	// Get list of menu item names
	public List<String> getMenuItemNames() {

		Log.info("Extracting menu item names from sidebar");
		List<String> menuNames = new ArrayList<>();
		for (WebElement label : sidebarMenuItems) {
			String name = label.getText().trim();
			if (!name.isEmpty()) {
				menuNames.add(name);
				Log.info("Menu: " + name);
			}
		}
		Log.info("Total menu items extracted: " + menuNames.size());
		Log.info("Menu items: " + menuNames);
		Assert.assertFalse(menuNames.isEmpty(), "No menu names extracted from sidebar");
		return menuNames;
	}

	// Verify if menu item exists
	public boolean isMenuItemVisible(String menuName) {
		Log.info("Checking if menu '" + menuName + "' is visible");
		try {
			List<String> menuNames = getMenuItemNames();
			boolean exists = menuNames.stream().anyMatch(name -> name.equalsIgnoreCase(menuName));

			if (exists) {
				Log.info("✓ Menu '" + menuName + "' is visible in sidebar");
			} else {
				Log.info("✗ Menu '" + menuName + "' is NOT visible in sidebar");
			}

			return exists;
		} catch (Exception e) {
			Log.error("Error checking menu visibility for '" + menuName + "': " + e.getMessage());
			return false;
		}
	}

	// Click on a sidebar menu by text
	public void clickSidebarMenu(String menuName) {

		Log.info("Clicking sidebar menu: " + menuName);

		for (WebElement label : sidebarMenuItems) {

			String text = label.getText().trim();

			if (text.equalsIgnoreCase(menuName)) {

				WebElement menuButton = label.findElement(
						By.xpath("//ul[@data-sidebar='menu']//a[.//span[normalize-space()='" + text + "']]"));
				wait.until(ExpectedConditions.elementToBeClickable(menuButton));

				String beforeUrl = driver.getCurrentUrl();
				if (beforeUrl == null) {
					beforeUrl = ""; // Ensure a non-null string is used
					Log.warn("Initial URL was null; using empty string for navigation check.");
				}
				waitUtils.waitForElementToBeClickable(label);
				menuButton.click();
				Log.info("Clicked on menu: " + menuName);

				waitUtils.waitForUrlToChange(beforeUrl);
				waitUtils.waitForPageLoad();
				waitUtils.waitForUiStability(2);
				wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(beforeUrl)));

				Log.info("Navigation successful for menu: " + menuName);
				return;
			}
		}
		Log.error("Menu not found: " + menuName);
		Assert.fail("Menu item not found: " + menuName);
	}

}