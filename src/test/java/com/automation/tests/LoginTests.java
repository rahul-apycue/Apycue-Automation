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

	
	  @Test(priority = 1) public void verifyLoginPageIsDisplayed() { test =
	  ExtentReportManager.createTest("Verify Login Page Is Displayed");
	  Log.info("===== Test Case 1 Started : Verify Login Page Is Displayed =====");
	  
	  LoginPage loginPage = new LoginPage(driver);
	  
	  test.info("Checking whether the Login page is loaded successfully");
	  Log.info("Verifying presence of Login button on Login page");
	  
	  // Add your assertion here to verify that the login page is displayed
	  Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
	  "Login button is not displayed on Login page");
	  
	  test.pass("Login page is displayed successfully with Login button visible");
	  Log.info("Login page verification completed successfully");
	  
	  Log.info("===== Test Case 1 Ended =====");
	  
	  }
	  
	  @Test(priority = 2) public void verifyLoginWithValidCredentials() { test =
	  ExtentReportManager.createTest("Verify Login With Valid Credentials"); Log.
	  info("===== Test Case 2 Started : Verify Login With Valid Credentials ====="
	  );
	  
	  LoginPage loginPage = new LoginPage(driver);
	  
	  test.info("Fetching valid login credentials from configuration file");
	  Log.info("Reading email and password from config file");
	  
	  String email = ConfigReader.getProperty("email"); String password =
	  ConfigReader.getProperty("password");
	  
	  test.info("Performing login with valid credentials");
	  Log.info("Calling login method of LoginPage");
	  
	  String actualUrl = loginPage.login(email, password); String expectedUrl =
	  "https://apycue-staging.web.app/backoffice/login-choice";
	  
	  test.info("Verifying the landing page URL after successful login");
	  Log.info("Comparing expected URL with actual URL");
	  
	  // Add your assertion here based on successful login
	  Assert.assertEquals(actualUrl, expectedUrl,
	  "The User is not navigated to the expected page after login");
	  
	  test.pass("Login successful and user navigated to expected page");
	  Log.info("Login with valid credentials verified successfully");
	  
	  Log.info("===== Test Case 2 Ended ====="); }
	  
	  @Test(priority = 3) public void verifyLoginWithIncorrectPassword() { test =
	  ExtentReportManager.createTest("Verify Login with incorrect password"); Log.
	  info("===== Test Case 3 Started : Verify Login with incorrect password====="
	  );
	  
	  LoginPage loginPage = new LoginPage(driver); String expectedMsg =
	  "Invalid credentials";
	  
	  String email = ConfigReader.getProperty("email"); String password =
	  "test@123456";
	  
	  test.info("Attempting Login with incorrect password");
	  Log.info("Calling getErrorMessage method of LoginPage");
	  
	  String actualMsg = loginPage.getErrorMessage(email, password);
	  
	  test.
	  info("Verifying validation message displayed for the Login with incorrect password attempt"
	  ); Log.info("Comparing expected and actual validation message");
	  
	  Assert.assertEquals(actualMsg, expectedMsg,
	  "Incorrect validation message displayed for the Login with unregistered email"
	  );
	  
	  test.
	  pass("Right validation message displayed for Login with incorrect password attempt"
	  );
	  Log.info("The Login with incorrect password scenario verified successfully");
	  
	  Log.info("===== Test Case 3 Ended ====="); }
	  
	  @Test(priority = 4) public void verifyLoginWithUnregisteredEmail() { test =
	  ExtentReportManager.createTest("Verify Login with unregistered email"); Log.
	  info("===== Test Case 4 Started : Verify Login with unregistered email====="
	  );
	  
	  LoginPage loginPage = new LoginPage(driver); String expectedMsg =
	  "Invalid credentials";
	  
	  String email = "jaydeep11@apycue.com"; String password =
	  ConfigReader.getProperty("password");
	  
	  test.info("Attempting Login with unregistered email");
	  Log.info("Calling getErrorMessage method of LoginPage");
	  
	  String actualMsg = loginPage.getErrorMessage(email, password);
	  
	  test.
	  info("Verifying validation message displayed for the Login with unregistered email attempt"
	  ); Log.info("Comparing expected and actual validation message");
	  
	  Assert.assertEquals(actualMsg, expectedMsg,
	  "Incorrect validation message displayed for the Login with unregistered email"
	  );
	  
	  test.
	  pass("Right validation message displayed for Login with incorrect password attempt"
	  );
	  Log.info("The Login with unregistered email scenario verified successfully");
	  
	  Log.info("===== Test Case 4 Ended ====="); }
	  
	  @Test(priority = 5) public void verifyCaseSensitivityPassword() { test =
	  ExtentReportManager.createTest("Verify Case sensitivity in password"); Log.
	  info("===== Test Case 5 Started : Verify Case sensitivity in password=====");
	  
	  LoginPage loginPage = new LoginPage(driver); String expectedMsg =
	  "Invalid credentials";
	  
	  String email = ConfigReader.getProperty("email"); String password =
	  ConfigReader.getProperty("password").toUpperCase();
	  
	  test.info("Attempting Case sensitivity in password");
	  Log.info("Calling getErrorMessage method of LoginPage");
	  
	  String actualMsg = loginPage.getErrorMessage(email, password);
	  
	  test.
	  info("Verifying validation message displayed for the Case sensitivity in password attempt"
	  ); Log.info("Comparing expected and actual validation message");
	  
	  Assert.assertEquals(actualMsg, expectedMsg,
	  "Incorrect validation message displayed for the Case sensitivity in password"
	  );
	  
	  test.
	  pass("Right validation message displayed for the Case sensitivity in password attempt"
	  );
	  Log.info("The Case sensitivity in password scenario verified successfully");
	  
	  Log.info("===== Test Case 5 Ended ====="); }
	 
	@Test(priority = 6)
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

}