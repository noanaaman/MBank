package actions_classes;

import java.sql.Connection;
import java.util.Calendar;

import enums.ClientType;
import enums.DepositType;
import bank_beans.Account;
import bank_beans.Activity;
import bank_beans.Deposit;
import bank_exceptions.MBankException;

public class ClientAction extends Action {

	public ClientAction(Connection con, long id) {
		super(con, id);
		// TODO Auto-generated constructor stub
	}
	
	public void withdrawFromAccount(double withdrawAmount) throws MBankException { 
		if (withdrawAmount > 0) {
			Account account=accountsManager.queryAccountByClient(id, con);
			ClientType type = clientManager.query(id, con).getType();
			double commissionRate = Double.parseDouble(propertyManager.query("commission_rate", con).getProp_value());
			double newBalance = account.getBalance() - withdrawAmount - commissionRate;
			switch (type) {
			case REGULAR: if (newBalance > (0 - Double.parseDouble(propertyManager.query("regular_credit_limit", con).getProp_value()))) {
								account.setBalance(account.getBalance() - withdrawAmount - commissionRate); //update balance- subtracaccount.setBalance(account.getBalance() - withdrawAmount - 0.5); //update balance- subtract withdraw amount and commission ratet withdraw amount and commission rate
								Activity act = new Activity(0, id, withdrawAmount, Calendar.getInstance(), commissionRate, "Client " + id + " withdrew " + withdrawAmount + " from their account.");
								activityManager.insert(act, con);
								accountsManager.update(account, con);
								System.out.println("Successfully withdrew " + withdrawAmount + " from account.");
							} else throw new MBankException("Client passed credit limit");
							break;
			case GOLD: if (newBalance > (0 - Double.parseDouble(propertyManager.query("gold_credit_limit", con).getProp_value()))) {
							account.setBalance(account.getBalance() - withdrawAmount - commissionRate); //update balance- subtracaccount.setBalance(account.getBalance() - withdrawAmount - 0.5); //update balance- subtract withdraw amount and commission ratet withdraw amount and commission rate
							Activity act = new Activity(0, id, withdrawAmount, Calendar.getInstance(), commissionRate, "Client " + id + " withdrew " + withdrawAmount + " from their account.");
							activityManager.insert(act, con);
							accountsManager.update(account, con);
							System.out.println("Successfully withdrew " + withdrawAmount + " from account.");
						} else throw new MBankException("Client passed credit limit");
						break;
			case PLATINUM: account.setBalance(account.getBalance() - withdrawAmount - commissionRate); //update balance- subtracaccount.setBalance(account.getBalance() - withdrawAmount - 0.5); //update balance- subtract withdraw amount and commission ratet withdraw amount and commission rate
							Activity act = new Activity(0, id, withdrawAmount, Calendar.getInstance(), commissionRate, "Client " + id + " withdrew " + withdrawAmount + " from their account.");
							activityManager.insert(act, con);
							accountsManager.update(account, con);
							System.out.println("Successfully withdrew " + withdrawAmount + " from account.");
							break;
			}
		} else throw new MBankException("You cannot withdraw a negative amount. Please enter a positive number.");
		
	}
	
	public void depositToAccount(double depositAmount) throws MBankException {
		if (depositAmount > 0) {
			Account account=accountsManager.queryAccountByClient(id, con);
			double commissionRate = Double.parseDouble(propertyManager.query("commission_rate", con).getProp_value());
			account.setBalance(account.getBalance() + depositAmount - commissionRate);
			accountsManager.update(account, con);
			Activity act = new Activity(0, id, depositAmount, Calendar.getInstance(), commissionRate, "Client " + id + " deposited " + depositAmount + " to their account.");
			activityManager.insert(act, con);
			System.out.println("Successfully deposited " + depositAmount + " to account.");
		} else throw new MBankException("You cannot deposit a negative amount.");
		
	}
	
	public void createNewShortDeposit(double depositAmount) {
		ClientType type = clientManager.query(id, con).getType();
		double regularYearlyInterest = 365 * ((Double.parseDouble(propertyManager.query("regular_daily_interest", con).getProp_value())) / 100);
		double goldYearlyInterest = 365 * ((Double.parseDouble(propertyManager.query("gold_daily_interest", con).getProp_value())) / 100);
		double platinumYearlyInterest = 365 * ((Double.parseDouble(propertyManager.query("platinum_daily_interest", con).getProp_value())) / 100);
		double estimated_balance = 0.0;
		switch (type) {
		case REGULAR: {
			estimated_balance = depositAmount + (depositAmount * regularYearlyInterest); 
			break;
		}
		case GOLD: {
			estimated_balance = depositAmount + (depositAmount * goldYearlyInterest);
			break;
		}
		case PLATINUM: {
			estimated_balance = depositAmount + (depositAmount * platinumYearlyInterest); 
			break;
		}
		}
		Calendar closingDate = Calendar.getInstance();
		closingDate.add(Calendar.YEAR, 1);
		Deposit deposit = new Deposit(0, id, depositAmount, DepositType.SHORT, estimated_balance, Calendar.getInstance(), closingDate);
		depositManager.insert(deposit, con);
		System.out.println("Successfully create deposit.");
	}
	
	public void createNewLongDeposit(double depositAmount) {
		ClientType type = clientManager.query(id, con).getType();
		double regularYearlyInterest = 365 * (Double.parseDouble(propertyManager.query("regular_daily_interest", con).getProp_value()) / 100);
		double goldYearlyInterest = 365 * (Double.parseDouble(propertyManager.query("gold_daily_interest", con).getProp_value()) / 100);
		double platinumYearlyInterest = 365 * (Double.parseDouble(propertyManager.query("platinum_daily_interest", con).getProp_value()) / 100);
		double estimated_balance = depositAmount;
		switch (type) {
			case REGULAR: {
				for (int i = 0; i < 40; i++) {
					estimated_balance = depositAmount + (depositAmount * regularYearlyInterest); 
				}
				break;
			} 
			case GOLD: {
				for (int i = 0; i < 40; i++) {
					estimated_balance = depositAmount + (depositAmount * goldYearlyInterest);
				}
				break;
			}
			case PLATINUM: {
				for (int i = 0; i < 40; i++) {
					estimated_balance = depositAmount + (depositAmount * platinumYearlyInterest);	
				}
				break;
			}
		}
		Calendar closingDate = Calendar.getInstance();
		closingDate.add(Calendar.YEAR, 40);
		Deposit deposit = new Deposit(0, id, depositAmount, DepositType.LONG, estimated_balance, Calendar.getInstance(), closingDate);
		depositManager.insert(deposit, con);
		System.out.println("Successfully create deposit.");
	}
	
	
	public void preOpenDeposit(long depositId) throws MBankException {
		Deposit deposit = depositManager.query(depositId, con);
		DepositType type = deposit.getType();
		if (type == DepositType.LONG) {
			Account account = accountsManager.queryAccountByClient(id, con);
			double preOpenCommission = Double.parseDouble(propertyManager.query("pre_open_fee", con).getProp_value());
			double preOpenFee = preOpenCommission * deposit.getBalance();
			deposit.setBalance(deposit.getBalance() - preOpenFee);
			account.setBalance(deposit.getBalance() + account.getBalance());
			accountsManager.update(account, con);
			Activity act = new Activity(0, id, deposit.getBalance(), Calendar.getInstance(), preOpenFee, "Client " + id + " pre-opened deposit " + depositId + ".");
			depositManager.delete(deposit, con);
			activityManager.insert(act, con);
			System.out.println("Successfully opened deposit.");
		}
		else throw new MBankException("You Cannot pre-open a short deposit.");
		
	}
	
	
	
	
}
