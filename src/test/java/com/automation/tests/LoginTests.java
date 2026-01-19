package com.automation.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;

public class LoginTests extends BaseTest {

	@Test(priority = 1) // TC_BO_001 Check the login page input elements. (Verify the email, password,
						// and login button is displayed or not.)
	public void verifyLoginPageIsDisplayed() {
		test = ExtentReportManager.createTest("Verify Login Page Is Displayed");
		Log.info("===== Test Case 1 Started : Verify Login Page Is Displayed =====");

		LoginPage loginPage = new LoginPage(driver);

		test.info("Checking whether the Login page is loaded successfully");
		Log.info("Verifying presence of Login button on Login page");

		// Add your assertion here to verify that the login page is displayed
		Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed on Login page");

		test.pass("Login page is displayed successfully with Login button visible");
		Log.info("Login page verification completed successfully");

		Log.info("===== Test Case 1 Ended =====");

	}

	@Test(priority = 2) // TC_BO_002 Login with valid credentials
	public void verifyLoginWithValidCredentials() {
		test = ExtentReportManager.createTest("Verify Login With Valid Credentials");
		Log.info("===== Test Case 2 Started : Verify Login With Valid Credentials =====");

		LoginPage loginPage = new LoginPage(driver);

		test.info("Fetching valid login credentials from configuration file");
		Log.info("Reading email and password from config file");

		String email = ConfigReader.getProperty("email");
		String password = ConfigReader.getProperty("password");

		test.info("Performing login with valid credentials");
		Log.info("Calling login method of LoginPage");

		String actualUrl = loginPage.login(email, password);
		String expectedUrl = "https://apycue-staging.web.app/backoffice/login-choice";

		test.info("Verifying the landing page URL after successful login");
		Log.info("Comparing expected URL with actual URL");

		// Add your assertion here based on successful login
		Assert.assertEquals(actualUrl, expectedUrl, "The User is not navigated to the expected page after login");

		test.pass("Login successful and user navigated to expected page");
		Log.info("Login with valid credentials verified successfully");

		Log.info("===== Test Case 2 Ended =====");
	}

	@Test(priority = 3) // TC_BO_003 Login with incorrect password
	public void verifyLoginWithIncorrectPassword() {
		test = ExtentReportManager.createTest("Verify Login with incorrect password");
		Log.info("===== Test Case 3 Started : Verify Login with incorrect password=====");

		LoginPage loginPage = new LoginPage(driver);
		String expectedMsg = "Invalid credentials";

		String email = ConfigReader.getProperty("email");
		String password = "test@123456";

		test.info("Attempting Login with incorrect password");
		Log.info("Calling getErrorMessage method of LoginPage");

		String actualMsg = loginPage.getErrorMessage(email, password);

		test.info("Verifying validation message displayed for the Login with incorrect password attempt");
		Log.info("Comparing expected and actual validation message");

		Assert.assertEquals(actualMsg, expectedMsg,
				"Incorrect validation message displayed for the Login with unregistered email");

		test.pass("Right validation message displayed for Login with incorrect password attempt");
		Log.info("The Login with incorrect password scenario verified successfully");

		Log.info("===== Test Case 3 Ended =====");
	}

	@Test(priority = 4) // TC_BO_004 Login with unregistered email
	public void verifyLoginWithUnregisteredEmail() {
		test = ExtentReportManager.createTest("Verify Login with unregistered email");
		Log.info("===== Test Case 4 Started : Verify Login with unregistered email=====");

		LoginPage loginPage = new LoginPage(driver);
		String expectedMsg = "Invalid credentials";

		String email = "jaydeep11@apycue.com";
		String password = ConfigReader.getProperty("password");

		test.info("Attempting Login with unregistered email");
		Log.info("Calling getErrorMessage method of LoginPage");

		String actualMsg = loginPage.getErrorMessage(email, password);

		test.info("Verifying validation message displayed for the Login with unregistered email attempt");
		Log.info("Comparing expected and actual validation message");

		Assert.assertEquals(actualMsg, expectedMsg,
				"Incorrect validation message displayed for the Login with unregistered email");

		test.pass("Right validation message displayed for Login with incorrect password attempt");
		Log.info("The Login with unregistered email scenario verified successfully");

		Log.info("===== Test Case 4 Ended =====");
	}

	@Test(priority = 5) // TC_BO_005 Password Masking and TC_BO_009 Password visibility toggle
	public void verifyPasswordMasking() {

		test = ExtentReportManager.createTest("Verify Password Masking");
		Log.info("===== Verify Password Masking Test Started =====");

		LoginPage loginPage = new LoginPage(driver);

		// Step 1: Verify password masking before input
		test.info("Verifying password field masking before entering password");
		Log.info("Checking password field type before input");

		boolean isMaskedBeforeInput = loginPage.isPasswordMasked();
		Assert.assertTrue(isMaskedBeforeInput, "Password field is not masked before input");

		test.pass("Password field is masked before entering password");
		Log.info("Password masking verified before input");

		// Step 2: Verify password masking after input
		test.info("Verifying password field masking after entering password");
		Log.info("Checking password field type after input");

		String password = ConfigReader.getProperty("password");
		boolean isMaskedAfterInput = loginPage.isPasswordMaskedAfterInput(password);

		Assert.assertTrue(isMaskedAfterInput, "Password field is not masked after input");

		test.pass("Password field remains masked after entering password");
		Log.info("Password masking verified after input");

		Log.info("===== Verify Password Masking Test Completed =====");
	}

	@Test(priority = 6) // TC_BO_006 Verify Case sensitivity in password
	public void verifyCaseSensitivityPassword() {
		test = ExtentReportManager.createTest("Verify Case sensitivity in password");
		Log.info("===== Test Case 5 Started : Verify Case sensitivity in password=====");

		LoginPage loginPage = new LoginPage(driver);
		String expectedMsg = "Invalid credentials";

		String email = ConfigReader.getProperty("email");
		String password = ConfigReader.getProperty("password").toUpperCase();

		test.info("Attempting Case sensitivity in password");
		Log.info("Calling getErrorMessage method of LoginPage");

		String actualMsg = loginPage.getErrorMessage(email, password);

		test.info("Verifying validation message displayed for the Case sensitivity in password attempt");
		Log.info("Comparing expected and actual validation message");

		Assert.assertEquals(actualMsg, expectedMsg,
				"Incorrect validation message displayed for the Case sensitivity in password");

		test.pass("Right validation message displayed for the Case sensitivity in password attempt");
		Log.info("The Case sensitivity in password scenario verified successfully");

		Log.info("===== Test Case 5 Ended =====");
	}

	@Test(priority = 7) // TC_BO_07 Mandatory field validation (Verify alerts when fields are left
						// empty.)
	public void verifyBlankSubmission() {
		test = ExtentReportManager.createTest("Verify Blank Submission Attempt");
		Log.info("===== Test Case 6 Started : Verify Blank Submission Attempt=====");

		LoginPage loginPage = new LoginPage(driver);
		List<String> expectedMsg = new ArrayList<String>();
		expectedMsg.add("Please enter the email address");
		expectedMsg.add("Please enter the password");

		test.info("Attempting Blank submission attempt");
		Log.info("Calling getErrorMessage method of LoginPage");

		List<String> actualMsg = new ArrayList<String>();
		actualMsg = loginPage.blankSubmit();

		test.info("Verifying validation message displayed for the Blank submission attempt attempt");
		Log.info("Comparing expected and actual validation message");

		for (String msg : expectedMsg) {
			Assert.assertTrue(actualMsg.contains(msg),
					"Incorrect validation message displayed for the Blank submission attempt");
		}

		test.pass("Right validation message displayed for the Blank submission attempt attempt");
		Log.info("The Blank submission attempt scenario verified successfully");

		Log.info("===== Test Case 6 Ended =====");
	}

	@Test(priority = 8) // TC_BO_008 Invalid Email format (Verify validation for incorrect email
						// syntax.)
	public void verifyInvalidEmailFormatValidation() {

		test = ExtentReportManager.createTest("Verify Invalid Email Format Validation");
		Log.info("===== Verify Invalid Email Format Validation Test Started =====");

		LoginPage loginPage = new LoginPage(driver);

		String invalidEmail = "jaydeep@apycue"; // invalid syntax
		String password = ConfigReader.getProperty("password");
		String expectedMsg = "Invalid email address";

		test.info("Attempting login with invalid email format");
		Log.info("Entering invalid email format and valid password");

		String actualMsg = loginPage.verifyInvalidEmailFormat(invalidEmail, password);

		test.info("Verifying validation message for invalid email format");
		Log.info("Expected Message: " + expectedMsg + " | Actual Message: " + actualMsg);

		Assert.assertEquals(actualMsg, expectedMsg, "Incorrect validation message displayed for invalid email format");

		test.pass("Correct validation message displayed for invalid email format");
		Log.info("===== Verify Invalid Email Format Validation Test Completed =====");
	}

	@Test(priority = 9) // TC_BO_009 Password visibility toggle
	public void verifyPasswordVisibilityToggle() {

		test = ExtentReportManager.createTest("Verify Password Visibility Toggle");
		Log.info("===== Verify Password Visibility Toggle Test Started =====");

		LoginPage loginPage = new LoginPage(driver);

		// Step 1: Verify password is masked by default
		test.info("Verifying password is masked by default");
		Log.info("Checking password field default masking");

		Assert.assertTrue(loginPage.isPasswordMasked(), "Password field is not masked by default");

		test.pass("Password is masked by default");

		// Step 2: Click eye icon → password visible
		test.info("Clicking eye icon to show password");
		Log.info("Clicking eye icon");

		loginPage.clickEyeIcon();

		Assert.assertTrue(loginPage.isPasswordVisible(), "Password is not visible after clicking eye icon");

		test.pass("Password becomes visible after clicking eye icon");

		// Step 3: Click eye icon again → password masked
		test.info("Clicking eye icon again to hide password");
		Log.info("Clicking eye icon again");

		loginPage.clickEyeIcon();

		Assert.assertTrue(loginPage.isPasswordMasked(), "Password is not masked after clicking eye icon again");

		test.pass("Password is masked again after second click");

		Log.info("===== Verify Password Visibility Toggle Test Completed =====");
	}

}
