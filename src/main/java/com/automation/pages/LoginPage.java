package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    
    @FindBy(id = "email")
    private WebElement emailField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signinButton;
    
    @FindBy(xpath = "//div[@class='text-xs leading-4 mt-1.5 text-destructive']")
    private WebElement errorMessage;
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void enterEmail(String email) {
        sendKeys(emailField, email);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }
    
    public void clickSignButton() {
        click(signinButton);
    }
    
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignButton();
    }
    
    public String getErrorMessage(String email, String password) {
    	enterEmail(email);
        enterPassword(password);
        clickSignButton();
    	return getText(errorMessage);
    }
    
    public boolean isLoginButtonDisplayed() {
        return isDisplayed(signinButton);
    }
}