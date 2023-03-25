import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.testng.TestNG;

import db.DbUtils;

public class testdb {

	static DbUtils db;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		db = new DbUtils();
		String email = "hannah.ford@domain.com";
		boolean act = true;
		boolean print = false;

		if (print) {

			System.out.println("BEFORE TEST EXECUTION");
			System.out.println(StringUtils.repeat("*", 47) + "\n");
			printTables();

		}

		if (act == true) {
			System.out.println("\n" + StringUtils.repeat("*", 150));

			runTestNG();

			db.cleanTestData(email);

			if (print) {
				System.out.println("AFTER TEST EXECUTION");
				System.out.println(StringUtils.repeat("*", 47) + "\n");
				printTables();
			}

		}
	}

	static void printTables() throws ClassNotFoundException, SQLException {
		ResultSet rs;

		rs = db.readTable(common.Constants.DBTables.userDetail);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.address);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.orderDetail);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.orderItem);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.cart);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.cartLine);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.product);
		db.printResultSet(rs);
		rs = db.readTable(common.Constants.DBTables.category);
		db.printResultSet(rs);
	}

	static void runTestNG() {
		TestNG runner = new TestNG();
		List<String> suitefiles = new ArrayList<String>();
		suitefiles.add("testng.xml");
		runner.setTestSuites(suitefiles);
		runner.run();
	}
}
