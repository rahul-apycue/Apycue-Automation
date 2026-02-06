package com.automation.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.pages.LoginPage;
import com.automation.pages.SelectLoginTypePage;
import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;

public class SelectLoginTypeTests extends BaseTest {

	@Test(priority = 1) // TC_PL_001 Redirection to Back Office Dashboard ( Admin )
	public void TC_PL_001_verifyBackOfficeAdminDashboardAccess() {

		Log.info("===== TC_PL_001 Started : Verify Back Office Admin Dashboard Access =====");
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login as Back Office Admin");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 2: Verify Select Login Type page is displayed");
		Assert.assertTrue(selectLoginTypePage.isOnSelectLoginTypePage(), "Select Login Type page is not displayed");

		ExtentReportManager.getTest().info("Step 3: Click on Back Office Login");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Step 4: Verify redirection to Back Office Dashboard");
		Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/backoffice/dashboard"),
				"Admin is not redirected to Back Office Dashboard");

		ExtentReportManager.getTest().info("Back Office Admin successfully accessed dashboard with full functionality");
		Log.info("===== TC_PL_001 Completed Successfully =====");
	}

	@Test(priority = 2) // TC_PL_002 Redirection to Hotel Search page ( Hotel Login ) TC_PL_003 Search
						// Functionality (Hotel Search)
	public void TC_PL_002_003_verifyHotelLoginRedirection() {

		Log.info("===== TC_PL_002 Started : Hotel Login Redirection =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login with valid Admin credentials");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Verifying the landing page URL after successful login");
		Log.info("Comparing expected URL with actual URL");

		// Add your assertion here based on successful login
		Assert.assertTrue(loginPage.verifyPageUrl().contains("/backoffice/login-choice"),
				"Login failed with valid credentials");

		ExtentReportManager.getTest().info("Step 2: Click on Hotel Login option");
		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());
		selectLoginTypePage.clickHotelLogin();

		ExtentReportManager.getTest().info("Step 3: Verify user is redirected to Hotel Search page");
		Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/backoffice/select-hotel"),
				"User is not redirected to Hotel Search page");

		ExtentReportManager.getTest().info("Searching and selecting hotel using keyboard");
		String searchHotelName = ConfigReader.getProperty("searchHotelName");
		selectLoginTypePage.searchAndSelectHotelUsingArrowKeys(searchHotelName);

		ExtentReportManager.getTest().info("Hotel Login successfully redirected to Hotel Search interface");
		Log.info("===== TC_PL_002 Completed Successfully =====");
	}

	@Test(priority = 3) // TC_PL_004 Logout Functionality Verify user can terminate session from the
						// choice page.
	public void TC_PL_004_verifyLogoutFromBackOffice() {
		Log.info("===== Test Started : Logout from Select Login Type Page =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Logging in with valid credentials to reach Select Login Type page");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Verifying the landing page URL after successful login");
		Log.info("Comparing expected URL with actual URL");

		// Add your assertion here based on successful login
		Assert.assertTrue(loginPage.verifyPageUrl().contains("/backoffice/login-choice"),
				"Login failed with valid credentials");

		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Clicking Logout button on Select Login Type page");
		selectLoginTypePage.clickLogOutFromSelectLoginPage();

		ExtentReportManager.getTest().info("Verifying user is redirected to login page after logout");
		Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "User is not redirected to login page after logout");

		ExtentReportManager.getTest().info("User successfully logged out from Select Login Type page");
		ExtentReportManager.getTest().info("Verify Logout from Back office Dashboard Page");

		Log.info("===== Test Started : Logout from Dashboard Page =====");
		ExtentReportManager.getTest().info("Logging in with valid credentials to reach Select Login Type page");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Verifying the landing page URL after successful login");
		Log.info("Comparing expected URL with actual URL");

		// Add your assertion here based on successful login
		Assert.assertTrue(loginPage.verifyPageUrl().contains("/backoffice/login-choice"),
				"Login failed with valid credentials");

		ExtentReportManager.getTest().info("Clicking Back Office Login button");
		selectLoginTypePage.clickBackOfficeLogin();

		ExtentReportManager.getTest().info("Verifying redirection to Back Office Dashboard");
		Log.info("Checking if URL contains /backoffice/dashboard");
		Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/backoffice/dashboard"),
				"Admin is not redirected to Back Office Dashboard");

		ExtentReportManager.getTest().info("Hovering over profile icon");
		selectLoginTypePage.hoverOnProfile();

		ExtentReportManager.getTest().info("Verifying user is redirected to login page");
		Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "User is not redirected to login page after logout");

		ExtentReportManager.getTest().info("User successfully logged out from Back Office Dashboard");

		Log.info("===== Test Completed Successfully =====");
	}

	@Test(priority = 4) // TC_PL_005 Hotel Search Security (SQL Injection) & Empty Search Result
	public void TC_PL_005_verifyHotelSearchSecurityAndEmptyResult() {

		Log.info("===== Test Started : Hotel Search Security & Empty Result =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("logging in with valid credentials to reach Select Login Type page");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));

		ExtentReportManager.getTest().info("Verifying the landing page URL after successful login");
		Log.info("Comparing expected URL with actual URL");

		Assert.assertTrue(loginPage.verifyPageUrl().contains("/backoffice/login-choice"),
				"Login failed with valid credentials");

		SelectLoginTypePage selectLoginTypePage = new SelectLoginTypePage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Navigating to Hotel Login page");
		selectLoginTypePage.clickHotelLogin();

		ExtentReportManager.getTest().info("Verify user is redirected to Hotel Search page");
		Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/backoffice/select-hotel"),
				"User is not redirected to Hotel Search page");

		/* ---------- Case 1: SQL Injection ---------- */
		String sqlPayload = "' OR 1=1 --";
		ExtentReportManager.getTest().info("Entering SQL Injection payload in hotel search: " + sqlPayload);
		Log.info("Performing SQL Injection test on hotel search");

		selectLoginTypePage.searchHotel(sqlPayload);

		List<String> sqlResults = selectLoginTypePage.getSearchResultsText(); 

		Assert.assertTrue(sqlResults.isEmpty(), "Search returned results for SQL Injection input");

		ExtentReportManager.getTest().info("Hotel search is protected against SQL Injection");

		/* ---------- Case 2: Empty Search Result ---------- */
		String randomText = "XYZ123";
		ExtentReportManager.getTest().info("Entering random text to validate empty search result: " + randomText);
		Log.info("Performing empty search result validation");

		selectLoginTypePage.clearSearch();
		selectLoginTypePage.searchHotel(randomText);

		List<String> emptyResults = selectLoginTypePage.getSearchResultsText(); 

		Assert.assertTrue(emptyResults.isEmpty(), "Search returned results for random invalid input");

		ExtentReportManager.getTest().info("Proper behavior observed when no hotels match the search criteria");

		Log.info("===== Test Completed Successfully =====");
	}

}
