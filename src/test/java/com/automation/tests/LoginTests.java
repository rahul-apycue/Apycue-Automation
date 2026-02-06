package com.automation.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;

public class LoginTests extends BaseTest {

	@Test(priority = 1) // TC_BO_001 Check the login page input elements. (Verify the email, password,
						// and login button is displayed or not.)
	public void TC_BO_001_verifyLoginPageIsDisplayed() {
		Log.info("===== Test Case 1 Started : Verify Login Page Is Displayed =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Checking whether the Login page is loaded successfully");
		Log.info("Verifying presence of Login button on Login page");

		// Add your assertion here to verify that the login page is displayed
		Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed on Login page");

		ExtentReportManager.getTest().info("Login page is displayed successfully with Login button visible");
		Log.info("Login page verification completed successfully");

		Log.info("===== Test Case 1 Ended =====");

	}

	@Test(priority = 2) // TC_BO_002 Login with valid credentials
	public void TC_BO_002_verifyLoginWithValidCredentials() {
		Log.info("===== Test Case 2 Started : Verify Login With Valid Credentials =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Fetching valid login credentials from configuration file");
		Log.info("Reading email and password from config file");

		String email = ConfigReader.getProperty("email");
		String password = ConfigReader.getProperty("password");

		ExtentReportManager.getTest().info("Performing login with valid credentials");
		Log.info("Calling login method of LoginPage");

		loginPage.login(email, password);

		ExtentReportManager.getTest().info("Verifying the landing page URL after successful login");
		Log.info("Comparing expected URL with actual URL");

		// Add your assertion here based on successful login
		Assert.assertTrue(loginPage.verifyPageUrl().contains("/backoffice/login-choice"), "Login failed with valid credentials");

		ExtentReportManager.getTest().info("Login successful and user navigated to expected page");
		Log.info("Login with valid credentials verified successfully");

		Log.info("===== Test Case 2 Ended =====");
	}

	@Test(priority = 3) // TC_BO_003 Login with incorrect password
	public void TC_BO_003_verifyLoginWithIncorrectPassword() {
		Log.info("===== Test Case 3 Started : Verify Login with incorrect password=====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		String expectedMsg = "Invalid credentials";

		String email = ConfigReader.getProperty("email");
		String password = "test@123456";

		ExtentReportManager.getTest().info("Attempting Login with incorrect password");
		Log.info("Calling getErrorMessage method of LoginPage");

		String actualMsg = loginPage.getErrorMessage(email, password);

		ExtentReportManager.getTest().info("Verifying validation message displayed for the Login with incorrect password attempt");
		Log.info("Comparing expected and actual validation message");

		Assert.assertEquals(actualMsg, expectedMsg,
				"Incorrect validation message displayed for the Login with unregistered email");

		ExtentReportManager.getTest().info("Right validation message displayed for Login with incorrect password attempt");
		Log.info("The Login with incorrect password scenario verified successfully");

		Log.info("===== Test Case 3 Ended =====");
	}

	@Test(priority = 4) // TC_BO_004 Login with unregistered email
	public void TC_BO_004_verifyLoginWithUnregisteredEmail() {
		Log.info("===== Test Case 4 Started : Verify Login with unregistered email=====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		String expectedMsg = "Invalid credentials";

		String email = "jaydeep11@apycue.com";
		String password = ConfigReader.getProperty("password");

		ExtentReportManager.getTest().info("Attempting Login with unregistered email");
		Log.info("Calling getErrorMessage method of LoginPage");

		String actualMsg = loginPage.getErrorMessage(email, password);

		ExtentReportManager.getTest().info("Verifying validation message displayed for the Login with unregistered email attempt");
		Log.info("Comparing expected and actual validation message");

		Assert.assertEquals(actualMsg, expectedMsg,
				"Incorrect validation message displayed for the Login with unregistered email");

		ExtentReportManager.getTest().info("Right validation message displayed for Login with incorrect password attempt");
		Log.info("The Login with unregistered email scenario verified successfully");

		Log.info("===== Test Case 4 Ended =====");
	}

	@Test(priority = 5) // TC_BO_005 Password Masking and TC_BO_009 Password visibility toggle
	public void TC_BO_005_verifyPasswordMasking() {

		Log.info("===== Verify Password Masking Test Started =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		// Step 1: Verify password masking before input
		ExtentReportManager.getTest().info("Verifying password field masking before entering password");
		Log.info("Checking password field type before input");

		boolean isMaskedBeforeInput = loginPage.isPasswordMasked();
		Assert.assertTrue(isMaskedBeforeInput, "Password field is not masked before input");

		ExtentReportManager.getTest().info("Password field is masked before entering password");
		Log.info("Password masking verified before input");

		// Step 2: Verify password masking after input
		ExtentReportManager.getTest().info("Verifying password field masking after entering password");
		Log.info("Checking password field type after input");

		String password = ConfigReader.getProperty("password");
		boolean isMaskedAfterInput = loginPage.isPasswordMaskedAfterInput(password);

		Assert.assertTrue(isMaskedAfterInput, "Password field is not masked after input");

		ExtentReportManager.getTest().info("Password field remains masked after entering password");
		Log.info("Password masking verified after input");

		Log.info("===== Verify Password Masking Test Completed =====");
	}

	@Test(priority = 6) // TC_BO_006 Verify Case sensitivity in password
	public void TC_BO_006_verifyCaseSensitivityPassword() {
		Log.info("===== Test Case 5 Started : Verify Case sensitivity in password=====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		String expectedMsg = "Invalid credentials";

		String email = ConfigReader.getProperty("email");
		String password = ConfigReader.getProperty("password").toUpperCase();

		ExtentReportManager.getTest().info("Attempting Case sensitivity in password");
		Log.info("Calling getErrorMessage method of LoginPage");

		String actualMsg = loginPage.getErrorMessage(email, password);

		ExtentReportManager.getTest().info("Verifying validation message displayed for the Case sensitivity in password attempt");
		Log.info("Comparing expected and actual validation message");

		Assert.assertEquals(actualMsg, expectedMsg,
				"Incorrect validation message displayed for the Case sensitivity in password");

		ExtentReportManager.getTest().info("Right validation message displayed for the Case sensitivity in password attempt");
		Log.info("The Case sensitivity in password scenario verified successfully");

		Log.info("===== Test Case 5 Ended =====");
	}

	@Test(priority = 7) // TC_BO_07 Mandatory field validation (Verify alerts when fields are left
						// empty.)
	public void TC_BO_007_verifyBlankSubmission() {

		Log.info("===== Test Case 6 Started : Verify Blank Submission Attempt=====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		List<String> expectedMsg = new ArrayList<String>();
		expectedMsg.add("Please enter the email address");
		expectedMsg.add("Please enter the password");

		ExtentReportManager.getTest().info("Attempting Blank submission attempt");
		Log.info("Calling getErrorMessage method of LoginPage");

		List<String> actualMsg = loginPage.blankSubmit();

		ExtentReportManager.getTest().info("Verifying validation message displayed for the Blank submission attempt attempt");
		Log.info("Comparing expected and actual validation message");

		for (String msg : expectedMsg) {
			Assert.assertTrue(actualMsg.contains(msg),
					"Incorrect validation message displayed for the Blank submission attempt");
		}

		ExtentReportManager.getTest().info("Right validation message displayed for the Blank submission attempt attempt");
		Log.info("The Blank submission attempt scenario verified successfully");

		Log.info("===== Test Case 6 Ended =====");
	}

	@Test(priority = 8) // TC_BO_008 Invalid Email format (Verify validation for incorrect email
						// syntax.)
	public void TC_BO_008_verifyInvalidEmailFormatValidation() {

		Log.info("===== Verify Invalid Email Format Validation Test Started =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		String invalidEmail = "jaydeep@apycue"; // invalid syntax
		String password = ConfigReader.getProperty("password");
		String expectedMsg = "Invalid email address";

		ExtentReportManager.getTest().info("Attempting login with invalid email format");
		Log.info("Entering invalid email format and valid password");

		String actualMsg = loginPage.verifyInvalidEmailFormat(invalidEmail, password);

		ExtentReportManager.getTest().info("Verifying validation message for invalid email format");
		Log.info("Expected Message: " + expectedMsg + " | Actual Message: " + actualMsg);

		Assert.assertEquals(actualMsg, expectedMsg, "Incorrect validation message displayed for invalid email format");

		ExtentReportManager.getTest().info("Correct validation message displayed for invalid email format");
		Log.info("===== Verify Invalid Email Format Validation Test Completed =====");
	}

	@Test(priority = 9) // TC_BO_009 Password visibility toggle
	public void TC_BO_009_verifyPasswordVisibilityToggle() {

		Log.info("===== TC_BO_009 Started : Verify Password Visibility Toggle Test Started =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		// Step 1: Verify password is masked by default
		ExtentReportManager.getTest().info("Verifying password is masked by default");
		Log.info("Checking password field default masking");

		Assert.assertTrue(loginPage.isEyeIconDisplayed(), "Eye icon for password visibility toggle is not displayed");

		ExtentReportManager.getTest().info("Password is masked by default");

		// Step 2: Click eye icon → password visible
		ExtentReportManager.getTest().info("Clicking eye icon to show password");
		Log.info("Clicking eye icon");

		loginPage.clickEyeIcon();

		Assert.assertTrue(loginPage.isPasswordVisible(), "Password is not visible after clicking eye icon");

		ExtentReportManager.getTest().info("Password becomes visible after clicking eye icon");

		// Step 3: Click eye icon again → password masked
		ExtentReportManager.getTest().info("Clicking eye icon again to hide password");
		Log.info("Clicking eye icon again");

		loginPage.clickEyeIcon();

		Assert.assertTrue(loginPage.isPasswordMasked(), "Password is not masked after clicking eye icon again");

		ExtentReportManager.getTest().info("Password is masked again after second click");

		Log.info("===== Verify Password Visibility Toggle Test Completed =====");
	}

	@Test(priority = 10) // TC_BO_010 Verify URL Manipulation (Bypass) after Logout
	public void TC_BO_010_verifyUrlManipulationAfterLogout() {

		Log.info("===== TC_BO_010 Started : URL Manipulation Verification =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: User logs out from the application");
		Log.info("Deleting session and logging out");
		loginPage.logout();

		ExtentReportManager.getTest().info("Step 2: User pastes internal Select Login Type URL");
		Log.info("Navigating to /backoffice/login-choice directly");
		loginPage.openLoginChoicePage();

		ExtentReportManager.getTest().info("Step 3: Verifying redirection to Login page");
		Log.info("Checking if access is blocked");

		Assert.assertTrue(loginPage.isRedirectedToLoginPage(),
				"User is able to access Select Login Type page after logout");

		ExtentReportManager.getTest().info("User cannot access Select Login Type page without login");
		Log.info("===== TC_BO_010 Completed Successfully =====");
	}

	@Test(priority = 11) // TC_BO_011 Verify Session Persistence using Browser Back Button
	public void TC_BO_011_verifySessionPersistenceUsingBackButton() {

		Log.info("===== TC_BO_011 Started : Session Persistence Verification =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		ExtentReportManager.getTest().info("Step 1: Login with valid credentials");
		loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
		if (!loginPage.verifyPageUrl().contains("/backoffice/login-choice")) {
			Assert.fail("Login failed with valid credentials");
		}
		ExtentReportManager.getTest().info("Step 2: Logout from application");
		loginPage.logout();

		ExtentReportManager.getTest().info("Step 3: Click browser Back button");
		loginPage.navigateBack();

		ExtentReportManager.getTest().info("Step 4: Verify session is not restored");
		Assert.assertTrue(loginPage.isRedirectedToLoginPage(), "Session restored after logout using Back button");

		ExtentReportManager.getTest().info("Session not persisted after logout using Back button");
		Log.info("===== TC_BO_011 Completed Successfully =====");
	}

	@Test(priority = 12) // TC_BO_012 Verify SQL Injection Protection
	public void TC_BO_012_verifySQLInjectionAttempt() {

		Log.info("===== TC_BO_012 Started : SQL Injection Validation =====");

		LoginPage loginPage = new LoginPage(DriverManager.getDriver());

		String sqlPayload = "' OR 1=1 --";

		ExtentReportManager.getTest().info("Step 1: Enter SQL injection payload in Email and Password fields");
		loginPage.sqlLoginAttempt(sqlPayload, sqlPayload);

		ExtentReportManager.getTest().info("Step 2: Verify login is blocked");
		Assert.assertFalse(loginPage.isLoginSuccessful(), "Login succeeded with SQL Injection payload");

		ExtentReportManager.getTest().info("Step 3: Verify proper validation message is shown");
		String actualError = loginPage.getLoginErrorMessage();

		Assert.assertTrue(actualError.contains("include an '@' in the email address"),
				"Incorrect validation message for SQL Injection attempt");

		ExtentReportManager.getTest().info("SQL Injection attempt successfully blocked");
		Log.info("===== TC_BO_012 Completed Successfully =====");
	}
}
