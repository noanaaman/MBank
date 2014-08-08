package mank_main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bank_exceptions.MBankException;

public class ConnectionPool {

	private static String url;
	private List<Connection> availableConnections = new ArrayList<Connection>();
	private List<Connection> usedConnections = new ArrayList<Connection>();

	public ConnectionPool(String url, int initialConnectionNum)	throws MBankException {
		ConnectionPool.url = url;
		for (int i = 0; i < initialConnectionNum; i++) {
			try {
				availableConnections.add(DriverManager.getConnection(ConnectionPool.url));
			} catch (SQLException e) {
				throw new MBankException("Failed to create connection to DB: " + url, e);
			}
		}
	}

	public Connection checkout() throws MBankException {
		Connection con;
		if (availableConnections.size() > 0) {// There are available connections
			con = availableConnections.get(availableConnections.size() - 1);
			usedConnections.add(con);
			return con;
		} else {
			throw new MBankException(
					"There aree too many connections to our database at the moment. Please try again later");
		}
	}
	
	public void checkin(Connection con) {
		if (con == null) {
			return;
		}
		usedConnections.remove(con);
		availableConnections.add(con);
	}

}
