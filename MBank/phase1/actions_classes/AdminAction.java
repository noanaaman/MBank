package actions_classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import enums.ClientType;
import bank_beans.Account;
import bank_beans.Activity;
import bank_beans.Client;
import bank_beans.Deposit;
import bank_beans.Property;
import bank_exceptions.MBankException;

public class AdminAction extends Action {

	public AdminAction(Connection con, long id) {
		super(con, id);
		// TODO Auto-generated constructor stub
	}
	
	
	public long addNewClient(String clientName, String password, String address, String email, String phone, String clientComment, String accountComment, double firstDeposit) throws MBankException, SQLException {
		
		
		double regularDepositRate = Double.parseDouble(propertyManager.query("regular_deposit_rate", con).getProp_value());
		double goldDepositRate = Double.parseDouble(propertyManager.query("gold_deposit_rate", con).getProp_value());
		double platinumDepositRate = Double.parseDouble(propertyManager.query("platinum_deposit_rate", con).getProp_value());
		
		if ((firstDeposit >= regularDepositRate)&(firstDeposit < goldDepositRate)) {
			ClientType type = ClientType.REGULAR;
			String regularCreditLimit = propertyManager.query("regular_credit_limit", con).getProp_value();
			Client client = new Client(0, clientName, password, type, address, email, phone, clientComment);
			long clientId = clientManager.insert(client, con);
			Account account = new Account(0,clientId,firstDeposit,regularCreditLimit,accountComment);
			accountsManager.insert(account, con);
			return clientId;
		}
		
		else if ((firstDeposit >= goldDepositRate)&(firstDeposit < platinumDepositRate)) {
			ClientType type = ClientType.GOLD;
			String goldCreditLimit = propertyManager.query("gold_credit_limit", con).getProp_value();
			Client client = new Client(0, clientName, password, type, address, email, phone, clientComment);
			long clientId = clientManager.insert(client, con);
			Account account = new Account(0,clientId,firstDeposit,goldCreditLimit,accountComment);
			accountsManager.insert(account, con);
			return clientId;
		}
		else if (firstDeposit >= platinumDepositRate) {
			ClientType type = ClientType.PLATINUM;
			String platinumCreditLimit = propertyManager.query("platinum_credit_limit", con).getProp_value();
			Client client = new Client(0, clientName, password, type, address, email, phone, clientComment);
			long clientId = clientManager.insert(client, con);
			Account account = new Account(0,clientId, firstDeposit, platinumCreditLimit, accountComment);
			accountsManager.insert(account, con);
			return clientId;
		}
		else throw new MBankException("In order to open an account, client must deposit at least " + regularDepositRate + ".");
	}
	

	public void removeClient(long clientId) throws MBankException {
		Account account = accountsManager.queryAccountByClient(clientId, con);
		ArrayList<Deposit> depositsList = depositManager.queryDepositsByClient(clientId, con);
		if (depositsList == null) {
			System.out.println("Client had no deposits");
		} else {
			for (int i = 0; i < depositsList.size(); i++) {
			double comission =  Double.parseDouble(propertyManager.query("pre_open_fee", con).getProp_value()) * depositsList.get(i).getBalance();
			account.setBalance(account.getBalance() + depositsList.get(i).getBalance() - comission);
			Activity act = new Activity(0, id, depositsList.get(i).getBalance(), Calendar.getInstance(), comission, "Client " + clientId + " pre-opened deposit " + depositsList.get(i).getDeposit_id() + ".");
			depositManager.delete(depositsList.get(i), con);
			activityManager.insert(act, con);
			}
		}
			
		if (account.getBalance() < 0) {
			Activity removeAccount = new Activity(0, clientId, account.getBalance(), Calendar.getInstance(), 0, "Client charged due to negative balance account on client removal.");
			activityManager.insert(removeAccount, con);
		}
		
		accountsManager.delete(account, con);
		Client c = clientManager.query(clientId, con);
		clientManager.delete(c, con);
	}
	
	public Account viewAccountByClient (long clientId) throws MBankException {
		return accountsManager.queryAccountByClient(clientId, con);
		
	}
	
	public ArrayList<Client> viewAllClientsDetails() throws MBankException {
		return clientManager.queryAllClients(con);
	}
	
	public ArrayList<Account> viewAllAccountsDetails() throws MBankException {
		return accountsManager.queryAllAccounts(con);
	}
	
	public ArrayList<Deposit> viewAllDeposits() throws MBankException {
		return depositManager.queryAllDeposits(con);
	}
	
	public ArrayList<Activity> viewAllActivities() throws MBankException {
		return activityManager.queryAllActivities(con);
	}
	
	public void updateSystemProperty(Property property) throws MBankException {
		propertyManager.update(property, con);
	}
	
	public ArrayList<Property> viewAllSystemProperties() throws MBankException {
		return propertyManager.queryAllProperties(con);
	}
	
}
