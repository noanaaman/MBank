package bank_beans;

import enums.ClientType;

public class Client {

	private long client_id;
	private String client_name;
	private String password;
	private ClientType type;
	private String address;
	private String email;
	private String phone;
	private String comment;
	
	
	
	public Client() {
		super();
	}


	public Client(long client_id, String client_name, String password,
			ClientType type, String address, String email, String phone,
			String comment)
	{
		this.client_id = client_id;
		this.client_name = client_name;
		this.password = password;
		this.type = type;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.comment = comment;
	}


	public long getClient_id()
	{
		return client_id;
	}


	public void setClient_id(long client_id)
	{
		this.client_id = client_id;
	}


	public String getClient_name()
	{
		return client_name;
	}


	public void setClient_name(String client_name)
	{
		this.client_name = client_name;
	}


	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}


	public ClientType getType()
	{
		return type;
	}


	public void setType(ClientType type)
	{
		this.type = type;
	}


	public String getAddress()
	{
		return address;
	}


	public void setAddress(String address)
	{
		this.address = address;
	}


	public String getEmail()
	{
		return email;
	}


	public void setEmail(String email)
	{
		this.email = email;
	}


	public String getPhone()
	{
		return phone;
	}


	public void setPhone(String phone)
	{
		this.phone = phone;
	}


	public String getComment()
	{
		return comment;
	}


	public void setComment(String comment)
	{
		this.comment = comment;
	}


	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Client))
		{
		return false;
		}
	if(this.client_id == ((Client)obj).getClient_id())
	{
		return true;
	}
	return false;
	} 
	
	
	
	
}
