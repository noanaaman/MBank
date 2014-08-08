package managers_interfaces;

import java.sql.Connection;
import java.util.ArrayList;
import bank_beans.Activity;

public interface ActivityManager {

	public boolean insert(Activity activity, Connection con);

	public boolean update(Activity activity, Connection con);

	public boolean delete(Activity activity, Connection con);

	public Activity query(Activity activity, Connection con);

	public ArrayList<Activity> queryAllActivities(Connection con);
	
	public ArrayList<Activity> queryActivitiesByClient(long clientId, Connection con);
	
}
