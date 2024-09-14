package org.automation.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage {
	private WebDriver browserDriverInstance;

	@FindBy(css = ".oxd-icon.bi-caret-down-fill.oxd-userdropdown-icon")
	private WebElement moreOption;

	@FindBy(className = "oxd-userdropdown-link")
	private WebElement logoutButton;

	public LogoutPage(WebDriver driver) {
		setBrowserDriverInstance(driver);
		PageFactory.initElements(driver, this);
		System.out.println("Initialized Logout Page POM elements");
	}

	public WebElement getMoreOptions() {
		return moreOption;
	}

	public WebElement getLogoutButton() {
		return logoutButton;
	}

	public void clkMoreOption() {
		getMoreOptions().click();
	}

	public void clkLogoutButton() {
		getLogoutButton().click();
	}

	public WebDriver getBrowserDriverInstance() {
		return browserDriverInstance;
	}

	public void setBrowserDriverInstance(WebDriver browserDriverInstance) {
		this.browserDriverInstance = browserDriverInstance;
	}
}
