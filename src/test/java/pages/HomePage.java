package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	@FindBy(xpath="//div[@class='list-group']//a")
	public static List<WebElement> categories;

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
}
