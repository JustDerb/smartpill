package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nogit.SQLCreds;

public class SQLDatabase {
	/**
	 * http://stackoverflow.com/questions/5183630/calendar-recurring-repeating-
	 * events-best-storage-method
	 * http://stackoverflow.com/questions/10545869/repeating
	 * -calendar-events-and-some-final-maths
	 */

	public static String url = SQLCreds.URL;
	public static String user = SQLCreds.USERNAME;
	private static String password = SQLCreds.PASSWORD;

	public static void setLogin(String username, String password) {
		SQLDatabase.user = username;
		SQLDatabase.password = password;
	}

	public static void setURL(String url) {
		SQLDatabase.url = url;
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(SQLDatabase.url, SQLDatabase.user,
				SQLDatabase.password);
	}
}
