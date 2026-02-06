package com.automation.tests;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.Log;
import com.automation.utils.ScreenshotUtil;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;

public class BaseTest {

	protected static ExtentReports extent;
	protected WaitUtils waitUtils;
	
	@BeforeSuite
	public void beforeSuite() {
		Log.info("==== Test Suite Setup ====");
		extent = ExtentReportManager.getReportInstance();
	}

	@BeforeMethod
	public void beforeMethod(Method method) {

		Log.info("Launching application");
		ExtentReportManager.createTest(method.getName());

		DriverManager.initDriver(ConfigReader.getProperty("browser"));

		waitUtils = new WaitUtils(DriverManager.getDriver());

		DriverManager.getDriver().manage().deleteAllCookies();
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().get(ConfigReader.getProperty("url"));

		waitUtils.waitForPageLoad();
		waitUtils.waitForUiStability(1);

		ScreenshotUtil.captureScreenshot(DriverManager.getDriver(), "InitialPageLoad");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentReportManager.getTest().fail(
					"Test Failed",
					MediaEntityBuilder.createScreenCaptureFromPath(
							ScreenshotUtil.captureScreenshot(
									DriverManager.getDriver(), result.getName()
							)
					).build()
			);
		}

		if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentReportManager.getTest().pass("Test Passed");
		}

		if (result.getStatus() == ITestResult.SKIP) {
			ExtentReportManager.getTest().skip("Test Skipped");
		}

		DriverManager.quitDriver();
	}

	@AfterSuite
	public void afterSuite() {
		extent.flush();
		Log.info("==== Test Suite Completed ====");
	}
}
