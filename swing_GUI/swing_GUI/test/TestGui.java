package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import screens.MainScreen;
import actions_classes.AdminAction;

public class TestGui {

	public static void main(String[] args) throws SQLException {
		
		String db_address = "jdbc:derby://localhost:1527/MBank;";
    	Connection con = DriverManager.getConnection(db_address);
    	AdminAction adminAction = new AdminAction(con, 0);
    	
    	new MainScreen(adminAction);
	}
	
}
