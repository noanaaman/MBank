package db_managers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import bank_beans.Activity;
import managers_interfaces.ActivityManager;

public class ActivityDBManager implements ActivityManager
{
	
	private static final String tableName = "Activity";
	
	public ActivityDBManager()
	{
	}
	
	@Override
	public boolean insert(Activity activity, Connection con)
	{
		
		try
		{
			String sql = "INSERT INTO " + tableName + " (client_id, amount, activity_date, commision, description)" + " VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getClient_id());
			ps.setDouble(2, activity.getAmount());
			ps.setDate(3, new Date(activity.getActivity_date().getTimeInMillis()));
			ps.setDouble(4, activity.getCommission());
			ps.setString(5, activity.getDescription());
			ps.execute();
			if (ps.getUpdateCount() > 0){
				return true;
			}
			
		} catch (SQLException e)
		{
			System.err.println("Failed to insert into Activity table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Activity activity, Connection con)
	{
		
		try
		{
			String sql = "UPDATE " + tableName + " SET client_id=?, amount=?, activity_date=?, commision=?, description=? WHERE id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getClient_id());
			ps.setDouble(2, activity.getAmount());
			ps.setDate(3, new Date(activity.getActivity_date().getTimeInMillis()));
			ps.setDouble(4, activity.getCommission());
			ps.setString(5, activity.getDescription());
			ps.setLong(6, activity.getId());
			ps.execute();
			if (ps.getUpdateCount() > 0)
			{
				return true;
			}	
			
		} catch (SQLException e)
		{
			System.err.println("Failed to update the Activity table");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Activity activity, Connection con)
	{
		try
		{
			String sql = "DELETE FROM " + tableName + " WHERE id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getId());
			ps.execute();
			if(ps.getUpdateCount() > 0)
			{
				return true;
			}
			
			
		} catch (SQLException e)
		{
			System.err.println("Failed to delete activity with id: "+ activity.getId() + " from the Activity table");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Activity query(Activity activity, Connection con)
	{
		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, activity.getId());
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs != null)
			{	
				if(rs.next()) //next() returns false if there are no more rows in the RS
				{	
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(rs.getDate(4).getTime());
					Activity a = new Activity(rs.getLong(1), rs.getLong(2), rs.getDouble(3), c, rs.getDouble(5), rs.getString(6));
					return a;
				}
			}
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Activity table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Activity> queryAllActivities(Connection con)
	{
				
		try
		{
			String sql = "SELECT * FROM " + tableName;	
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Activity> activitiesList = new ArrayList<>();
			while(rs.next())
			{
				long  id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double amount = rs.getDouble(3);
				Calendar activity_date = Calendar.getInstance();
				activity_date.setTimeInMillis(rs.getDate(4).getTime());
				double commission = rs.getDouble(5);
				String description = rs.getString(6);
				Activity activity = new Activity(id, client_id, amount, activity_date, commission, description);
				activitiesList.add(activity);
			}

			return activitiesList;
			
			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the Activity table");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Activity> queryActivitiesByClient(long clientId, Connection con)
	{

		try
		{
			String sql = "SELECT * FROM " + tableName + " WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, clientId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			ArrayList<Activity> clientActivitiesList = new ArrayList<>();
			while(rs.next())
			{
				long id = rs.getLong(1);
				long client_id = rs.getLong(2);
				double amount = rs.getDouble(3);
				Calendar activity_date = Calendar.getInstance();
				activity_date.setTimeInMillis(rs.getDate(4).getTime());
				double commision = rs.getDouble(5);
				String description = rs.getString(6);
				Activity a = new Activity(id, client_id, amount, activity_date, commision, description);
				clientActivitiesList.add(a);
				
			}
			if(!(clientActivitiesList.isEmpty()))
			{
				return clientActivitiesList;	
			}
			
		} catch (SQLException e)
		{
			System.err.println("Failed to query the " + tableName + " table");
			e.printStackTrace();
		}
		return null;
	}

	
}
