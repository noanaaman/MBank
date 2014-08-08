package cli_screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import bank_beans.Activity;
import bank_beans.Client;
import bank_beans.Deposit;
import bank_exceptions.MBankException;
import actions_classes.ClientAction;
import mank_main.MBank;

public class ClientMain {
	
	private ClientAction clientAction;
	
	public ClientMain(){
		showClientLogin();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			long clientId = Long.parseLong(br.readLine());
			System.out.println("Please enter your password\r\n");
			String password = br.readLine();
			clientAction = MBank.getInstance().clientLogin(clientId, password);
			showClientMenu(clientId);
			int input = Integer.parseInt(br.readLine());
			
			switch (input) {
            case 1: updateDetailsMenu(clientId);
            case 2: showClientDetails(clientId);
            case 3: withdraw(clientId);
            case 4: deposit(clientId);
            case 5: createDeposit(clientId);
            case 6: preOpen(clientId);
            case 7: showAccountDetails(clientId);
            case 8:	showDeposits(clientId);
            case 9: showActivities(clientId);
            case 10: logOut();
            default: System.out.println("Please choose 1-3");
            }
			
		} catch (Exception e) {
			System.out.println("This is not a valid client ID/password combination.");
			e.printStackTrace();
			showClientLogin();
		}
	}
	
	public void showClientLogin(){
		System.out.println("");
		System.out.println("Hello Client!");
		System.out.println("====================================================");
		System.out.println("To login to MBank, please enter your ID");
		
	}
	
	public void showClientMenu(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
        System.out.println("Main Menu");
        System.out.println("---------------------------");
        System.out.println("1. Update my details");
        System.out.println("2. View my details");
        System.out.println("3. Withdraw from my account");
        System.out.println("4. Deposit to my account");
        System.out.println("5. Create a new deposit");
        System.out.println("6. Pre-open a long deposit");
        System.out.println("7. View account details");
        System.out.println("8. View my deposits");
        System.out.println("9. View my activities");
        System.out.println("10. Log out");
        System.out.println("----------------------------");
        System.out.println("");
        System.out.print("Please select an option from 1-10");
        System.out.println("");
        System.out.println("");
	}
	
	public void updateDetailsMenu(long clientId) throws MBankException {
		Client c = clientAction.viewClientDetails(clientId);
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
        System.out.println("Update Client Details");
        System.out.println("---------------------------");
        System.out.println("1. edit address");
        System.out.println("2. edit email");
        System.out.println("3. edit phone");
        System.out.println("4. jump to previous level");
        System.out.println("5. Log out");
        System.out.println("----------------------------");
        System.out.println("");
        System.out.print("Please select an option from 1-5");
        System.out.println("");
        System.out.println("");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
			int input = Integer.parseInt(br.readLine());
			switch (input) {
			case 1: {
				System.out.println("Enter address:");
				System.out.println("");
				c.setAddress(br.readLine());
				showBasicMenu(clientId);
			}
			case 2: {
				System.out.println("Enter email:");
				System.out.println("");
				c.setEmail(br.readLine());
				showBasicMenu(clientId);
			}
			case 3: {
				System.out.println("Enter phone:");
				System.out.println("");
				c.setPhone(br.readLine());
				showBasicMenu(clientId);
			}
			case 4: jumpToPreviousLevel(clientId);
            case 5: logOut();
            default: System.out.println("Please choose 1-5.");
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void showClientDetails(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
        System.out.println("These are Your Details:");
        System.out.println("Client ID: " + clientAction.viewClientDetails(clientId).getClient_id());
        System.out.println("Client Type: " + clientAction.viewClientDetails(clientId).getType().getTypeStringValue());
        System.out.println("Address: " + clientAction.viewClientDetails(clientId).getAddress());
        System.out.println("Email: " + clientAction.viewClientDetails(clientId).getEmail());
        System.out.println("Phone: " + clientAction.viewClientDetails(clientId).getPhone());
        System.out.println("");
        System.out.println("-------------------------------");
        showBasicMenu(clientId);
        
	}
	
	public void withdraw(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
		System.out.println("Please enter withdraw amount.");
		System.out.println("");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			double amount = Double.parseDouble(br.readLine());
			clientAction.withdrawFromAccount(amount);
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid positive number.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		showBasicMenu(clientId);
		
	}
	
	public void deposit(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
		System.out.println("Please enter deposit amount.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			double amount = Double.parseDouble(br.readLine());
			clientAction.depositToAccount(amount);
		} catch (Exception e) {
			System.out.println("Please enter a valid positive number.");
			e.printStackTrace();
		}
		
		showBasicMenu(clientId);
	}
	
	public void createDeposit (long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
		System.out.println("Which type of deposit do you want to create?");
		System.out.println("1. Short");
        System.out.println("2. Long");
        System.out.println("3. jump to previous level");
        System.out.println("4. Log out");
        System.out.println("");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        try {
			int input = Integer.parseInt(br.readLine());
			switch (input) {
			case 1: {
				System.out.println("Please enter deposit amount.");
				double amount = Double.parseDouble(br.readLine());
				clientAction.createNewShortDeposit(amount);
			}
			case 2: {
				System.out.println("Please enter deposit amount.");
				double amount = Double.parseDouble(br.readLine());
				clientAction.createNewLongDeposit(amount);
			}
			case 3: jumpToPreviousLevel(clientId);
            case 4: logOut();
            default: System.out.println("Please choose 1-4.");
            }
		} catch (Exception e) {
			System.out.println("Please choose 1-4.");
			e.printStackTrace();
		}
        
        showBasicMenu(clientId);
	}
	
	public void preOpen(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
		System.out.println("Please enter deposit ID.");
		System.out.println("");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			long depositId = Long.parseLong(br.readLine());
			clientAction.preOpenDeposit(depositId);
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		showBasicMenu(clientId);
	}
	
	
	public void showAccountDetails(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
        System.out.println("These are Your Details:");
        System.out.println("Account ID: " + clientAction.viewClientAccountDetails(clientId).getAccount_id());
        System.out.println("Balance: " + clientAction.viewClientAccountDetails(clientId).getBalance());
        System.out.println("Credit Limit: " + clientAction.viewClientAccountDetails(clientId).getCredit_limit());
        System.out.println("");
        System.out.println("-------------------------------");
        showBasicMenu(clientId);
        
	}
	
	public void showDeposits(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
		ArrayList<Deposit> deposits = clientAction.viewClientDeposits(clientId);
		if (deposits != null) {
			for (int i = 0; i < deposits.size(); i++) {
				System.out.println("Deposit ID: " + deposits.get(i).getDeposit_id() + " Balance: " + deposits.get(i).getBalance() + " Type: " + deposits.get(i).getType().getTypeStringValue() + " Estimated Balance: " + deposits.get(i).getEstimated_balance() + " Opening Date: " + deposits.get(i).getOpening_date() + " Closing Date: " + deposits.get(i).getClosing_date());
			}
			System.out.println("");
	        System.out.println("-------------------------------");
	        showBasicMenu(clientId);
		} else {
			System.out.println("you have no deposits.");
		}
		showBasicMenu(clientId);
	}
	
	public void showActivities(long clientId) throws MBankException {
		System.out.println("");
		System.out.println("Welcome, " + clientAction.viewClientDetails(clientId).getClient_name() + "!");
		System.out.println("");
		ArrayList<Activity> activities = clientAction.viewClientActivities(clientId);
		if (activities != null) {
			for (int i = 0; i < activities.size(); i++) {
				System.out.println("Activity ID: " + activities.get(i).getId() + " Amount: " + activities.get(i).getAmount() + " Date: " + activities.get(i).getActivity_date() + " Commision: " + activities.get(i).getCommission() + " Description: " + activities.get(i).getDescription());
			}
			System.out.println("");
	        System.out.println("-------------------------------");
	        
		} else {
			System.out.println("There are no activities to show.");
		}
		showBasicMenu(clientId);
	}
	
	public void jumpToPreviousLevel(long clientId) throws MBankException {
		showClientMenu(clientId);
	}
	
	public void logOut() throws SQLException, MBankException {
		MBank.getInstance().clientLogout(clientAction);
		new MainMenu();
	}
	
	public void showBasicMenu(long clientId) {
		System.out.println("");
		System.out.println("----------------------------");
        System.out.println("1. jump to previous level");
        System.out.println("2. Log out");
        System.out.println("");
        System.out.println("Please choose 1 or 2.");
        System.out.println("");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
			int input = Integer.parseInt(br.readLine());
			switch (input) {
            case 1: showClientMenu(clientId);;
            case 2: logOut();
            default: System.out.println("Please choose 1 or 2.");
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
