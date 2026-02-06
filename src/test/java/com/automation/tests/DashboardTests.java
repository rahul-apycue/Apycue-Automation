package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.*;
import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;
import java.util.List;

public class DashboardTests extends BaseTest {

	@Test(priority = 1) // TC_DB_001 Verify that a Back Office Admin can view all sidebar menu items.
	public void TC_DB_001_verifyDashboardAccessAdmin() {
		Log.info("===== TC_DB_001 Started: Dashboard Access (Admin) =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());
		DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login as Admin");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Step 2: Click on 'Back Office Login' button");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Verify: Admin can view all sidebar menu items");
		Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");

		int menuCount = dashboardPage.getSidebarMenuCount();
		List<String> menuNames = dashboardPage.getMenuItemNames();

		ExtentReportManager.getTest().info("Total sidebar menus visible: " + menuCount);
		ExtentReportManager.getTest().info("Menu items: " + menuNames);

		Assert.assertTrue(menuCount == 8, "Sidebar menus are not expected for the Admin");

		ExtentReportManager.getTest().info("Admin can view all sidebar menu items successfully");
		Log.info("===== TC_DB_001 Completed Successfully =====");
	}

	@Test(priority = 2) // TC_DB_002 Verify that a non-admin user only sees menus they have privilege
						// for.
	public void TC_DB_002_verifyDashboardAccessRestrictedUser() {
		Log.info("===== TC_DB_002 Started: Dashboard Access (Restricted User) =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());
		DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login with restricted user (View Hotel only)");
		loginPage.login(ConfigReader.getProperty("restricted.user.email"),
				ConfigReader.getProperty("restricted.user.password"));

		ExtentReportManager.getTest().info("Step 2: Click on 'Back Office Login' button");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Verify: User only sees menus they have privilege for");
		Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");

		List<String> menuNames = dashboardPage.getMenuItemNames();
		ExtentReportManager.getTest().info("Restricted user menu items: " + menuNames);

		// Verify restricted menu is visible
		boolean isMenuVisible = dashboardPage.isMenuItemVisible("View Hotel");
		Log.info("isMenuVisible of the privilege: " + isMenuVisible);
		ExtentReportManager.getTest().info("isMenuVisible of the privilege: " + isMenuVisible);
		Assert.assertTrue(isMenuVisible, "View Hotel menu is visible for restricted user");

		// Verify admin-only menus are NOT visible
		isMenuVisible = dashboardPage.isMenuItemVisible("Manage Reseller");
		Log.info("isMenuVisible of the privilege: " + isMenuVisible);
		ExtentReportManager.getTest().info("isMenuVisible of the privilege: " + isMenuVisible);
		Assert.assertFalse(isMenuVisible, "Manage Reseller menu should not be visible for restricted user");

		ExtentReportManager.getTest().info("Restricted user sees only permitted menus");
		Log.info("===== TC_DB_002 Completed Successfully =====");
	}

	@Test(priority = 3) // TC_DB_003 Verify that clicking a menu item redirects to the correct page.
	public void TC_DB_003_verifySidebarMenuRedirection() {
		Log.info("===== TC_DB_003 Started: Sidebar Menu Redirection =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());
		DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login as Admin");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Step 2: Click on 'Back Office Login' button");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Step 3: Click on sidebar tabs one by one");
		dashboardPage.clickingOnAllSidebarMenus();

		ExtentReportManager.getTest().info("Verify: All menu redirections completed successfully");
		Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/backoffice"), "Not on backoffice page");

		ExtentReportManager.getTest().info("All sidebar menu redirections verified successfully");
		Log.info("===== TC_DB_003 Completed Successfully =====");
	}

	@Test(priority = 4) // TC_DB_004 Verify that a user cannot access a restricted page via direct URL.
	public void TC_DB_004_verifyUnauthorizedPageAccess() {
		Log.info("===== TC_DB_004 Started: Unauthorized Page Access =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());
		UnauthorizedPage unauthorizedPage = new UnauthorizedPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login as user without Manage Reseller privilege");
		loginPage.login(ConfigReader.getProperty("restricted.user.email"),
				ConfigReader.getProperty("restricted.user.password"));

		ExtentReportManager.getTest().info("Step 2: Click on 'Back Office Login' button");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Step 3: enter Manage Reseller URL directly in address bar");
		DriverManager.getDriver().get(ConfigReader.getProperty("restricted_url"));

		ExtentReportManager.getTest().info("Verify: User cannot access restricted page");
		Assert.assertTrue(unauthorizedPage.isUnauthorizedPageDisplayed() && unauthorizedPage.isUnauthorizedUrl(),
				"Unauthorized access was not prevented");

		ExtentReportManager.getTest().pass("Unauthorized page access blocked successfully");
		Log.info("===== TC_DB_004 Completed Successfully =====");
	}

	@Test(priority = 5) // TC_DB_005 Concurrent Tab Logout // Verify logout in one tab affects all other
						// open tabs.
	public void TC_DB_005_verifyConcurrentTabLogout() {
		Log.info("===== TC_DB_005 Started: Concurrent Tab Logout =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login as Admin");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Step 2: Click on 'Back Office Login' button");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Step 3: Open Dashboard in Tab 1 and Tab 2");
		String tab1 = DriverManager.getDriver().getWindowHandle();

		// Fix: Handle potential null from getCurrentUrl() before using it
		String dashboardUrl = DriverManager.getDriver().getCurrentUrl();

		if (dashboardUrl == null) {
			dashboardUrl = ConfigReader.getProperty("url");
			Log.warn("Dashboard URL was null, defaulting to base URL from config");
		}

		// Open new tab using JavascriptExecutor via DriverManager
		((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
				.executeScript("window.open('" + dashboardUrl + "', '_blank');");

		java.util.Set<String> allTabs = DriverManager.getDriver().getWindowHandles();
		String tab2 = allTabs.stream().filter(tab -> !tab.equals(tab1)).findFirst()
				.orElseThrow(() -> new RuntimeException("Failed to open second tab"));

		ExtentReportManager.getTest().info("Tab 1 Handle: " + tab1);
		ExtentReportManager.getTest().info("Tab 2 Handle: " + tab2);

		ExtentReportManager.getTest().info("Step 4: Logout in Tab 1");
		DriverManager.getDriver().switchTo().window(tab1);
		selectLoginTypePage.hoverOnProfile(); // Assumes this triggers logout logic

		ExtentReportManager.getTest().info("Step 5: Go to Tab 2 and Refresh");
		DriverManager.getDriver().switchTo().window(tab2);
		DriverManager.getDriver().navigate().refresh();

		ExtentReportManager.getTest().info("Verify: Tab 2 is automatically logged out");

		// Fix: Null-safe check for the final URL
		String finalUrl = DriverManager.getDriver().getCurrentUrl();
		Assert.assertNotNull(finalUrl, "Final URL should not be null after refresh");

		Assert.assertTrue(finalUrl.contains("/backoffice/login"),
				"Tab 2 should be redirected to login page after Tab 1 logs out");

		ExtentReportManager.getTest().pass("Concurrent tab logout verified successfully");
		Log.info("===== TC_DB_005 Completed Successfully =====");
	}

}