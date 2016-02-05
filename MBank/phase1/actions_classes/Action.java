 package actions_classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import db_managers.AccountDBManager;
import db_managers.ActivityDBManager;
import db_managers.ClientDBManager;
import db_managers.DepositDBManager;
import db_managers.PropertyDBManager;
import enums.ClientType;
import bank_beans.Account;
import bank_beans.Activity;
import bank_beans.Client;
import bank_beans.Deposit;
import bank_beans.Property;
import bank_exceptions.MBankException;

public abstract class Action {

	protected long id;
	protected Connection con;
	protected ClientDBManager clientManager;
	protected AccountDBManager accountsManager;
	protected DepositDBManager depositManager;
	protected ActivityDBManager activityManager;
	protected PropertyDBManager propertyManager;
	
	public Action(Connection con, Long id) {
		clientManager=new ClientDBManager();
		accountsManager=new AccountDBManager();	
		depositManager=new DepositDBManager();
		activityManager=new ActivityDBManager ();
		propertyManager=new PropertyDBManager();
		this.con=con;
		this.id=id;
	}
	
	public Connection logOut() throws SQLException {
		return con;
	}
	
	public void updateClientDetails(Client c) throws MBankException {
		clientManager.update(c, con);
		ClientType type = c.getType();
		Account account = viewAccountDetails(c.getClient_id());
		switch (type) {
			case REGULAR: account.setCredit_limit(propertyManager.query("regular_credit_limit", con).getProp_value()); break;
			case GOLD: account.setCredit_limit(propertyManager.query("gold_credit_limit", con).getProp_value()); break;
			case PLATINUM: account.setCredit_limit(propertyManager.query("platinum_credit_limit", con).getProp_value()); break;
		}
		accountsManager.update(account, con);
		
	}
	
	public Client viewClientDetails(long clientId) throws MBankException {
		return clientManager.query(clientId, con);
	}
	
	public Account viewAccountDetails(long accountId) throws MBankException {
		return accountsManager.query(accountId, con);
	}
	
	public Account viewClientAccountDetails(long clientId) throws MBankException {
		return accountsManager.queryAccountByClient(clientId, con);
	}
	
	public ArrayList<Deposit> viewClientDeposits(long clientId) throws MBankException {
		return depositManager.queryDepositsByClient(clientId, con);
	}
	
	public ArrayList<Activity> viewClientActivities(long clientId) throws MBankException {
		return activityManager.queryActivitiesByClient(clientId, con);
	}
	
	public Property viewSystemProperty(String propertyName) {
		return propertyManager.query(propertyName, con);
	}
	
	public Deposit viewDepositById(Long depositId) throws MBankException {
		return depositManager.query(depositId, con);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ClientDBManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(ClientDBManager clientManager) {
		this.clientManager = clientManager;
	}

	public AccountDBManager getAccountsManager() {
		return accountsManager;
	}

	public void setAccountsManager(AccountDBManager accountsManager) {
		this.accountsManager = accountsManager;
	}

	public DepositDBManager getDepositManager() {
		return depositManager;
	}

	public void setDepositManager(DepositDBManager depositManager) {
		this.depositManager = depositManager;
	}

	public ActivityDBManager getActivityManager() {
		return activityManager;
	}

	public void setActivityManager(ActivityDBManager activityManager) {
		this.activityManager = activityManager;
	}

	public PropertyDBManager getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyDBManager propertyManager) {
		this.propertyManager = propertyManager;
	}
	
	
}
