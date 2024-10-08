package org.automation.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserFactory {


	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browserName) {
        if (driver.get() == null) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    // Set up ChromeDriver path here
                    driver.set(new ChromeDriver());
                    break;
                case "firefox":
                    // Set up GeckoDriver path here
                    driver.set(new FirefoxDriver());
                    break;
                case "edge":
                    // Set up EdgeDriver path here
                    driver.set(new EdgeDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }
        }
        return driver.get();
    }

//	public List<WebDriver> getBrowsers(String browserName, String browserVersion) {
//		List<WebDriver> drivers = new ArrayList<>();
//
//		Arrays.stream(browserName.split(",")).forEach(browser -> {
//			WebDriver driver = getLocalDriver();
//			switch (browser.trim().toLowerCase()) {
//			case "chrome":
//				driver = new ChromeDriver();
//				break;
//			case "firefox":
//				driver = new FirefoxDriver();
//				break;
//			default:
//				throw new IllegalArgumentException("Browser not supported: " + browser);
//			}
//			drivers.add(driver);
//		});
//		return drivers;
//	}

}
