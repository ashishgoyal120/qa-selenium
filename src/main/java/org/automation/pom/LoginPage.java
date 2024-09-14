package org.automation.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	private WebDriver browserDriverInstance;

	@FindBy(name = "username")
	private WebElement txtUserName;

	@FindBy(name = "password")
	private WebElement txtPassword;

	@FindBy(css = "button[type='submit']")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		setBrowserDriverInstance(driver);
		PageFactory.initElements(driver, this);
		System.out.println("Initialized Login Page POM elements");
	}

	public WebDriver getBrowserDriverInstance() {
		return browserDriverInstance;
	}

	public void setBrowserDriverInstance(WebDriver browserDriverInstance) {
		this.browserDriverInstance = browserDriverInstance;
	}

	public WebElement getTextUserName() {
		return txtUserName;
	}

	public WebElement getTextPassword() {
		return txtPassword;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void enterUserName(String userName) {
		getTextUserName().clear();
		getTextUserName().sendKeys(userName);
		System.out.println("Entered User name.");
	}

	public void enterPassword(String password) {
		getTextPassword().clear();
		getTextPassword().sendKeys(password);
		System.out.println("Entered Password.");
	}

	public void clkLoginButton() {
		getLoginButton().click();
		System.out.println("Clicked on Login Button");
	}

}
