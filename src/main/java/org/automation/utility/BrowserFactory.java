package org.automation.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory {
	
	public WebDriver getBrowser(String browserName, String browserVersion) {
		WebDriver driver = null;
		if(browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			
		} else if(browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		if(driver!=null) {
			System.out.println("Loaded Browser Driver Instance");
		} else {
			System.out.println("Failed to Load Browser Driver Instance");
		}
		return driver;
		
	}

}
