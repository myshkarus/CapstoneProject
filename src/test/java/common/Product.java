package common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
	private String name;
	private String brand;
	private String description;
	private BigDecimal unitPrice;
	private int quantity;
	private String image;
	private Category category;

	public Product() {}

	public Product(String name, String brand, String description, BigDecimal unitPrice, int quantity, String image, Category category){
		this.setName(name);
		this.setBrand(brand);
		this.setDescription(description);
		this.setUnitPrice(unitPrice);
		this.setQuantity(quantity);
		this.setImage(image);
		this.setCategory(category);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		unitPrice.setScale(2, RoundingMode.HALF_EVEN);
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
