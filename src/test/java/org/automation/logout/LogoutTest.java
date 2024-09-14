package org.automation.logout;

import java.time.Duration;

import org.automation.basePackage.Base;
import org.automation.pom.LogoutPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LogoutTest extends Base {

	private LogoutPage logoutPage;
	private WebDriverWait waitForReload;

	@BeforeClass
	public void initializeResource() {
		logoutPage = new LogoutPage(browserDriver);
		waitForReload = new WebDriverWait(browserDriver, Duration.ofSeconds(10));
		System.out.println("Initialized the POM object for Logout Page");
	}

	@Test
	public void testLogout() {
		try {
			waitForReload.until(ExpectedConditions.elementToBeClickable(logoutPage.getMoreOptions())).click();
			waitForReload.until(ExpectedConditions.elementToBeClickable(logoutPage.getLogoutButton())).click();
			System.out.println("Successfully Logout of application");
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
