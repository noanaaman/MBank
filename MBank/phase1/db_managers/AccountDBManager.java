package db_managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bank_beans.Account;
import bank_exceptions.MBankException;
import managers_interfaces.AccountManager;

public class AccountDBManager implements AccountManager
{

	private static final String tableName = "Accounts";
	
	public AccountDBManager()
	{
	}
	
	@Override
	public void insert(Account account, Connection con) throws SQLException
	{
	
			String sql = "INSERT INTO " + tableName + " (client_id, balance, credit_limit, comment)" + " VALUES (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account.getClient_id());
			ps.setDouble(2, account.getBalance());
			ps.setString(3, account.getCredit_limit());
			ps.setString(4, account.getComment());
			ps.execute();
	}

	@Override
	public boolean update(Account account, Connection con)
	{
		try
		{
			String sql = "UPDATE " + tableName + " SET client_id=?, balance=?, credit_limit=?, comment=? WHERE account_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account.getClient_id());
			ps.setDouble(2, account.getBalance());
			ps.setString(3, account.getCredit_limit());
			ps.setString(4, account.getComment());
			ps.setLong(5, account.getAccount_id());
			ps.execute();
			if (ps.getUpdateCount() > 0){
				return true;
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to update the Accounts table");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Account account, Connection con)
	{
		try
		{
			String sql = "DELETE FROM " + tableName + " WHERE account_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account.getAccount_id());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
			
			
		} catch (SQLException e)
		{
			System.err.println("Failed to delete account with id: "+ account.getAccount_id() + " from the Accounts table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Account query(Long account_id, Connection con)
	{
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE account_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, account_id);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Account a = new Account(rs.getLong(1), rs.getLong(2), rs.getDouble(3), rs.getString(4), rs.getString(5));
					return a;
				}
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Accounts table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account queryAccountByClient(long clientId, Connection con) throws MBankException
	{

		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Account> accountlist = new ArrayList<>();
			Account account = null;
			while(rs.next())
			{
				long account_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				String credit_limit = rs.getString(4);
				String comment = rs.getString(5);
				account = new Account(account_id, client_id, balance, credit_limit, comment);
				accountlist.add(account);
			}
			if(!(accountlist.isEmpty()))
			{
				if(accountlist.size() > 1)
				{
					throw new MBankException("Client [" + clientId + "] has more than one account.");
				}
				return account;	
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Accounts table");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<Account> queryAllAccounts(Connection con) {
		try
		{
			String sql = "SELECT * FROM " + tableName;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Account> accountList = new ArrayList<>();
			while(rs.next())
			{
				long account_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				String credit_limit = rs.getString(4);
				String comment = rs.getString(5);
				Account account = new Account(account_id, client_id, balance, credit_limit, comment);
				accountList.add(account);
			}
			return accountList;
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Accounts table");
			e.printStackTrace();
		}	
		return null;
	}

}
