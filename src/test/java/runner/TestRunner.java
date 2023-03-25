package runner;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import db.DbUtils;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(monochrome = true, features = "src/test/java/features", glue = "stepdefs", plugin = { "pretty",
		"json:target/cucumber.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}, tags = ("@sanity"))

//publish =true
public class TestRunner extends AbstractTestNGCucumberTests {

	public static WebDriver driver;
	public static DbUtils db;

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	public void cleanDB() throws ClassNotFoundException, SQLException {
		System.out.println("\n" + StringUtils.repeat("=", 47));
		if (db != null) {
			db.cleanTestData(common.Constants.user.getEmail());
			db.cleanTestData(common.Constants.supplier.getEmail());
			System.out.println("TEST DATA CLEANUP COMPLETE");
		}
		System.out.println(StringUtils.repeat("=", 47) + "\n");
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteSetup() {

	}

	@AfterSuite(alwaysRun = true)
	public void afterSuiteSetup() throws ClassNotFoundException, SQLException {

	}

	@BeforeMethod(alwaysRun = true)
	public void setup() throws ClassNotFoundException, SQLException {
		db = new DbUtils();
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("--headless");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		deleteAllCookies();
	}

	@AfterMethod(alwaysRun = true)
	public void TearDown() throws InterruptedException, ClassNotFoundException, SQLException {
		cleanDB();
		db.closeConnection();
		driver.close();
	}
}
