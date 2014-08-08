package screens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import db_managers.PropertyDBManager;

public class Login {
	public static boolean authenticate(String username, String password) {
        // check admin username and password
		String db_address = "jdbc:derby://localhost:1527/MBank;";
		try {
			Connection con = DriverManager.getConnection(db_address);
			PropertyDBManager propertyManager = new PropertyDBManager();
			String adminUser = propertyManager.query("admin_username", con).getProp_value();
			String adminPassword = propertyManager.query("admin_password", con).getProp_value();
			if (username.equals(adminUser) && password.equals(adminPassword)) {
	            return true;
	        }
	        con.close();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		
    }
}
