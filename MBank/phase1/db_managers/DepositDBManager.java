package db_managers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import enums.DepositType;
import bank_beans.Deposit;
import bank_exceptions.MBankException;
import managers_interfaces.DepositManager;

public class DepositDBManager implements DepositManager
{
	
	private static final String tableName = "Deposits";
	
	public DepositDBManager()
	{
	}
	
	@Override
	public boolean insert(Deposit deposit, Connection con)
	{
		try
		{
			String sql = "INSERT INTO " + tableName + " (client_id, balance, type, estimated_balance, opening_date, closing_date)" + " VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, deposit.getClient_id());
			ps.setDouble(2, deposit.getBalance());
			ps.setString(3, deposit.getType().getTypeStringValue());
			ps.setDouble(4, deposit.getEstimated_balance());
			ps.setDate(5, new Date(deposit.getOpening_date().getTimeInMillis()));
			ps.setDate(6, new Date(deposit.getClosing_date().getTimeInMillis()));
			ps.execute();
			if (ps.getUpdateCount() > 0){
				return true;
			}
				
			
		} catch (SQLException e)
		{
			System.err.println("Failed to insert into Deposits table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Deposit deposit, Connection con)
	{
		try
		{
			String sql = "UPDATE " + tableName + " SET client_id=?, balance=?, type=?, estimated_balance=?, opening_date=?, closing_date=? WHERE deposit_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, deposit.getClient_id());
			ps.setDouble(2, deposit.getBalance());
			ps.setString(3, deposit.getType().getTypeStringValue());
			ps.setDouble(4, deposit.getEstimated_balance());
			ps.setDate(5, new Date(deposit.getOpening_date().getTimeInMillis()));
			ps.setDate(6, new Date(deposit.getClosing_date().getTimeInMillis()));
			ps.setLong(7, deposit.getDeposit_id());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
			
		} catch (SQLException e)
		{
			System.err.println("Failed to update the Deposits table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Deposit deposit, Connection con)
	{
		try
		{
			String sql = "DELETE FROM " + tableName + " WHERE deposit_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, deposit.getDeposit_id());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
			
			
		} catch (SQLException e)
		{
			System.err.println("Failed to delete deposit with id: "+ deposit.getDeposit_id() + " from the Deposits table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Deposit query(long depositId, Connection con)
	{
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE deposit_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, depositId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Calendar openingDate = Calendar.getInstance();
					openingDate.setTimeInMillis(rs.getDate(6).getTime());
					Calendar closingDate = Calendar.getInstance();
					closingDate.setTimeInMillis(rs.getDate(7).getTime());
					Deposit d = new Deposit(rs.getLong(1), rs.getLong(2), rs.getDouble(3), DepositType.getEnumFromString(rs.getString(4)), rs.getDouble(5), openingDate, closingDate);
					return d;
				}
			}
		} catch (SQLException | MBankException e)
		{
			System.err.println("Failed to query the Deposits table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Deposit> queryDepositsByClient(long clientId, Connection con)
	{
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Deposit> depositsList = new ArrayList<>();
			while(rs.next())
			{
				long  deposit_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				DepositType type = DepositType.getEnumFromString(rs.getString(4));
				double estimated_balance = rs.getDouble(5);
				Calendar openingDate = Calendar.getInstance();
				openingDate.setTimeInMillis(rs.getDate(6).getTime());
				Calendar closingDate = Calendar.getInstance();
				closingDate.setTimeInMillis(rs.getDate(7).getTime());
				Deposit deposit = new Deposit(deposit_id, client_id, balance, type, estimated_balance, openingDate, closingDate);
				depositsList.add(deposit);
			}
			if(!(depositsList.isEmpty()))
			{
				return depositsList;	
			}
		} catch (SQLException | MBankException e)
		{
			System.err.println("Failed to query the " + tableName + " table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Deposit> queryAllDeposits(Connection con) {
		try
		{
			String sql = "SELECT * FROM " + tableName;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Deposit> depositList = new ArrayList<>();
			while(rs.next())
			{
				long  deposit_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				DepositType type = DepositType.getEnumFromString(rs.getString(4));
				double estimated_balance = rs.getDouble(5);
				Calendar openingDate = Calendar.getInstance();
				openingDate.setTimeInMillis(rs.getDate(6).getTime());
				Calendar closingDate = Calendar.getInstance();
				closingDate.setTimeInMillis(rs.getDate(7).getTime());
				Deposit deposit = new Deposit(deposit_id, client_id, balance, type, estimated_balance, openingDate, closingDate);
				depositList.add(deposit);
			}
			return depositList;
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Deposits table");
			e.printStackTrace();
		} catch (MBankException e) {
			System.err.println("Unknown deposit type");
			e.printStackTrace();
		}	
		return null;
	}

	@Override
	public ArrayList<Deposit> queryDepositsByDate(Calendar closingDate, Connection con) {
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE closing_date = ?";			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, new Date(closingDate.getTimeInMillis()));
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Deposit> depositsList = new ArrayList<>();
			while(rs.next())
			{
				long  deposit_id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double balance = rs.getDouble(3);
				DepositType type = DepositType.getEnumFromString(rs.getString(4));
				double estimated_balance = rs.getDouble(5);
				Calendar openingDate = Calendar.getInstance();
				openingDate.setTimeInMillis(rs.getDate(6).getTime());
				Calendar actualClosingDate = Calendar.getInstance();
				closingDate.setTimeInMillis(rs.getDate(7).getTime());
				Deposit deposit = new Deposit(deposit_id, client_id, balance, type, estimated_balance, openingDate, actualClosingDate);
				depositsList.add(deposit);
			}
			if(!(depositsList.isEmpty()))
			{
				return depositsList;	
			}
		} catch (SQLException | MBankException e)
		{
			System.err.println("Failed to query the " + tableName + " table");
			e.printStackTrace();
		}
		return null;
	}
	
	

}
