package com.example.librarymanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	static {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
        }
	}
    private static final String URL = "jdbc:mysql:///library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "Maru@841";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

