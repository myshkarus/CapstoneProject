package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class NavigatePanel extends BasePage {

	@FindBy(linkText = "Medicare")
	public static WebElement linkButtonHome;

	@FindBy(linkText = "About")
	public static WebElement linkButtonAbout;

	@FindBy(linkText = "Contact")
	public static WebElement linkButtonContact;

	@FindBy(linkText = "View Products")
	public static WebElement linkButtonViewProducts;

	@FindBy(linkText = "Sign Up")
	public static WebElement linkButtonSignUp;

	@FindBy(linkText = "Login")
	public static WebElement linkButtonLogin;

	@FindBy (xpath = "//li[@id='userModel']//a")
	public static WebElement accountDropdown;

	@FindBy (linkText = "Manage Product")
	public static WebElement linkButtonManageProducts;

	public NavigatePanel(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public LoginPage gotoLoginPage(WebDriver driver) {
		waitUntilInvisible("//div[@class='se-pre-con']");
		waitUntilClickable(linkButtonLogin);
		linkButtonLogin.click();
		return new LoginPage(driver);
	}

	public SignupPage gotoSignupPage(WebDriver driver) {
		waitUntilInvisible("//div[@class='se-pre-con']");
		waitUntilClickable(linkButtonSignUp);
		linkButtonSignUp.click();
		return new SignupPage(driver);
	}

	public CatalogPage gotoCatalogPage(WebDriver driver) {
		waitUntilInvisible("//div[@class='se-pre-con']");
		waitUntilClickable(linkButtonViewProducts);
		linkButtonViewProducts.click();
		return new CatalogPage(driver);
	}

	public ProductMngtPage gotoProductMngtPage(WebDriver driver) {
		waitUntilInvisible("//div[@class='se-pre-con']");
		waitUntilClickable(linkButtonManageProducts);
		linkButtonManageProducts.click();
		return new ProductMngtPage(driver);
	}

	public HomePage gotoHomePage(WebDriver driver) {
		waitUntilInvisible("//div[@class='se-pre-con']");
		waitUntilClickable(linkButtonHome);
		linkButtonHome.click();
		return new HomePage(driver);
	}
}
