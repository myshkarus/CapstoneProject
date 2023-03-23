package stepdefs;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import common.Category;
import common.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;
import pages.NavigatePanel;
import pages.ProductMngtPage;
import runner.TestRunner;

public class AddProductStepDefs extends TestRunner {
	NavigatePanel panel;
	ProductMngtPage page;
	Category category;
	Product product;

	@Given("I logged in as admin")
	public void i_logged_in_as_admin() {
		LoginPage loginPage = new LoginPage(driver);
		panel = new NavigatePanel(driver);
		loginPage = panel.gotoLoginPage(driver);
		loginPage.enterCredentials(common.Constants.admin.getEmail(), common.Constants.admin.getPassword());
		loginPage.login();
	}

	@Given("I am on product management page")
	public void i_am_on_product_management_page() {
		page = panel.gotoProductMngtPage(driver);
		Assert.assertEquals(ProductMngtPage.pageHeading.getText(), "Product Management");
	}

	@When("I click Add New Category button")
	public void i_click_add_new_category_button() {
		page.addNewCategory();
	}

	@When("I enter new category name as {string} and category description as {string}")
	public void i_enter_new_product_with_a_name_as_and_a_description_as(String name, String description) {
		category = new Category(name, description);
		page.enterCategoryData(name, description);
	}

	@When("I save new category")
	public void i_save_new_category() {
		page.saveCategory();

		Assert.assertEquals(ProductMngtPage.successMessage.getText(), "Category submitted successfully!");
	}

	@Then("I should see new category in Categories list on home page")
	public void i_should_see_new_category_in_categories_list_on_home_page() {
		HomePage homePage;
		homePage = panel.gotoHomePage(driver);
		Assert.assertTrue(homePage.categoryList().contains(category.getName()));
	}

	@When("I enter new product with a name as {string}, brand as {string}, description as {string}, unit price as {string} and quantity as {int}")
	public void i_enter_new_product_with_a_name_as_brand_as_description_as_unit_price_as_and_quantity_as(String name,
			String brand, String description, String unitPrice, int quantity) {
		product = new Product();
		product.setName(name);
		product.setBrand(brand);
		product.setDescription(description);
		product.setUnitPrice(new BigDecimal(unitPrice));
		product.setQuantity(quantity);
		page.addNewProductData(product);
	}

	@When("I upload a product image as {string}")
	public void i_upload_a_product_image_as(String imageFile) throws URISyntaxException {
		Path resourceDirectory = Paths.get("src", "test", "java", "resources", imageFile);
		String filePath = resourceDirectory.toFile().getAbsolutePath();
		product.setImage(filePath);
		page.uploadFile(filePath);
	}

	@Given("There is a product category {string} with description as {string}")
	public void there_is_a_category_in_the_category_list(String categoryName, String description) {
		// if the category list doesn't contain test category
		if (!page.categoryExist(categoryName)) {
			i_click_add_new_category_button();
			i_enter_new_product_with_a_name_as_and_a_description_as(categoryName, description);
			i_save_new_category();
			Assert.assertEquals(ProductMngtPage.successMessage.getText(), "Category submitted successfully!");
		}
	}

	@When("I select product category as {string}")
	public void i_select_product_category_as(String categoryName) {
		page.selectCategory(categoryName);
	}

	@Given("I save new product")
	public void i_save_new_product() {
		page.saveProduct();
		Assert.assertEquals(ProductMngtPage.successMessage.getText(), "Product submitted successfully!");
	}

	@Then("I should see product in list of available product")
	public void i_should_see_product_in_list_of_available_product() throws InterruptedException {
		List<List<String>> availableProducts = new ArrayList<>();
		List<String> testProduct = Arrays.asList(product.getName(), product.getBrand(),
				Integer.toString(product.getQuantity()), Integer.toString(product.getUnitPrice().intValue()));

		availableProducts = page.getAvailableProducts();
		Assert.assertTrue(availableProducts.contains(testProduct));
	}

	@Then("product should be stored in database")
	public void product_should_be_stored_in_database() throws SQLException {
		ResultSet rst;

		rst = db.readTable(common.Constants.DBTables.product, "*", "name", product.getName());
		Assert.assertTrue(rst != null);

		while (rst.next()) {
			Assert.assertEquals(rst.getString("name"), product.getName());
			Assert.assertEquals(rst.getString("brand"), product.getBrand());
			Assert.assertEquals(rst.getString("description"), product.getDescription());
			Assert.assertEquals((int) Double.parseDouble(rst.getString("unit_price")),
					product.getUnitPrice().intValue());
			Assert.assertEquals(rst.getInt("quantity"), product.getQuantity());
		}
	}
}
