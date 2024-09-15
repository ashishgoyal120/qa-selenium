package org.automation.login;

import org.automation.basePackage.Base;
import org.automation.pom.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends Base {
	private LoginPage loginPage;

	@BeforeClass
	public void initializeResource() {
		loginPage = new LoginPage(browserDriver);

		System.out.println("Initialized the POM object for Login Page");
	}

	@Test
	public void verifyPresenceOfelement() {
		int elementCount = 0;
		elementCount += (loginPage.getLoginButton() == null ? 0 : 1);
		elementCount += (loginPage.getTextPassword() == null ? 0 : 1);
		elementCount += (loginPage.getTextUserName() == null ? 0 : 1);
		if (elementCount != 3) {
			System.out.println("Elements Not Present");
		}

		System.out.println("Login elements are present");
	}

	@Test(dependsOnMethods = { "verifyPresenceOfelement" })
	public void testLogin() {
		try {
			loginPage.enterUserName("Admin");
			loginPage.enterPassword("admin123");
			loginPage.clkLoginButton();
			System.out.println("Successfully Login application");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void failTestCase() {
		Assert.fail("Failed");
	}

}
