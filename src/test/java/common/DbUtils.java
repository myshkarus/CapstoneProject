package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import dnl.utils.text.table.TextTable;

public class DbUtils {

	Connection con;
	Statement stm;
	String cartLine;
	String orderItem;
	String userDetail;
	String orderDetail;
	String cart;
	String product;
	String category;
	String address;
	String dropFKOrderItem;
	String dropFKCartline;
	String dropFKOrderDetail;
	String dropFKCart;
	String dropFKAddress;
	String addFKOrderItem;
	String addFKOrderDetail;
	String addFKAddress;
	String addFKCart;
	String addFKCartline;

	public DbUtils() throws SQLException, ClassNotFoundException {
		this.openConnection();
		this.initTables();
		this.initConstraintString();
	}

	private void initTables() {
		cartLine = common.Constants.DBTables.cartLine;
		orderItem = common.Constants.DBTables.orderItem;
		userDetail = common.Constants.DBTables.userDetail;
		orderDetail = common.Constants.DBTables.orderDetail;
		cart = common.Constants.DBTables.cart;
		product = common.Constants.DBTables.product;
		category = common.Constants.DBTables.category;
		address = common.Constants.DBTables.address;
	}

	private void initConstraintString() {
		dropFKOrderItem = common.Constants.DBConstraints.dropFKOrderItemOrderIdConstraint;
		dropFKCartline = common.Constants.DBConstraints.dropFKCartlineProductIdConstraint;
		dropFKOrderDetail = common.Constants.DBConstraints.dropFKOrderDetailBillingIdConstraint;
		dropFKCart = common.Constants.DBConstraints.dropFKCartUserIdConstraint;
		dropFKAddress = common.Constants.DBConstraints.dropFKAddressUserIdConstraint;
		addFKOrderItem = common.Constants.DBConstraints.addFKOrderItemOrderIdConstraint;
		addFKOrderDetail = common.Constants.DBConstraints.addFKOrderDetailBillingIdConstraint;
		addFKAddress = common.Constants.DBConstraints.addFKAddressUserIdConstraint;
		addFKCart = common.Constants.DBConstraints.addFKCartUserIdConstraint;
		addFKCartline = common.Constants.DBConstraints.addFKCartlineProductIdConstraint;
	}

	public void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.con = DriverManager.getConnection(common.Constants.connection_string, common.Constants.db_user,
				common.Constants.db_password);
		this.stm = con.createStatement();
	}

	public void closeConnection() throws SQLException {
		this.stm.close();
		this.con.close();
	}

	public ResultSet readTable(String table, String... values) throws SQLException {
		ResultSet result;
		String[] val = values;

		if (val.length == 0) {
			result = this.stm.executeQuery("SELECT * FROM " + table);
		} else {
			result = this.stm
					.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s'", val[0], table, val[1], val[2]));
		}
		return result;
	}

	private String userID(String email) throws SQLException {
		String id = "";
		ResultSet result = this.stm.executeQuery(
				String.format("SELECT id FROM %s WHERE %s='%s'", userDetail, "email", email));
		if (result.next()) {
			id = result.getString("id");
		}
		return id;
	}

	private void deleteData(String table, String columnName, String value) throws SQLException {
		this.stm.executeUpdate(String.format("DELETE FROM %s where %s='%s'", table, columnName, value));
	}

	public void printResultSet(ResultSet r) throws SQLException {
		ResultSetMetaData meta = r.getMetaData();
		int colCount = meta.getColumnCount();

		List<String> colNames = new LinkedList<String>();
		List<List<String>> rowList = new LinkedList<List<String>>();

		for (int col = 1; col <= colCount; ++col) {
			String colName = meta.getColumnName(col);
			colNames.add(colName);
		}

		while (r.next()) {
			List<String> colList = new LinkedList<String>();
			rowList.add(colList);

			for (int col = 1; col <= colCount; ++col) {
				Object value = r.getObject(col);
				colList.add(String.valueOf(value));
			}
		}

		String[] columns = colNames.toArray(new String[0]);
		Object[][] data = new Object[rowList.size()][];
		for (int i = 0; i < rowList.size(); i++) {
			data[i] = rowList.get(i).toArray();
		}

		System.out.println("\nTable: " + meta.getTableName(1).toUpperCase());
		TextTable tt = new TextTable(columns, data);
		tt.printTable();
	}

	private String orderItemID(String email) throws SQLException {
		String id = "";
		String query = String.format(
				"SELECT DISTINCT %s.order_id FROM %s\n"
		      + "INNER JOIN %s ON %s.user_id=%s.id\n"
			  + "INNER JOIN %s ON %s.order_id=%s.id\n"
		      + "WHERE %s.email='%s'", orderItem, userDetail, orderDetail, orderDetail,
		                               userDetail, orderItem, orderItem, orderDetail, userDetail, email
		      );

		ResultSet result = this.stm.executeQuery(query);

		if (result.next()) {
			id = result.getString("order_id");
		}
		return id;
	}

	private String cartLineID(String email) throws SQLException {
		String id = "";
		String query = String.format(
				"SELECT DISTINCT %s.cart_id FROM %s\n"
		      + "INNER JOIN %s ON %s.user_id = %s.id\n"
			  + "INNER JOIN %s ON %s.cart_id = %s.id\n"
		      + "WHERE %s.email = '%s'", cartLine, userDetail, cart, cart, userDetail,
				                         cartLine, cartLine, cart, userDetail, email
			   );

		ResultSet result = this.stm.executeQuery(query);

		if (result.next()) {
			id = result.getString("cart_id");
		}
		return id;
	}

	public ResultSet executeQuery(String query) throws SQLException {
		ResultSet rst = null;
		rst = this.stm.executeQuery(query);
		return rst;
	}

	public void cleanTestData(String email) throws SQLException {
		try {
			this.stm.execute(dropFKOrderItem);
			this.stm.execute(dropFKCartline);
			this.stm.execute(dropFKOrderDetail);
			this.stm.execute(dropFKCart);
			this.stm.execute(dropFKAddress);

			String id = userID(email);
			if (!id.isEmpty()) {
				String oIId = orderItemID(email);
				String clId = cartLineID(email);

				if (!oIId.isEmpty()) {
					deleteData(common.Constants.DBTables.orderItem, "order_id", oIId);
				}

				if (!clId.isEmpty()) {
					deleteData(common.Constants.DBTables.cartLine, "cart_id", clId);
				}

				deleteData(orderDetail, "user_id", id);
				deleteData(cart, "user_id", id);
				deleteData(address, "user_id", id);
				deleteData(userDetail, "id", id);
			}
			this.stm.execute(String.format("DELETE FROM %s where %s like '%s'", product, "name", "Test%"));
			this.stm.execute(String.format("DELETE FROM %s where %s like '%s'", category, "name", "Test%"));

		} catch (SQLException e) {

		} finally {
			this.stm.execute(addFKOrderItem);
			this.stm.execute(addFKOrderDetail);
			this.stm.execute(addFKAddress);
			this.stm.execute(addFKCart);
			this.stm.execute(addFKCartline);
		}
	}
}
