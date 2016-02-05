package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

import bank_exceptions.MBankException;
import actions_classes.AdminAction;
import actions_classes.ClientAction;

public class Test {

	public static void main(String[] args) throws SQLException, MBankException {
		
		 String db_address = "jdbc:derby://localhost:1527/MBank;create=true";
		 Connection connection = DriverManager.getConnection(db_address);
		 ClientAction lizAction = new ClientAction(connection, 1000);
         ClientAction leslieAction = new ClientAction(connection, 1003);
         lizAction.createNewLongDeposit(2500);
         lizAction.createNewShortDeposit(348);
         leslieAction.createNewLongDeposit(1240);
         leslieAction.createNewShortDeposit(5600);
         lizAction.depositToAccount(123);
         leslieAction.depositToAccount(1200);
         lizAction.withdrawFromAccount(560);
         leslieAction.withdrawFromAccount(900);
         

         connection.close();
		
		Calendar now = Calendar.getInstance();
		System.out.println(now);
	}
}
