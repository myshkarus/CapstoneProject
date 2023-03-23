package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.Address;
import common.Person;
import common.Person.Role;

public class SignupPage extends BasePage {

	// ******** Personal *********

	@FindBy(tagName = "h4")
	public static WebElement registerFormTitle;

	@FindBy(id = "firstName")
	public static WebElement firstName;

	@FindBy(id = "lastName")
	public static WebElement lastName;

	@FindBy(id = "email")
	public static WebElement email;

	@FindBy(id = "contactNumber")
	public static WebElement contactNumber;

	@FindBy(id = "password")
	public static WebElement password;

	@FindBy(id = "confirmPassword")
	public static WebElement confirmPassword;

	@FindBy(xpath = "//input[@name='role' and @value='USER']")
	public static WebElement roleUser;

	@FindBy(xpath = "//input[@name='role' and @value='SUPPLIER']")
	public static WebElement roleSupplier;

	@FindBy(id = "firstName.errors")
	public static WebElement firstNameError;

	@FindBy(id = "lastName.errors")
	public static WebElement lastNameError;

	@FindBy(id = "email.errors")
	public static WebElement emailError;

	@FindBy(id = "contactNumber.errors")
	public static WebElement contactNumberError;

	@FindBy(id = "password.errors")
	public static WebElement passwordError;

	@FindBy(name = "_eventId_billing")
	public static WebElement nextBillingButton;

	// ******** Address *********

	@FindBy(id = "addressLineOne")
	public static WebElement addressLineOne;

	@FindBy(id = "addressLineTwo")
	public static WebElement addressLineTwo;

	@FindBy(id = "city")
	public static WebElement city;

	@FindBy(id = "postalCode")
	public static WebElement postalCode;

	@FindBy(id = "state")
	public static WebElement state;

	@FindBy(id = "country")
	public static WebElement country;

	@FindBy(name = "_eventId_personal")
	public static WebElement backPersonalButton;

	@FindBy(name = "_eventId_confirm")
	public static WebElement nextConfirmButton;

	@FindBy(id = "addressLineOne.errors")
	public static WebElement addressLineOneError;

	@FindBy(id = "addressLineTwo.errors")
	public static WebElement addressLineOneTwoError;

	@FindBy(id = "city.errors")
	public static WebElement cityError;

	@FindBy(id = "postalCode.errors")
	public static WebElement postalCodeError;

	@FindBy(id = "state.errors")
	public static WebElement stateError;

	@FindBy(id = "country.errors")
	public static WebElement countryError;

	// ******** Confirm *********

	@FindBy(xpath = "//div[@class='text-center']/a")
	public static WebElement confirmButton;

	@FindBy(xpath = "(//a[text()='Edit'])[1]")
	public static WebElement editPersonalDetailsButton;

	@FindBy(xpath = "(//a[text()='Edit'])[2]")
	public static WebElement editBillingAddressButton;

	@FindBy(xpath = "(//div[@class='text-center'])[1]")
	public static WebElement personalDetails;

	@FindBy(xpath = "(//div[@class='text-center'])[2]")
	public static WebElement billingAddress;

	// ******** Welcome *********

	// text "Welcome!"
	@FindBy(tagName = "h1")
	public static WebElement welcomePrompt;

	@FindBy(linkText = "Login Here")
	public static WebElement loginHereButton;

	public SignupPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public SignupPage fillInPersonalData(Person person) {
		pageLoad(5000);
		if (!person.getFirstName().isEmpty()) {
			firstName.sendKeys(person.getFirstName());
		}

		if (!person.getLastName().isEmpty()) {
			lastName.sendKeys(person.getLastName());
		}
		email.sendKeys(person.getEmail());
		contactNumber.sendKeys(person.getContactNumber());
		password.sendKeys(person.getPassword());
		confirmPassword.sendKeys(person.getPassword());

		if (person.getRole().equals(Role.USER)) {
			roleUser.click();
		} else {
			roleSupplier.click();
		}

		nextBillingButton.click();
		return this;
	}

	public SignupPage fillInAddress(Person person) {
		Address address = person.getAddress();
		addressLineOne.sendKeys(address.getAddressLineOne());
		addressLineTwo.sendKeys(address.getAddressLineTwo());
		city.sendKeys(address.getCity());
		postalCode.sendKeys(address.getPostalCode());
		state.sendKeys(address.getState());
		country.sendKeys(address.getCountry());

		nextConfirmButton.click();
		return this;
	}

	public SignupPage confirmData() {
		waitUntilInvisible("//div[@class='se-pre-con']");
		waitUntilVisible(confirmButton);

		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", confirmButton);
		waitUntilVisible(loginHereButton);
		return this;
	}

	public LoginPage login() {
		waitUntilInvisible("//div[@class='se-pre-con']");
		loginHereButton.click();
		return new LoginPage(driver);
	}
}