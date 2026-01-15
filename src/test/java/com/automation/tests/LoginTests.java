package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;

public class LoginTests extends BaseTest {

	@Test(priority = 1)
	public void verifyLoginPageIsDisplayed() {
		test = ExtentReportManager.createTest("verifyLoginPageIsDisplayed");
		Log.info("Test-1 is started");
		LoginPage loginPage = new LoginPage(driver);
		test.info("verifying the login page is displayed");
		// Add your assertion here to verify that the login page is displayed
		Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed");
		Log.info("Test-1 is ended");
		test.info("Verification is completed");

	}

	@Test(priority = 2)
	public void verifyLoginWithValidCredentials() {
		test = ExtentReportManager.createTest("verifyLoginPageIsDisplayed");
		Log.info("Test-2 is started");

		test.info("Test-2 is started");
		String expectedUrl = "https://apycue-staging.web.app/backoffice/login-choice";
		LoginPage loginPage = new LoginPage(driver);
		Log.info("Getting the credentials from config file");
		test.info("Adding the credentials from config file");
		String email = ConfigReader.getProperty("email");
		String password = ConfigReader.getProperty("password");

		Log.info("Calling Login method of LoginPage class");
		test.info("Calling Login method of LoginPage class");
		String actualUrl = loginPage.login(email, password);

		test.info("Verifying the expected URL after login");
		// Add your assertion here based on successful login
		Assert.assertEquals(actualUrl, expectedUrl, "The expected URL is mismatched");
		Log.info("Test-2 is ended");
		test.info("Verification is completed");
	}

	@Test(priority = 3)
	public void verifyLoginWithInValidPassword() {
		test = ExtentReportManager.createTest("verifyLoginWithInValidPassword");
		Log.info("Test-3 is started");
		String expectedMsg = "Invalid credentials";
		LoginPage loginPage = new LoginPage(driver);

		String email = ConfigReader.getProperty("email");
		String password = "Test@123";
		test.info("Calling getErrorMessage method of LoginPage class");
		Log.info("Calling getErrorMessage method of LoginPage class");
		String actualMsg = loginPage.getErrorMessage(email, password);
		test.info("Verifying the expected error message after login");
		// Add your assertion here based on successful login
		Assert.assertEquals(actualMsg, expectedMsg, "Message is wrong");
		Log.info("Test-3 is ended");
		test.info("Verification is completed");
	}

}