package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage{

	@FindBy(xpath = "//tfoot//tr[@class='visible-xs']//strong")
	WebElement totalPrice;

	@FindBy(partialLinkText = "Continue Shopping")
	WebElement continueShoppingButton;

	@FindBy(partialLinkText = "Checkout")
	WebElement checkoutButton;

	@FindBy (xpath = "//div[contains(@class, 'alert-info')]//h3")
	public static WebElement cartAlertMessage;

	@FindBy(xpath = "//table[@id='cart']//tbody//tr")
	private List<WebElement> cartLines;


	public CartPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public CheckoutPage gotoCheckout() {
		waitUntilInvisible("//div[@class='se-pre-con']");
		checkoutButton.click();
		return new CheckoutPage(driver);
	}

	public void continueShopping() {
		waitUntilInvisible("//div[@class='se-pre-con']");
		continueShoppingButton.click();
	}

	public void changeProductQuantity(String productName, String qty) {
		WebElement productQty = null;
		WebElement qtyInput;
		String product;

		for (WebElement line : cartLines) {
			product = line.findElement(By.xpath("td//h4")).getText();
			if (product.equals(productName)) {
				productQty = line;
				break;
			}
		}

		if (productQty != null) {
			qtyInput = productQty.findElement(By.xpath("td//following-sibling::input"));
			qtyInput.clear();
			qtyInput.sendKeys(qty);

			productQty.findElement(By.xpath("td//following-sibling::button")).click();
		}
	}
}
