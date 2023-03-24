package stepdefs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;

import common.Person;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.NavigatePanel;
import pages.SignupPage;
import runner.TestRunner;

public class SignupStepDefs extends TestRunner {
	SignupPage page;
	LoginPage loginPage;
	NavigatePanel panel;
	Person testUser;

	String missedPart;

	@Given("I go to Sign up page")
	public void i_go_to_sign_up_page() {
		panel = new NavigatePanel(driver);
		page = panel.gotoSignupPage(driver);
	}

	@Given("I have a {string}")
	public void i_have_a(String role) {
		testUser = new Person();
		switch (role) {
		case "user":
			testUser = common.Constants.user;
			break;
		case "supplier":
			testUser = common.Constants.supplier;
			break;
		}
	}

	@Given("I am a user")
	public void i_am_a_user() {
		testUser = new Person();
		testUser = common.Constants.user;
	}

	@When("I enter my personal data")
	public void i_enter_my_personal_data() {
		page.fillInPersonalData(testUser);
	}

	@When("I enter my address")
	public void i_enter_my_address() {
		page.fillInAddress(testUser);
	}

	@When("I submit my data")
	public void i_submit_my_data() {
		page.confirmData();
	}

	@When("I see {string} message on web app and click Login")
	public void i_see_message_on_web_app_and_click_login(String message) {
		Assert.assertEquals(SignupPage.welcomePrompt.getText(), message);
		loginPage = page.login();
	}

	@When("I enter my credentials on Login form")
	public void i_enter_my_credentials_on_login_form() {
		loginPage.enterCredentials(testUser.getEmail(), testUser.getPassword());
	}

	@When("I click on Login button on Login form")
	public void i_click_on_login_button_on_login_form() {
		loginPage.login();
	}

	@When("I missed to enter my {string} or {string}")
	public void i_missed_to_enter_my_or(String firstName, String lastName) {
		if (firstName.isEmpty()) {
			testUser.setFirstName(firstName);
			missedPart = "firstName";
		} else {
			testUser.setLastName(lastName);
			missedPart = "lastName";
		}
		page.fillInPersonalData(testUser);
	}

	@Then("I should see my name in navigate menu")
	public void i_should_see_my_name_in_navigate_menu() {
		Assert.assertEquals(NavigatePanel.accountDropdown.getText(), testUser.getFullName());
	}

	@Then("I should see {string} message under corresponding field")
	public void i_should_see_message_under_corresponding_field(String errorMessage) {
		if (missedPart.equals("firstName")) {
			Assert.assertEquals(SignupPage.firstNameError.getText(), errorMessage);
		} else {
			Assert.assertEquals(SignupPage.lastNameError.getText(), errorMessage);
		}
	}

	@Then("I can find my personal data and address in database")
	public void i_can_find_my_personal_data_and_address_in_database() throws SQLException {
		ResultSet rst;
		String query = String.format(
						"SELECT * FROM %s\n"
				      + "INNER JOIN %s ON %s.user_id=%s.id\n"
					  + "WHERE %s.email='%s'", common.Constants.DBTables.userDetail,
				                               common.Constants.DBTables.address,
				                               common.Constants.DBTables.address,
				                               common.Constants.DBTables.userDetail,
				                               common.Constants.DBTables.userDetail,
				                               testUser.getEmail()
				      );

		rst = db.executeQuery(query);
		Assert.assertTrue(rst != null);

		while (rst.next()) {
			Assert.assertEquals(rst.getString("first_name"), testUser.getFirstName());
			Assert.assertEquals(rst.getString("last_name"), testUser.getLastName());
			Assert.assertEquals(rst.getString("email"), testUser.getEmail());
			Assert.assertEquals(rst.getString("contact_number"), testUser.getContactNumber());
			Assert.assertEquals(rst.getString("country"), testUser.getAddress().getCountry());
			Assert.assertEquals(rst.getString("postal_code"), testUser.getAddress().getPostalCode());
		}
	}
}
