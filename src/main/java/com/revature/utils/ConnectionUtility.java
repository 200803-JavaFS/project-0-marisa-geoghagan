package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:postgresql://javafs200803.c0vdqceozeoi.us-east-2.rds.amazonaws.com/bank";
		String username = "postgres";
		String password = "password"; 
		
		return DriverManager.getConnection(url, username, password);
	}
}