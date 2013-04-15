package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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

	public static void setUpTables() throws SQLException {
		String query = "SET SQL_MODE=\"NO_AUTO_VALUE_ON_ZERO\";\n"
				+ "SET time_zone = \"+00:00\";\n"
				+ "\n"
				+ "DROP TABLE IF EXISTS `alerts`;\n"
				+ "CREATE TABLE IF NOT EXISTS `alerts` (\n"
				+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `name` text NOT NULL,\n"
				+ "  `description` text NOT NULL,\n"
				+ "  `type` varchar(32) NOT NULL,\n"
				+ "  `date_time` datetime NOT NULL,\n"
				+ "  `read` tinyint(1) NOT NULL,\n"
				+ "  `for_doctor_id` int(11) NOT NULL,\n"
				+ "  PRIMARY KEY (`id`),\n"
				+ "  KEY `for_doctor_id` (`for_doctor_id`)\n"
				+ ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;\n"
				+ "\n"
				+ "DROP TABLE IF EXISTS `doctors`;\n"
				+ "CREATE TABLE IF NOT EXISTS `doctors` (\n"
				+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `username` varchar(64) NOT NULL,\n"
				+ "  `password` text NOT NULL,\n"
				+ "  `salt` varchar(32) NOT NULL,\n"
				+ "  `name` varchar(128) NOT NULL,\n"
				+ "  `email` varchar(512) NOT NULL,\n"
				+ "  PRIMARY KEY (`id`),\n"
				+ "  UNIQUE KEY `username` (`username`)\n"
				+ ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;\n"
				+ "\n"
				+ "DROP TABLE IF EXISTS `key_value_etc`;\n"
				+ "CREATE TABLE IF NOT EXISTS `key_value_etc` (\n"
				+ "  `key` varchar(128) NOT NULL,\n"
				+ "  `value` text,\n"
				+ "  PRIMARY KEY (`key`)\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n"
				+ "\n"
				+ "DROP TABLE IF EXISTS `patients`;\n"
				+ "CREATE TABLE IF NOT EXISTS `patients` (\n"
				+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `name` varchar(128) NOT NULL,\n"
				+ "  `email` varchar(512) NOT NULL,\n"
				+ "  `sms_email` varchar(512) NOT NULL,\n"
				+ "  PRIMARY KEY (`id`)\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;\n"
				+ "\n"
				+ "DROP TABLE IF EXISTS `prescription`;\n"
				+ "CREATE TABLE IF NOT EXISTS `prescription` (\n"
				+ "  `id` int(11) NOT NULL,\n"
				+ "  `for_patient_id` int(11) NOT NULL,\n"
				+ "  `name` varchar(64) NOT NULL,\n"
				+ "  `message` text NOT NULL,\n"
				+ "  `picture_path` text NOT NULL,\n"
				+ "  `dosage` text NOT NULL,\n"
				+ "  PRIMARY KEY (`id`),\n"
				+ "  KEY `for_patient_id` (`for_patient_id`)\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n"
				+ "\n"
				+ "DROP TABLE IF EXISTS `prescription_meta`;\n"
				+ "CREATE TABLE IF NOT EXISTS `prescription_meta` (\n"
				+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `prescription_id` int(11) NOT NULL,\n"
				+ "  `meta_key` varchar(128) DEFAULT NULL,\n"
				+ "  `meta_value` int(11) DEFAULT NULL,\n"
				+ "  `day_time` time DEFAULT NULL,\n"
				+ "  PRIMARY KEY (`id`),\n"
				+ "  KEY `prescription_id` (`prescription_id`)\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;\n"
				+ "\n"
				+ "\n"
				+ "ALTER TABLE `alerts`\n"
				+ "  ADD CONSTRAINT `alerts_ibfk_1` FOREIGN KEY (`for_doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
				+ "\n"
				+ "ALTER TABLE `prescription`\n"
				+ "  ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`for_patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
				+ "\n"
				+ "ALTER TABLE `prescription_meta`\n"
				+ "  ADD CONSTRAINT `prescription_meta_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
				+ "\n";
		
		Connection conn = SQLDatabase.getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.execute();
	}
}
