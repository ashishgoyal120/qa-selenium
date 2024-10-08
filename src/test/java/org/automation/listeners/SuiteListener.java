package org.automation.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.automation.utility.ApplicationConfiguration;
import org.automation.utility.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class SuiteListener implements ISuiteListener {
	
	private ExtentReports extentReportInstance;   // Populate Common Info on report.
	private ExtentSparkReporter reportUI;     // UI Of Report

	@Override
	public void onStart(ISuite suite) {
		performStartupActivities(suite);

	}

	@Override
	public void onFinish(ISuite suite) {
		//closeBrowserDriver(suite);
		performCleanUpActivities(suite);

	}

//	private void closeBrowserDriver(ISuite suite) {
//		WebDriver webDriverInstance = (WebDriver) suite.getAttribute("driverForBrowser");
//		webDriverInstance.quit();
//		System.out.println("Closed Browser Window");
//	}

	private void performCleanUpActivities(ISuite suite) {
		suite.removeAttribute("driverForBrowser");
		suite.removeAttribute("configurations");
	}

	private void performStartupActivities(ISuite suite) {
		ApplicationConfiguration config = readConfigurationFile(suite);
		loadBrowserDriverInstance(suite, config);
		loadExtentReportConfiguration(config, suite);
	}
	
	private void loadExtentReportConfiguration(ApplicationConfiguration config, ISuite suite) {
		String filePath = System.getProperty("user.dir") + File.separator + config.getextentReportsPath() + File.separator;
		filePath += new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		try {
			Files.createDirectories(Paths.get(filePath));
		} catch(IOException e) {
			System.out.println("Error While creating Extent file report directory.");
		}
		reportUI = new ExtentSparkReporter(filePath + File.separator + "report.html");
		String encoding = reportUI.config().getEncoding();
		System.out.println(encoding);
		reportUI.config().setDocumentTitle("Orange HrM Report");
		reportUI.config().setReportName("Automation report");
		reportUI.config().setTheme(Theme.DARK);
		extentReportInstance = new ExtentReports();
		extentReportInstance.attachReporter(reportUI);
		extentReportInstance.setSystemInfo("Automation Tester", "Ashish Goyal");
		extentReportInstance.setSystemInfo("Organization", "Ashish Automation Labs");
		extentReportInstance.setSystemInfo("Build No", "WA-11122");
		suite.setAttribute("extentReport", extentReportInstance);
		
	}

	private ApplicationConfiguration readConfigurationFile(ISuite suite) {
		ApplicationConfiguration config = new ApplicationConfiguration();
		config.loadConfiguration();
		suite.setAttribute("Configurations", config);
		return config;
	}

	private void loadBrowserDriverInstance(ISuite suite, ApplicationConfiguration config) {
		String browserList = config.getBrowserName();
        List<String> browsers = Arrays.asList(browserList.split(","));
        browsers.parallelStream().forEach(browser -> {
            WebDriver driver = BrowserFactory.getDriver(browser);
            driver.get(config.getUrl()); // Example URL, replace with your own
            driver.manage().window().maximize();
    		setTimeout(config, driver.manage());
            System.out.println("Session In SuiteListener : " + Thread.currentThread().getId() + " | Session:  " + ((RemoteWebDriver) driver).getSessionId().toString());
            suite.setAttribute("driverForBrowser",driver);
        });

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
