package managers_interfaces;

import java.sql.Connection;
import java.util.ArrayList;
import bank_beans.Client;
import bank_exceptions.MBankException;


public interface ClientManager
{

	public long insert(Client client, Connection con);

	public boolean update(Client client, Connection con) throws MBankException;

	public boolean delete(Client client, Connection con);

	public Client query(Long client_id, Connection con);

	public ArrayList<Client> queryAllClients(Connection con);
}
