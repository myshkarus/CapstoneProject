package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.Product;

public class ProductMngtPage extends BasePage {

	@FindBy(xpath = "//div[@class='panel-heading']//h4")
	public static WebElement pageHeading;

	@FindBy(id = "name")
	public static WebElement name;

	@FindBy(id = "brand")
	public static WebElement brand;

	@FindBy(id = "description")
	public static WebElement description;

	@FindBy(id = "unitPrice")
	public static WebElement unitPrice;

	@FindBy(id = "quantity")
	public static WebElement quantity;

	@FindBy(id = "file")
	public static WebElement file;

	@FindBy(id = "categoryId")
	public static WebElement category;

	@FindBy(xpath = "//button[contains(@class, 'btn')]")
	public static WebElement addCategoryButton;

	@FindBy(xpath = "//form[@id='product']//input[@type='submit']")
	public static WebElement saveProductButton;

	@FindBy(xpath = "//*[@id='myCategoryModal']//input[@id='name']")
	public static WebElement categoryName;

	@FindBy(xpath = "//*[@id='myCategoryModal']//textarea[@id='description']")
	public static WebElement categoryDesc;

	@FindBy(xpath = "//*[@id='myCategoryModal']//input[@type='submit']")
	public static WebElement saveCategoryButton;

	@FindBy(xpath = "//div[@class='alert alert-info fade in']")
	public static WebElement successMessage;

	@FindBy(xpath = "//div[@class='modal-header']//h4")
	public static WebElement modalFormTitle;

	@FindBy(xpath = "//table[@id='productsTable']//tbody//tr")
	public static List<WebElement> availableProducts;

	@FindBy(xpath = "li[@class='paginate_button next']//a")
	public static WebElement activeNextPageButton;

	@FindBy(xpath = "//li[@class='paginate_button next disabled']//a")
	public static List<WebElement> disabledNextPageButton;

	@FindBy(xpath = "//li[@class='paginate_button active']")
	public static WebElement activePageButton;

	@FindBy(xpath = "//select[@name='productsTable_length']")
	public static WebElement entriesNumber;

	public ProductMngtPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public ProductMngtPage addNewCategory() {
		waitUntilInvisible("//div[@class='se-pre-con']");
		addCategoryButton.click();
		return this;
	}

	public ProductMngtPage enterCategoryData(String name, String description) {
		waitUntilVisible(modalFormTitle);

		categoryName.sendKeys(name);
		if (!description.equals("")) {
			categoryDesc.sendKeys(description);
		}
		return this;
	}

	public ProductMngtPage saveCategory() {
		saveCategoryButton.click();
		return this;
	}

	public ProductMngtPage saveProduct() {
		saveProductButton.click();
		return this;
	}

	public ProductMngtPage uploadFile(String fileName) {
		file.sendKeys(fileName);
		return this;
	}

	public Boolean categoryExist(String categoryName) {
		Boolean exist = false;
		List<WebElement> options = new Select(category).getOptions();
		for(WebElement opt: options) {
			if (opt.getText().equals(categoryName)) {
				exist = true;
			}
		}
		return exist;
	}

	public ProductMngtPage selectCategory(String categoryName) {
		Select dropdown = new Select(category);
		dropdown.selectByVisibleText(categoryName);
		return this;
	}

	public ProductMngtPage addNewProductData(Product product) {
		name.sendKeys(product.getName());
		brand.sendKeys(product.getBrand());
		description.sendKeys(product.getDescription());
		unitPrice.clear();
		unitPrice.sendKeys(product.getUnitPrice().toString());
		quantity.clear();
		quantity.sendKeys(Integer.toString(product.getQuantity()));
		return this;
	}

	public List<List<String>> getAvailableProducts() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		List<List<String>> products = new ArrayList<>();
		WebElement we = null;
		Boolean next = true;

		Select dropdown = new Select(entriesNumber);
		dropdown.selectByVisibleText("10 Records");

		while (next) {
			next = false;
			for (int i = 0; i < availableProducts.size(); i++) {
				List<String> product = new ArrayList<String>();

				product.add(availableProducts.get(i).findElement(By.xpath("td[3]")).getText());
				product.add(availableProducts.get(i).findElement(By.xpath("td[4]")).getText());
				product.add(availableProducts.get(i).findElement(By.xpath("td[5]")).getText());
				product.add(availableProducts.get(i).findElement(By.xpath("td[6]")).getText().replace("₹ ", ""));
				products.add(product);
			}

			List<WebElement> check = disabledNextPageButton;
			if (check.isEmpty()) {
				next = true;
				we = driver.findElement(By.xpath("//li[@class='paginate_button next']//a"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
				we.click();
				wait.until(ExpectedConditions.stalenessOf(we));
			}
			;
		}
		return products;
	}
}
