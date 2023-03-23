package common;

import java.math.BigDecimal;

import common.Person.Role;

public class Constants {

	private static final String host = "http://localhost";
	private static final int port = 8080;

	public static final String baseURL = host + ":" + port + "/medicare";
	public static final String db_user = "root";
	public static final String db_password = "root";

	public static final String host_db = "//localhost";
	public static final int db_port = 3306;
	public static final String db_name = "medicare";
	public static final String connection_string = "jdbc:mysql:" + host_db + ":" + db_port + "/" + db_name;

	public static Address user_address = new Address("Street 131N", "Main Ave", "Scranton", "18504", "Pennsylvania",
			"United States");
	public static Person user = new Person("Hannah", "Ford", "hannah.ford@domain.com", "8975937680", "ze±!_0&~a[].Edr",
			Role.USER, user_address);

	public static Address supplier_address = new Address("381 St-Jerome Street", "building 2-3", "St Jerome", "S4P3Y2",
			"QC", "Canada");
	public static Person supplier = new Person("Evelyn", "Sorrells", "evelyn.sorrels@hotmail.com", "4504322072",
			"secretPassword123#", Role.SUPPLIER, supplier_address);

	public static User admin = new User("Vikas", "Kashyap", "vk@gmail.com", "admin");
	public static User registered_user = new User("Kavita", "Nigam", "kn@gmail.com", "12345");

	public static CreditCard card = new CreditCard("4123450131003312", "12", "29", "123");

	public static Category testCategory = new Category("Testiotics",
			"This is a test category of brand new test medicine");
	public static Product testProduct = new Product("Testofenac", "Testbrand", "A test medicine for testing purpose",
			new BigDecimal("35.70"), 50, "medicine.png", testCategory);



	public static class Endpoints {
		// private static String home = "/home";
		// private static String login = "/login";
		// private static String membership = "/membership";

	}

	public static class DBTables {
		public static String address = "Address";
		public static String cart = "Cart";
		public static String cartLine = "Cart_line";
		public static String category = "Category";
		public static String orderDetail = "Order_detail";
		public static String orderItem = "Order_item";
		public static String product = "Product";
		public static String userDetail = "User_detail";
	}

	public static class DBConstraints {

		public static String dropFKAddressUserIdConstraint = String.format("ALTER TABLE %s DROP FOREIGN KEY %s",
				DBTables.address, "fk_address_user_id");

		public static String addFKAddressUserIdConstraint = String.format(
				"ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)", DBTables.address,
				"fk_address_user_id", "user_id", DBTables.userDetail, "id");

		public static String dropFKCartlineProductIdConstraint = String.format("ALTER TABLE %s DROP FOREIGN KEY %s",
				DBTables.cartLine, "fk_cartline_product_id");

		public static String addFKCartlineProductIdConstraint = String.format(
				"ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)", DBTables.cartLine,
				"fk_cartline_product_id", "product_id", DBTables.product, "id");

		public static String dropFKCartUserIdConstraint = String.format("ALTER TABLE %s DROP FOREIGN KEY %s",
				DBTables.cart, "fk_cart_user_id");

		public static String addFKCartUserIdConstraint = String.format(
				"ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)", DBTables.cart, "fk_cart_user_id",
				"user_id", DBTables.userDetail, "id");

		public static String dropFKOrderDetailBillingIdConstraint = String.format("ALTER TABLE %s DROP FOREIGN KEY %s",
				DBTables.orderDetail, "fk_order_detail_billing_id");

		public static String addFKOrderDetailBillingIdConstraint = String.format(
				"ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)", DBTables.orderDetail,
				"fk_order_detail_billing_id", "billing_id", DBTables.address, "id");

		public static String dropFKOrderItemOrderIdConstraint = String.format("ALTER TABLE %s DROP FOREIGN KEY %s",
				DBTables.orderItem, "fk_order_item_order_id");

		public static String addFKOrderItemOrderIdConstraint = String.format(
				"ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)", DBTables.orderItem,
				"fk_order_item_order_id", "order_id", DBTables.orderDetail, "id");
	}
}
