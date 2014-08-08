package managers_interfaces;

import java.sql.Connection;
import java.util.ArrayList;

import bank_beans.Property;

public interface PropertyManager {

	public boolean insert(Property property, Connection con);

	public boolean update(Property property, Connection con);

	public boolean delete(Property property, Connection con);

	public Property query(String propertyName, Connection con);
	
	public ArrayList<Property> queryAllProperties(Connection con);
	
}
