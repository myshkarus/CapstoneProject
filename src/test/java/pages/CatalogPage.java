package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CatalogPage extends BasePage {

	@FindBy(xpath = "//select[@name='productListTable_length']")
	private WebElement showEntries;

	@FindBy(xpath = "//table[@id='productListTable']//tbody//tr")
	private List<WebElement> productLine;

	public CatalogPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public CatalogPage showAllProducts() {
		Select showDropdown = new Select(showEntries);
		showDropdown.selectByVisibleText("ALL");
		return this;
	}

	public Integer productAvailability(String productName) {
		WebElement product;
		waitUntilInvisible("//div[@class='se-pre-con']");
		String qtyAvailable = "";
		int availability;

		for (WebElement we : productLine) {
			product = we.findElement(By.xpath("td[2]"));
			String name = product.getText();
			if (name.equals(productName)) {
				qtyAvailable = product.findElement(By.xpath("parent::tr//td[5]")).getText();
				break;
			}
		}
		availability = Integer.valueOf(qtyAvailable);
		return availability;
	}

	public CartPage selectProduct(String productName) {
		WebElement productToCartButton = null;
		WebElement product;
		String name;

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
