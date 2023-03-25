package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	@FindBy(xpath="//div[@class='list-group']//a")
	public static List<WebElement> categories;

	@FindBy(xpath = "//div[contains(@ng-repeat, 'mvProducts')]")
	public static List<WebElement> mostViewedProducts;

	@FindBy(xpath = "//div[contains(@ng-repeat, 'mpProducts')]")
	public static List<WebElement> mostPurchasedProducts;

	public HomePage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public List<String> categoryList() {
		List<String> list = new ArrayList<>();
		for(WebElement category: categories) {
			list.add(category.getText());
		}
		return list;
	}

	public List<String> getMostViewedProducts(){
		List<String> list = new ArrayList<String>();
		for(WebElement we: mostViewedProducts) {
			String productName = we.findElement(By.xpath(".//h5")).getText();
			list.add(productName);
		}
		return list;
	}

	public List<String> getMostPurchasedProducts(){
		List<String> list = new ArrayList<String>();
		for(WebElement we: mostPurchasedProducts) {
			String productName = we.findElement(By.xpath(".//h5")).getText();
			list.add(productName);
		}
		return list;
	}
}
