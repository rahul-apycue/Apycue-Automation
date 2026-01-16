package com.automation.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import com.automation.utils.Log;

public class LoginPage extends BasePage {
	

	@FindBy(id = "email")
	private WebElement emailField;

	@FindBy(id = "password")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement signinButton;

	@FindBy(xpath = "//div[@class='text-xs leading-4 mt-1.5 text-destructive']")
	private WebElement errorMessage;

	@FindBy(xpath = "//div[contains(text(),'Invalid credentials')]")
	private WebElement poptxt;

	@FindBy(xpath = "//span[contains(text(),'Logout')]")
	private WebElement logOutBtn;

	@FindBy(xpath = "//p[contains(text(),'Please enter the email address')]")
	private WebElement emailFieldBlankMsg;
	
	@FindBy(xpath = "//p[contains(text(),'Please enter the password')]")
	private WebElement passwordFieldBlankMsg;
	
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

	public String login(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickSignButton();
		//wait.until(ExpectedConditions.visibilityOf(logOutBtn));
		wait.until(ExpectedConditions.visibilityOfAllElements(logOutBtn));
		Log.info("LoginPage - getCurrentUrl: " + driver.getCurrentUrl());
		return driver.getCurrentUrl();
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
	
	public List <String> blankSubmit() {
		clickSignButton();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(emailFieldBlankMsg,passwordFieldBlankMsg));
		
		List<String> messages = new  ArrayList<>();
		
		if (isDisplayed(emailFieldBlankMsg)) {
			messages.add(emailFieldBlankMsg.getText());
		}
		
		if (isDisplayed(passwordFieldBlankMsg)) {
			messages.add(passwordFieldBlankMsg.getText());
		}
		return messages;
	}

}