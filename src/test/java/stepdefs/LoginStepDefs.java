package stepdefs;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.NavigatePanel;
import runner.TestRunner;

public class LoginStepDefs extends TestRunner {
	LoginPage page;
	NavigatePanel panel;
	String email = "";
	String password = "";

	@Given("I have launched the application")
	public void i_have_launched_the_application() {
		driver.get(common.Constants.baseURL);
	}

	@Given("I go to Login page")
	public void i_go_to_Login_page() {
		panel = new NavigatePanel(driver);
		page = panel.gotoLoginPage(driver);
	}

	@When("I enter valid email and password")
	public void i_enter_valid_email_and_password() {
		page.enterCredentials(email, password);
	}

	@When("I click on Login button")
	public void i_click_on_login_button() {
		page.login();
	}

	@Given("I am end-user")
	public void i_am_end_user() {
		email = common.Constants.registered_user.getEmail();
		password = common.Constants.registered_user.getPassword();
	}

	@When("I enter invalid email as {string}, password as {string}")
	public void i_enter_invalid_email_as_password_as(String email, String password) {
		page.enterCredentials(email, password);
	}

	@Then("I should see home page and my name in navigate menu")
	public void i_should_see_home_page_and_my_name_in_navigate_menu() {
		Assert.assertEquals(NavigatePanel.accountDropdown.getText(), common.Constants.registered_user.getFullName());
	}

	@Then("I should get the error message {string}")
	public void i_should_get_the_error_message(String message) {
		Assert.assertEquals(LoginPage.alertDanger.getText(), message);
	}

	@Given("I am an admin")
	public void i_am_an_admin() {
	    email = common.Constants.admin.getEmail();
	    password = common.Constants.admin.getPassword();
	}

	@Then("I should see {string} option in admin portal")
	public void i_should_see_option_in_admin_portal(String optionTitle) {
		Assert.assertEquals(NavigatePanel.linkButtonManageProducts.getText(), optionTitle);
	}
}
