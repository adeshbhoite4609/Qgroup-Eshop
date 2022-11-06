package com.velocity.qmart;



import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	static Connection con;

	public static Connection createC() {

		try {

			// Load the Driver
			Class.forName("com.mysql.jdbc.Driver");

			// Create Connection
			String user = "root";
			String password = "aadi4609";
			String url = "jdbc:mysql://localhost:3306/qmart";
			con = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return con;
	}

}