package org.automation.listeners;

import java.time.Duration;

import org.automation.utility.ApplicationConfiguration;
import org.automation.utility.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		performStartupActivities(suite);

	}

	@Override
	public void onFinish(ISuite suite) {
		closeBrowserDriver(suite);
		performCleanUpActivities(suite);

	}

	private void closeBrowserDriver(ISuite suite) {
		WebDriver webDriverInstance = (WebDriver) suite.getAttribute("driverForBrowser");
		webDriverInstance.quit();
		System.out.println("Closed Browser Window");
	}

	private void performCleanUpActivities(ISuite suite) {
		suite.removeAttribute("driverForBrowser");
		suite.removeAttribute("configurations");
	}

	private void performStartupActivities(ISuite suite) {
		ApplicationConfiguration config = readConfigurationFile(suite);
		loadBrowserDriverInstance(suite, config);

	}

	private ApplicationConfiguration readConfigurationFile(ISuite suite) {
		ApplicationConfiguration config = new ApplicationConfiguration();
		config.loadConfiguration();
		suite.setAttribute("Configurations", config);
		return config;
	}

	private void loadBrowserDriverInstance(ISuite suite, ApplicationConfiguration config) {
		BrowserFactory browserFactory = new BrowserFactory();
		WebDriver webDriverInstance = browserFactory.getBrowser(config.getBrowserName(), config.getBrowserVersion());
		webDriverInstance.get(config.getUrl());
		webDriverInstance.manage().window().maximize();
		setTimeout(config, webDriverInstance.manage());
		suite.setAttribute("driverForBrowser", webDriverInstance);

	}

	private void setTimeout(ApplicationConfiguration config, Options optionsInstance) {
		setImplicitTimeoutForBrowser(config, optionsInstance);
		setPageLoadTimeoutForBrowser(config, optionsInstance);
		// setScriptTimeout(config, optionsInstance);
		System.out.println("Initialize Timeout Settings");
	}

	private void setImplicitTimeoutForBrowser(ApplicationConfiguration config, Options optionsInstance) {
		optionsInstance.timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitTimeout()));
	}

	private void setPageLoadTimeoutForBrowser(ApplicationConfiguration config, Options optionsInstance) {
		optionsInstance.timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
	}

//	private void setScriptTimeout(ApplicationConfiguration config, Options optionsInstance) {
//		optionsInstance.timeouts().setScriptTimeout(Duration.ofSeconds(config.getScriptTimeout()));
//	}

}
