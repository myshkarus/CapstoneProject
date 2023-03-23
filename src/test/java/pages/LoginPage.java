package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

	@FindBy(tagName = "h4")
	public static WebElement loginFormTitle;

	@FindBy(id = "username")
	public static WebElement user;

	@FindBy(id = "password")
	public static WebElement password;

	@FindBy(xpath = "//input[@value='Login']")
	public static WebElement loginButton;

	@FindBy(linkText = "Register Here")
	public static WebElement registerLink;

	@FindBy(id = "username-error")
	public static WebElement usernameError;

	@FindBy(id = "password-error")
	public static WebElement passwordError;

	@FindBy(xpath = "//div[contains(@class, 'alert-success')]")
	public static WebElement alertSuccess;

	@FindBy(xpath = "//div[contains(@class, 'alert-danger')]")
	public static WebElement alertDanger;

	public LoginPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public LoginPage enterCredentials(String userName, String userPassword) {
		waitUntilVisible(loginFormTitle);
		user.sendKeys(userName);
		password.sendKeys(userPassword);
		return this;
	}

	public HomePage login() {
		waitUntilClickable(loginButton);
		loginButton.click();
		return new HomePage(driver);
	}

	public void gotoRegisterPage() {
		registerLink.click();
	}
}
