package stepdefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import common.Person;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.CatalogPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.NavigatePanel;
import pages.OrderSummaryPage;
import pages.SignupPage;
import runner.TestRunner;

public class ProductOrderingStepDefs extends TestRunner {

	LoginPage loginPage;
	SignupPage signUpPage;
	CatalogPage catalog;
	CartPage cart;
	CheckoutPage checkout;
	Person user;
	List<Integer> initialQtyAvailable;

	@Given("I have account registered on web app")
	public void i_have_account_registered_on_web_app() {
		user = common.Constants.user;

		NavigatePanel panel = new NavigatePanel(driver);
		signUpPage = panel.gotoSignupPage(driver);
		signUpPage.fillInPersonalData(user);
		signUpPage.fillInAddress(user);
		signUpPage.confirmData();
		signUpPage.login();
	}

	@Given("I logged in as user")
	public void i_logged_in_as_an_end_user() {
		loginPage = new LoginPage(driver);
		loginPage.enterCredentials(user.getEmail(), user.getPassword());
		loginPage.login();
	}

	@Given("I am on Catalog page")
	public void i_am_on_catalog_page() {
		NavigatePanel panel = new NavigatePanel(driver);
		CatalogPage catalogPage = panel.gotoCatalogPage(driver);
		catalogPage.showAllProducts();
	}

	@Given("I put products to the cart")
	public void i_put_products_to_the_cart(List<String> products) {
		initialQtyAvailable = new ArrayList<Integer>();
		int productNumber = products.size();

		for (int i = 0; i < productNumber; i++) {
			CatalogPage catalogPage = new CatalogPage(driver);
			initialQtyAvailable.add(catalogPage.productAvailability(products.get(i)));
			cart = catalogPage.selectProduct(products.get(i));

			Assert.assertEquals(CartPage.cartAlertMessage.getText(),
					"Product has been successfully added inside cart!");

			if (i < (productNumber - 1)) {
				cart.continueShopping();
			}
		}
	}

	@Given("I change product quantity")
	public void i_change_product_quantity(io.cucumber.datatable.DataTable quantities) {
		List<Map<String, String>> rows = quantities.asMaps(String.class, String.class);

		for (Map<String, String> columns : rows) {
			CartPage cartPage = new CartPage(driver);
			cartPage.changeProductQuantity(columns.get("product"), columns.get("qty"));

			Assert.assertEquals(CartPage.cartAlertMessage.getText(), "Cart has been updated successfully!");
		}
	}

	@Given("I choose shipping address on Checkout page")
	public void i_choose_shipping_address_on_checkout_page() {
		checkout = cart.gotoCheckout();
		checkout.selectShippingAddress();
		Assert.assertEquals(CheckoutPage.payButton.getText(), "Pay");
	}

	@When("I enter credit card data")
	public void i_enter_credit_card_data() {
		checkout.enterCreditCardData(common.Constants.card);
	}

	@When("I click Pay button")
	public void i_click_pay_button() {
		checkout.makePayment();
	}

	@Then("I should see Order summary with products name, their price, and quantity")
	public void i_should_see_order_summary_with_products_name_and_their_quantity(io.cucumber.datatable.DataTable data) {
		OrderSummaryPage osp = new OrderSummaryPage(driver);
		Assert.assertEquals(osp.successPrompt(), "Your Order is Confirmed!!");
		List<List<String>> lines = data.asLists(String.class);

		for (int i = 1; i < lines.size(); i++) {
			Assert.assertEquals(lines.toArray()[i], osp.productsOrdered(i - 1));
		}
	}

	@Then("I should see Total price of products")
	public void i_should_see_total_price_of_products(io.cucumber.datatable.DataTable data) {
		OrderSummaryPage osp = new OrderSummaryPage(driver);
		List<String> totals = osp.totals();
		List<List<String>> lines = data.asLists(String.class);

		for (int i = 1; i < lines.size(); i++) {
			Assert.assertEquals(totals.get(i-1), lines.get(i).get(1));
		}
	}

	@Then("I should see product availability is reduced by quantity ordered")
	public void i_should_see_product_availability_is_reduced_by_quantity_ordered(io.cucumber.datatable.DataTable data) {
		NavigatePanel panel = new NavigatePanel(driver);
		panel.gotoHomePage(driver);
		CatalogPage catalogPage = panel.gotoCatalogPage(driver);
		catalogPage.showAllProducts();

		Integer actualAvailability;

		List<List<String>> products = data.asLists(String.class);

		for (int i = 1; i < products.size(); i++) {
			actualAvailability = catalogPage.productAvailability(products.get(i).get(0));
			Assert.assertEquals(initialQtyAvailable.get(i-1) - actualAvailability, Integer.valueOf(products.get(i).get(1)));
		}
	}
}
