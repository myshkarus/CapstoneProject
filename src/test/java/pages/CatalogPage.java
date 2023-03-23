package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CatalogPage extends BasePage {

	@FindBy(xpath = "//table[@id='productListTable']//tbody//tr")
	private List<WebElement> productLine;

	public CatalogPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public CartPage selectProduct(String productName) {
		WebElement productToCartButton = null;
		String name;
		WebElement product;

		waitUntilInvisible("//div[@class='se-pre-con']");

		for (WebElement we : productLine) {
			product = we.findElement(By.xpath("td[2]"));
			name = product.getText();
			if (name.equals(productName)) {
				productToCartButton = we;
				break;
			}
		}

		if (productToCartButton != null) {
			productToCartButton.findElement(By.xpath("td//following-sibling::td/a[@class='btn btn-success']")).click();
		}
		return new CartPage(driver);
	}
}
