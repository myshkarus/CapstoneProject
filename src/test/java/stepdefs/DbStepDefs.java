package stepdefs;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import common.Category;
import common.Product;
import db.DbUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ProductMngtPage;
import runner.TestRunner;

public class DbStepDefs extends TestRunner {
	ProductMngtPage page;
	Category category;
	List<String> productList;
	DbUtils db;
	ResultSet rstCategory;
	ResultSet rstProduct;
	String cat;

	@Given("I add new category as {string} with description as {string}")
	public void i_add_new_category_as_with_description_as(String categoryName, String description) {
		page = new ProductMngtPage(driver);
		category = new Category(categoryName, description);
		page.addNewCategory();
		page.enterCategoryData(categoryName, description);
		page.saveCategory();
		Assert.assertEquals(ProductMngtPage.successMessage.getText(), "Category submitted successfully!");
	}

	@Given("I add new products")
	public void i_add_new_products(io.cucumber.datatable.DataTable products) {
		Path resourceDirectory = null;
		productList = new ArrayList<String>();
		List<List<String>> items = products.asLists(String.class);

		for (int i = 1; i < items.size(); i++) {
			Product product = new Product();

			for(int j = 0; j < items.get(i).size(); j++) {
				product.setName(items.get(i).get(1));
				product.setBrand(items.get(i).get(2));
				product.setDescription(items.get(i).get(3));
				product.setUnitPrice(new BigDecimal(items.get(i).get(4)));
				product.setQuantity(Integer.parseInt(items.get(i).get(5)));
				product.setImage(items.get(i).get(6));
				product.setCategory(category);

				resourceDirectory = Paths.get("src", "test", "java", "resources", items.get(i).get(6));
			}

			page.addNewProductData(product);
			String filePath = resourceDirectory.toFile().getAbsolutePath();
			page.uploadFile(filePath);
			page.selectCategory(product.getCategory().getName());
			page.saveProduct();

			Assert.assertEquals(ProductMngtPage.successMessage.getText(), "Product submitted successfully!");
			productList.add(product.getName());
		}
	}

	@When("I connect to database")
	public void i_connect_to_database() throws ClassNotFoundException, SQLException {
		db = new DbUtils();
	}

	@When("I trigger SELECT query to database tables {string} and {string}")
	public void i_trigger_select_query_to_database_tables_and(String productTable, String categoryTable) throws SQLException {
		String temp = "";

		String queryToCategoryTable = String.format("SELECT * FROM %s WHERE %s='%s'", categoryTable, "name", category.getName() );
		rstCategory = db.executeQuery(queryToCategoryTable);
		Assert.assertTrue(rstCategory != null);

		rstCategory.next();
		cat = rstCategory.getString("id");

		for(String p: productList) {
			temp = temp + "\"" + p + "\"" + ", ";
		}

		String queryToProductTable = String.format("SELECT * FROM %s WHERE %s IN (%s)", productTable, "name", temp.substring(0, temp.length()-2));
		rstProduct = db.executeQuery(queryToProductTable);
		Assert.assertTrue(rstProduct != null);
	}

	@Then("I should see field {string} in category table has relation one-to-many with field {string} in product table")
	public void i_should_see_field_in_table_has_relation_one_to_many_with_field_in_table(String idField, String categoryIdField) throws SQLException {

		while (rstProduct.next()) {
			Assert.assertEquals(rstProduct.getString("category_id"), cat);
		}
	}

}
