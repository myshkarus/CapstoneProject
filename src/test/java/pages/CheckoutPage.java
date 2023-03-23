package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.CreditCard;



public class CheckoutPage extends BasePage {

	@FindBy(xpath = "//a[contains(@class, 'btn btn-primary')]")
	public static WebElement addressSelectButton;

	@FindBy(id = "cardNumber")
	public static WebElement cardNumber;

	@FindBy(id = "expityMonth")
	public static WebElement expiryMonth;

	@FindBy(id = "expityYear")
	public static WebElement expiryYear;

	@FindBy(id = "cvCode")
	public static WebElement cvv;

	@FindBy(xpath = "//a[@role='button']")
	public static WebElement payButton;

	public CheckoutPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public void selectShippingAddress(){
		waitUntilInvisible("//div[@class='se-pre-con']");

		waitUntilVisible(addressSelectButton);
		addressSelectButton.click();
	}

	public void enterCreditCardData(CreditCard card) {
		cardNumber.sendKeys(card.getNumber());
		expiryMonth.sendKeys(card.getExpiryMonth());
		expiryYear.sendKeys(card.getExpiryYear());
		cvv.sendKeys(card.getCvv());
	}

	public void makePayment() {
		payButton.click();
	}
}
