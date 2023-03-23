package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import runner.TestRunner;

public abstract class BasePage extends TestRunner {
	WebDriver driver;
	WebDriverWait wait;

	public BasePage(WebDriver basedriver) {
		this.driver = basedriver;
		this.wait = new WebDriverWait(driver, 100);
	}

	public void waitUntilVisible(WebElement element) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitUntilInvisible(String locator) {
		wait = new WebDriverWait(driver, 2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
	}

	public void waitUntilClickable(WebElement element) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void pageLoad(int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.MILLISECONDS);
	}
}
