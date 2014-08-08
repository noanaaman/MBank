package bank_beans;

public class Property {

	private String prop_key;
	private String prop_value;
	private String description;
	
	public Property(String prop_key, String prop_value, String description)	{
		this.prop_key = prop_key;
		this.prop_value = prop_value;
		this.description = description;
	}
	
	public String getProp_key()
	{
		return prop_key;
	}
	public String getProp_value()
	{
		return prop_value;
	}
	public void setProp_key(String prop_key)
	{
		this.prop_key = prop_key;
	}
	public void setProp_value(String prop_value)
	{
		this.prop_value = prop_value;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	
	public boolean equals(Object obj) {
		if (obj instanceof Property)
		{
			if (((Property)obj).getProp_key().equals(this.prop_key) && ((Property)obj).getProp_value().equals(this.prop_value))
			{
				return true;
			}		
		}
		return false;
	}

	
}
