package managers_interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import bank_beans.Account;
import bank_exceptions.MBankException;

public interface AccountManager {
	
	public void insert(Account account, Connection con) throws SQLException;
	
	public boolean update(Account account, Connection con);
	
	public boolean delete(Account account, Connection con);
	
	public Account query(Long account_id, Connection con);
	
	public Account queryAccountByClient(long clientId, Connection con) throws MBankException;
	
	public ArrayList<Account> queryAllAccounts(Connection con);

}
