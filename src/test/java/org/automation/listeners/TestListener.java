package org.automation.listeners;

import java.io.File;
import java.util.Arrays;

import org.automation.utility.ApplicationConfiguration;
import org.automation.utility.Utilities;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestListener implements ITestListener {

	private ExtentReports extentReportInstance;
	private ExtentTest extentTestInstance;
	ApplicationConfiguration config;

	@Override
	public void onStart(ITestContext context) {
		ISuite suiteInstance = context.getSuite();
		extentReportInstance = (ExtentReports) suiteInstance.getAttribute("extentReport");
		config = (ApplicationConfiguration) suiteInstance.getAttribute("Configurations");
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReportInstance.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		extentTestInstance = extentReportInstance.createTest(
				result.getTestClass().getName() + "      @TestCase : " + result.getMethod().getMethodName());
		extentTestInstance.assignCategory(result.getTestClass().getName()); // This line is ued to add catagories.
		extentTestInstance.log(Status.INFO, result.getTestClass().getName() + "      @TestCase : "
				+ result.getMethod().getMethodName() + "test is started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE : - " + methodName.toUpperCase() + " Passed" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTestInstance.pass(m);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String path = config.getScreenshotPath() + File.separator;
		String screenshotPath = Utilities.captureScreenshotWithRobot(result.getName(), path);
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTestInstance.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
				+ "Exception Occured : Click to see" + "</font>" + "</b>" + "</summary>"
				+ exceptionMessage.replaceAll(",", "<br>") + "</details>" + "\n");
		String failureLog = "TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
		extentTestInstance.log(Status.FAIL, m);
		extentTestInstance.fail("<font color=" + "red>" + "Snapshot below: "
				+ extentTestInstance.addScreenCaptureFromPath(screenshotPath));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE : - " + methodName.toUpperCase() + " SKipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		extentTestInstance.skip(m);
	}
}
