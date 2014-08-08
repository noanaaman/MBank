package managers_interfaces;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;

import bank_beans.Deposit;

public interface DepositManager {

	public boolean insert(Deposit deposit, Connection con);

	public boolean update(Deposit deposit, Connection con);

	public boolean delete(Deposit deposit, Connection con);

	public Deposit query(long depositId, Connection con);

	public ArrayList<Deposit> queryDepositsByClient(long clientId, Connection con);
	
	public ArrayList<Deposit> queryDepositsByDate(Calendar closingDate, Connection con);
	
	public ArrayList<Deposit> queryAllDeposits(Connection con);
	
}
