package org.automation.basePackage;

import org.automation.utility.ApplicationConfiguration;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class Base {

	protected WebDriver browserDriver;
	protected ApplicationConfiguration config;

	@BeforeClass
	public void preProcessor(ITestContext context) {
		ISuite suite = context.getSuite();
		browserDriver = (WebDriver) suite.getAttribute("driverForBrowser");
		config = (ApplicationConfiguration) suite.getAttribute("configurations");
	}

	@AfterClass
	public void postProcessor() {

	}

	public WebDriver getBrowserDriver() {
		return browserDriver;
	}

	public ApplicationConfiguration getConfig() {
		return config;
	}

}
