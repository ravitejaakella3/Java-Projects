package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class DataBaseUtil{
	private static final String URL = "********";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "**********";

	public static Connection getConnection() throws SQLException {
		Connection c= DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement s = c.createStatement();
		String SQL = "CREATE TABLE IF NOT EXISTS users ("
		            + "first_name VARCHAR(255), "
		            + "last_name VARCHAR(255), "
		            + "mobile_number VARCHAR(20), "
		            + "gender VARCHAR(20), "
		            + "email VARCHAR(255), "
		            + "password VARCHAR(255), "
		            + "failed_count INT, "
		            + "account_status VARCHAR(20)"
		            + ")";
		String sql = "CREATE TABLE IF NOT EXISTS orders ("
				+" order_id INT NOT NULL AUTO_INCREMENT,"
                + "route_id INT NOT NULL, "
                + "order_amount DECIMAL(10, 2), "
                + "no_of_passengers INT, "
                + "order_status VARCHAR(50), "
                + "PRIMARY KEY (order_id),"
                +"FOREIGN KEY (route_id) REFERENCES Routes(route_id))"; 
		String sql1 = "CREATE TABLE IF NOT EXISTS Routes ("
                + "route_id INT NOT NULL AUTO_INCREMENT, "
                + "source VARCHAR(255), "
                + "destination VARCHAR(255), "
                + "journey_date DATE, "
                + "ticket_price_per_passenger DOUBLE, "
                + "no_of_seats_available INT, "
                + "PRIMARY KEY (route_id))";
		s.executeUpdate(SQL);
		s.executeUpdate(sql1);
		s.executeUpdate(sql);
		return c;
	}
}
