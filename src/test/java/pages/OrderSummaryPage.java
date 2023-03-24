package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderSummaryPage extends BasePage {

	@FindBy(xpath = "//div[@class='alert alert-success']//h3")
	public static WebElement orderConfirmMessage;

	@FindBy(xpath = "//table[@class='table table-condensed']//tbody//tr")
	private List<WebElement> table;

	public OrderSummaryPage(WebDriver basedriver) {
		super(basedriver);
		PageFactory.initElements(basedriver, this);
	}

	public String successPrompt() {
		waitUntilVisible(orderConfirmMessage);
		return orderConfirmMessage.getText();
	}

	public List<String> productsOrdered(int index) {
		List<String> products = new ArrayList<>();

		String productName = table.get(index).findElement(By.xpath("td")).getText();
		String price = table.get(index).findElement(By.xpath("td[2]")).getText().replace("₹ ", "");
		Double productPrice = Double.parseDouble(price);
		String productQty = table.get(index).findElement(By.xpath("td[3]")).getText();

		products = Arrays.asList(productName, productQty, Integer.toString(productPrice.intValue()));
		return products;
	}

	public List<String> totals() {
		List<String> orderTotals = new ArrayList<>();

		for(WebElement entry: table) {
			String total = entry.findElement(By.xpath("td[4]")).getText().replace("₹ ", "");
			Double totalPrice = Double.parseDouble(total);
			orderTotals.add(Integer.toString(totalPrice.intValue()));
		}
		return orderTotals;
	}
}
