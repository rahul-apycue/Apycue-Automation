package com.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.utils.ConfigReader;
import com.automation.utils.Log;

public class LoginPage extends BasePage {

	private static final String LOGIN_CHOICE_URL = ConfigReader.getProperty("expected_Url");

	@FindBy(id = "email")
	private WebElement emailField;

	@FindBy(id = "password")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@type='submit' and contains(.,'Sign In')]")
	private WebElement signinButton;

	@FindBy(xpath = "//div[@class='text-xs leading-4 mt-1.5 text-destructive']")
	private WebElement errorMessage;

	@FindBy(xpath = "//div[contains(text(),'Invalid credentials')]")
	private WebElement poptxt;

	@FindBy(xpath = "//span[contains(text(),'Logout')]")
	private WebElement logOutBtn;

	@FindBy(xpath = "//span[contains(.,'mail')]")
	private WebElement mailFavicon;

	@FindBy(xpath = "//span[contains(.,'lock')]")
	private WebElement passwordFavicon;

	@FindBy(xpath = "//p[contains(text(),'Please enter the email address')]")
	private WebElement emailFieldBlankMsg;

	@FindBy(xpath = "//p[contains(text(),'Please enter the password')]")
	private WebElement passwordFieldBlankMsg;

	@FindBy(xpath = "//p[contains(.,'Invalid email address')]")
	private WebElement emailValidationErrorMsg;

	@FindBy(xpath = "//span[contains(text(),'visibility')]")
	private WebElement eyeIcon;

	@FindBy(xpath = "//h1[contains(text(),'Welcome back,')]")
	private WebElement welcomeMsg;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void enterEmail(String email) {
		emailField.clear();
		sendKeys(emailField, email);
	}

	public void enterPassword(String password) {
		passwordField.clear();
		sendKeys(passwordField, password);
	}

	public void clickSignButton() {
		click(signinButton);
	}

	public String verifyPageUrl() {

		Log.info("LoginPage - getCurrentUrl: " + driver.getCurrentUrl());
		return driver.getCurrentUrl();
	}

	public void login(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickSignButton();
		wait.until(ExpectedConditions.visibilityOfAllElements(logOutBtn));
		wait.until(ExpectedConditions.elementToBeClickable(logOutBtn));

	}

	// Logout (generic – adjust locator if needed)
	public void logout() {
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
	}

	public String getErrorMessage(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickSignButton();
		wait.until(ExpectedConditions.visibilityOf(poptxt));
		Log.info("LoginPage - poptxt.getText(): " + poptxt.getText());

		return poptxt.getText();
	}

	public boolean isLoginButtonDisplayed() {

		return isDisplayed(signinButton);
	}

	public List<String> blankSubmit() {
		clickSignButton();

		wait.until(ExpectedConditions.visibilityOfAllElements(emailFieldBlankMsg, passwordFieldBlankMsg));

		List<String> messages = new ArrayList<>();

		if (isDisplayed(emailFieldBlankMsg)) {
			messages.add(emailFieldBlankMsg.getText());
		}

		if (isDisplayed(passwordFieldBlankMsg)) {
			messages.add(passwordFieldBlankMsg.getText());
		}
		return messages;
	}

	public boolean isPasswordMasked() {

		return "password".equalsIgnoreCase(passwordField.getAttribute("type"));
	}

	public boolean isEyeIconDisplayed(){
		String eyeStatus = eyeIcon.getText();
		return "visibility".equalsIgnoreCase(eyeStatus);
	}

	// Check if password is visible
	public boolean isPasswordVisible() {
		String typeAttribute = passwordField.getAttribute("type");
		return "password".equalsIgnoreCase(typeAttribute);
	}

	// Click on eye icon
	public void clickEyeIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(eyeIcon)).click();
	}

	public boolean isPasswordMaskedAfterInput(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
		String typeAttribute = passwordField.getAttribute("type");
		return "password".equalsIgnoreCase(typeAttribute);
	}

	public String verifyInvalidEmailFormat(String invalidEmail, String password) {

		enterEmail(invalidEmail);
		enterPassword(password);
		clickSignButton();

		wait.until(ExpectedConditions.visibilityOf(emailValidationErrorMsg));
		return emailValidationErrorMsg.getText().trim();
	}

	public void openLoginChoicePage() {
		driver.get(LOGIN_CHOICE_URL);
	}

	// Verify redirection to Login page
	public boolean isRedirectedToLoginPage() {
		String currentUrl = driver.getCurrentUrl();
		// Check for null before calling .contains() to prevent NPE
		if (currentUrl == null) {
			Log.warn("Current URL is null; cannot verify redirection.");
			return false;
		}
		return currentUrl.contains("/login");
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void sqlLoginAttempt(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickSignButton();
	}

	public String getLoginErrorMessage() {
		return emailField.getAttribute("validationMessage");
	}

	public boolean isLoginSuccessful() {
		String currentUrl = driver.getCurrentUrl();
		// Return false if URL is null, otherwise check if it excludes "/login"
		return currentUrl != null && !currentUrl.contains("/login");
	}

}