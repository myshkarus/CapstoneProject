package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {

	@FindBy(tagName = "h3")
	WebElement productName;

	@FindBy(xpath = "//h4//*[1]")
	WebElement productPrice;

	@FindBy(tagName = "img")
	WebElement productImage;

	@FindBy(tagName = "h6")
	WebElement productQuantity;

	@FindBy(xpath = "//a[contains(@class, 'btn-success')]")
	WebElement addToCartButton;

	@FindBy(xpath = "//a[contains(@class, 'btn-warning')]")
	WebElement continueShoppingButton;

	public ProductPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}
}
