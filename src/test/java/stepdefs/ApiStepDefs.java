package stepdefs;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import api.ApiUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import runner.TestRunner;

public class ApiStepDefs extends TestRunner {
	ApiUtils api;
	String userName;
	String userPassword;

	@Given("base URL is {string}")
	public void base_url_is(String baseUrl) {
		driver.get(common.Constants.baseURL);
		userName = common.Constants.admin.getEmail();
		userPassword = common.Constants.admin.getPassword();
		api = new ApiUtils(baseUrl);
	}

	@Given("the API endpoint is {string}")
	public void the_api_endpoint_is(String endpoint) {
		api.setEndpoint(endpoint);
	}

	@When("I hit endpoint with GET request")
	public void i_hit_endpoint_with_get_request() {
		api.get();
	}

	@Then("I should see response status code {int}")
	public void i_should_see_response_status_code(int statusCode) {
		Assert.assertEquals(api.getStatusCode(), statusCode);
	}

	@Then("The list of {string} of the most viewed products is the same as on Home page of web application")
	public void the_list_of_the_most_viewed_products_is_the_same_as_on_home_page_of_web_application(String name) {
		HomePage homePage = new HomePage(driver);

		List<String> mostViewedProductsOnUI = homePage.getMostViewedProducts();

		Assert.assertEquals(mostViewedProductsOnUI, api.getAPIData(name));
	}

	@Then("cookie is {string}")
	public void cookie_is(String cookie) {
		Map<String, String> allCookies = api.getCookies();
		Assert.assertTrue(allCookies.containsKey(cookie));
	}

	@Then("The list of {string} of the most purchased products is the same as on Home page of web application")
	public void the_list_of_the_most_purchased_products_is_the_same_as_on_home_page_of_web_application(String name) {
		HomePage homePage = new HomePage(driver);
		List<String> mostViewedProductsOnUI = homePage.getMostPurchasedProducts();

		Assert.assertEquals(mostViewedProductsOnUI, api.getAPIData(name));
	}
}
