package db_managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.ClientType;
import bank_beans.Client;
import bank_exceptions.MBankException;
import managers_interfaces.ClientManager;

public class ClientDBManager implements ClientManager {

	private static final String tableName = "Clients";
	
	public ClientDBManager() {
	}
	
	@Override
	public long insert(Client client, Connection con)
	{
		
		try
		{
			String sql = "INSERT INTO " + tableName + " (client_name, password, type, address, email, phone, comment)" + " VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, client.getClient_name());
			ps.setString(2, client.getPassword());
			ps.setString(3, client.getType().getTypeStringValue());
			ps.setString(4, client.getAddress());
			ps.setString(5, client.getEmail());
			ps.setString(6, client.getPhone());
			ps.setString(7, client.getComment());
			ps.execute();
			if (ps.getUpdateCount() > 0){
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAX(client_id) from Clients");
				long max = 0;
				while(rs.next()) {
					max = rs.getLong(1);
					return max;
				}
				
			}
				
			
		} catch (SQLException e)
		{
			System.err.println("Failed to insert into Clients table");
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean update(Client client, Connection con) throws MBankException
	{
		
		try
		{
			String sql = "UPDATE " + tableName + " SET client_name=?, password=?, type=?, address=?, email=?, phone=?, comment=? WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, client.getClient_name());
			ps.setString(2, client.getPassword());
			ps.setString(3, client.getType().getTypeStringValue());
			ps.setString(4, client.getAddress());
			ps.setString(5, client.getEmail());
			ps.setString(6, client.getPhone());
			ps.setString(7, client.getComment());
			ps.setLong(8, client.getClient_id());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}
			
		} catch (SQLException e)
		{
			throw new MBankException("Failed to update Clients table", e);
		}
		
		return false;
	}

	@Override
	public boolean delete(Client client, Connection con)
	{
		
		try
		{
			String sql = "DELETE FROM " + tableName + " WHERE client_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, client.getClient_id());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
			
			
		} catch (SQLException e)
		{
			System.err.println("Failed to delete client with id: "+ client.getClient_id() + " from the Clients table");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Client query(Long client_id, Connection con)
	{
		
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, client_id);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Client c = new Client(rs.getLong(1), rs.getString(2), rs.getString(3), ClientType.getEnumFromString(rs.getString(4)), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
					return c;
				}
			}
		} catch (SQLException | MBankException e)
		{
			System.err.println("Failed to query the Clients table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Client> queryAllClients(Connection con)
	{
		
		try
		{
			String sql = "SELECT * FROM " + tableName;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Client> clientList = new ArrayList<>();
			while(rs.next())
			{
				long client_id = rs.getLong(1);
				String client_name = rs.getString(2);
				String password = rs.getString(3);
				String type = rs.getString(4); //convert to ClientType in client object
				String address = rs.getString(5);
				String email = rs.getString(6);
				String phone = rs.getString(7);
				String comment = rs.getString(8);
				Client client = new Client(client_id, client_name, password, ClientType.getEnumFromString(type), address, email, phone, comment);
				clientList.add(client);
			}

			return clientList;
			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Clients table");
			e.printStackTrace();
		} catch (MBankException e)
		{
			System.err.println("Unknown client type");
			e.printStackTrace();
		}
		
		return null;
	}

}
