package com.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
    private static ExtentReports extent;

	private ExtentReportManager() {
		// Private constructor to prevent instantiation
	}
    
    public static ExtentReports getReportInstance() {
     
    	if (extent == null) {
        	
    		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "report/ExtentReports_"+timestamp+".html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            
            reporter.config().setDocumentTitle("Automation Test Report");
            reporter.config().setReportName("Functional Test Report");
            reporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
    
	public static ExtentTest createTest(String testName) {
		return getReportInstance().createTest(testName);
	}
}
