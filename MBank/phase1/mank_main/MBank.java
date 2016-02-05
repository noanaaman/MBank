package mank_main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cli_screens.MainMenu;
import bank_beans.Account;
import bank_beans.Deposit;
import bank_exceptions.MBankException;
import db_managers.AccountDBManager;
import db_managers.ActivityDBManager;
import db_managers.ClientDBManager;
import db_managers.DepositDBManager;
import db_managers.PropertyDBManager;
import enums.ClientType;
import actions_classes.AdminAction;
import actions_classes.ClientAction;

public class MBank {

	Connection con;
	ConnectionPool connectionPool;
	ClientDBManager clientManager;
	AccountDBManager accountsManager;
	DepositDBManager depositManager;
	ActivityDBManager activityManager;
	PropertyDBManager propertyManager;

	// singleton definitions
	private static MBank myBank = null;

	private MBank() throws SQLException, MBankException {
		clientManager = new ClientDBManager();
		accountsManager = new AccountDBManager();
		depositManager = new DepositDBManager();
		activityManager = new ActivityDBManager();
		propertyManager = new PropertyDBManager();
		con = DriverManager.getConnection("jdbc:derby://localhost:1527/MBank;create=false");
		int numberOfConnections = Integer.parseInt(propertyManager.query("number_of_connections", con).getProp_value());
		connectionPool = new ConnectionPool("jdbc:derby://localhost:1527/MBank;create=false",numberOfConnections);
	}

	public static MBank getInstance() {
		if (myBank == null) {
			try {
				myBank = new MBank();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return myBank;
	}

	// methods

	public AdminAction adminLogin()	{
		Connection con;
		try {
			con = myBank.connectionPool.checkout();
			AdminAction adminAction = new AdminAction(con, 0);
			return adminAction;
		} catch (MBankException e) {
			System.err.println("There are no available DB connections at the moment");
			e.printStackTrace();
			return null;
		}
		
			}

	public void adminLogout(AdminAction adminAction) throws SQLException {
		connectionPool.checkin(adminAction.logOut());

	}

	public ClientAction clientLogin(long clientId, String password) throws MBankException {
		String clientPassword = clientManager.query(clientId, con).getPassword();
		if (password.equals(clientPassword)) {
			Connection con = myBank.connectionPool.checkout();
			ClientAction clientAction = new ClientAction(con, clientId);
			return clientAction;
		} else {
			throw new MBankException(
					"The user/password combination is incorrect");
		}
	}

	public void clientLogout(ClientAction clientAction) throws SQLException {
		connectionPool.checkin(clientAction.logOut());

	}

	//daily deposits thread- adds interest to all the deposits and closes the ones that end that day
	public class DailyDepositsCheck extends TimerTask {

		@Override
		public void run() {

			try {
				Connection con = myBank.connectionPool.checkout();

				ArrayList<Deposit> allDeposits = depositManager.queryAllDeposits(con);

				for (int i = 0; i < allDeposits.size(); i++) {
					long clientId = allDeposits.get(i).getClient_id();
					ClientType type = clientManager.query(clientId, con).getType();
					double regularDailyInterest = Double.parseDouble(propertyManager.query("regular_daily_interest", con).getProp_value());
					double goldDailyInterest = Double.parseDouble(propertyManager.query("gold_daily_interest", con).getProp_value());
					double platinumDailyInterest = Double.parseDouble(propertyManager.query("platinum_daily_interest", con).getProp_value());
					double currentBalance = allDeposits.get(i).getBalance();
					switch (type) {
					case REGULAR: allDeposits.get(i).setBalance(currentBalance + (currentBalance*regularDailyInterest));
					case GOLD: allDeposits.get(i).setBalance(currentBalance + (currentBalance*goldDailyInterest));
					case PLATINUM: allDeposits.get(i).setBalance(currentBalance + (currentBalance*platinumDailyInterest));
					}

				}

				ArrayList<Deposit> closingDeposits = depositManager.queryDepositsByDate(Calendar.getInstance(), con);

				for (int i = 0; i < closingDeposits.size(); i++) {
					long clientId = closingDeposits.get(i).getClient_id();
					double balance = closingDeposits.get(i).getBalance();
					Account acc = accountsManager.queryAccountByClient(clientId, con);
					acc.setBalance(acc.getBalance() + balance);
					accountsManager.update(acc, con);
					depositManager.delete(closingDeposits.get(i), con);
				}
				
				myBank.connectionPool.checkin(con);

			} catch (MBankException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public static void main(String[] args) throws SQLException, MBankException {
		
		
		MBank myBank = new MBank(); 
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		Timer timer = new Timer();
		DailyDepositsCheck dailyDepositsCheck = myBank.new DailyDepositsCheck();
		timer.scheduleAtFixedRate(dailyDepositsCheck, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		new MainMenu();
		
	}
}
