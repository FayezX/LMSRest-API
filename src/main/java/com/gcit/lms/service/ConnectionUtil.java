package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	/*

	public String driver = "com.mysql.cj.jdbc.Driver";
	//public String url = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public String url = "jdbc:mysql://awsdatabase.cswmfnxnrnuj.us-east-1.rds.amazonaws.com:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//public String username = "root";
	//public String password = "root";
	public String username = "awsdatabase";
	public String password = "awsdatabase";

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(Boolean.FALSE);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	*/

}
