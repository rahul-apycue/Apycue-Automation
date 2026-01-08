package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;

public class LoginTests extends BaseTest {
    
    @Test(priority = 1)
    public void verifyLoginPageIsDisplayed() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed");
    }
    
    @Test(priority = 2)
    public void verifyLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");
        
        loginPage.login(email, password);
        
        // Add your assertion here based on successful login
        // Example: Assert.assertEquals(driver.getTitle(), "Dashboard");
    }
}