package mbank;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actions_classes.ClientAction;
import bank_beans.Account;
import bank_beans.Activity;
import bank_beans.Client;
import bank_beans.Deposit;
import bank_beans.Property;
import bank_exceptions.MBankException;
import mank_main.MBank;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final String MBANK_ATTR = "mbank";
	private static final String CLIENT_ACTION_ATTR = "client_action";
	
	
	private static final String COMMAND_PARAM = "command";
	private static final String LOGIN_COMMAND = "login";
	private static final String USER_ID_PARAM = "user_id";
	private static final String PASSWORD_PARAM = "password";
	private static final String PROPERTIES_COMMAND = "view_system_properties";
	private static final String ACCOUNT_COMMAND = "client_main";
	private static final String DEPOSITS_COMMAND = "view_deposits";
	private static final String CLIENT_DETAILS_COMMAND = "view_details";
	private static final String ACTIVITIES_COMMAND = "activities";
	private static final String LOGOUT_COMMAND = "logout";
	
	private static final String WITHDRAW_COMMAND = "withdraw";
	private static final String DEPOSIT_COMMAND = "deposit";
	private static final String NEW_ADDRESS_PARAM = "new_address";
	private static final String NEW_EMAIL_PARAM = "new_email";
	private static final String NEW_PHONE_PARAM = "new_phone";
	private static final String UPDATE_DETAILS_COMMAND = "update_details";
	private static final String PREOPEN_DEPOSIT_COMMAND = "preopen_deposit";
	private static final String NEW_DEPOSIT_COMMAND = "new_deposit";
	private static final String WITHDRAW_AMOUNT_PARAM = "withdraw_amount";
	private static final String DEPOSIT_AMOUNT_PARAM = "deposit_amount";
	private static final String DEPOSIT_ID_PARAM = "deposit_id";
	private static final String NEW_DEPOSIT_AMOUNT_PARAM = "new_deposit_amount";
	private static final String NEW_DEPOSIT_TYPE_PARAM = "new_deposit_type";
	private static final String LONG_DEPOSIT_TYPE = "long_deposit";
	private static final String SHORT_DEPOSIT_TYPE = "short_deposit";
	
	private static final String LOGIN_JSP = "/index.jsp";
	private static final String CLIENT_MAIN_JSP = "/clientMain.jsp";
	private static final String DEPOSITS_JSP = "/deposits.jsp";
	private static final String CLIENT_DETAILS_JSP = "/clientDetails.jsp";
	private static final String ACTIVITIES_JSP = "/activities.jsp";
	private static final String SYSTEM_PROPERTIES_JSP = "/systemProperties.jsp";
	private static final String PAGE_NOT_FOUND = "/pageNotFound.html";

	private static final String CLIENT_NAME_ATTR = "client_name";
	private static final String SYSTEM_PROPERTIES_ATTR = "systemProperties";
	private static final String CLIENT_ACTIVITIES_ATTR = "clientActivities";
	private static final String CLIENT_DEPOSITS_ATTR = "clientDeposits";
	private static final String ACCOUNT_ATTR = "account";
	private static final String CLIENT_ATTR = "client";
	private static final String ERROR_ATTR = "error";
	private static final String WITHDRAW_ERROR_ATTR = "withdraw_error";
	private static final String DEPOSIT_ERROR_ATTR = "deposit_error";
	private static final String WITHDRAW_SUCCESS_ATTR = "withdraw_success";
	private static final String DEPOSIT_SUCCESS_ATTR = "deposit_success";
	private static final String UPDATE_DETAILS_ERROR_ATTR = "update_details_error";
	private static final String UPDATE_DETAILS_SUCCESS_ATTR = "update_details_success";
	private static final String PRE_OPEN_DEPOSIT_SUCCESS_ATTR = "preopen_success";
	private static final String PRE_OPEN_DEPOSIT_ERROR_ATTR = "preopen_error";
	private static final String NEW_DEPOSIT_SUCCESS_ATTR = "new_deposit_success";
	private static final String NEW_DEPOSIT_ERROR_ATTR = "new_deposit_error";
	private static final String COMMISSION_ATTR = "commission";
	

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		MBank mbank = MBank.getInstance();
		this.getServletContext().setAttribute(MBANK_ATTR, mbank);
		
	}


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller.service() started");
		
		String nextPage = null;
		String command = request.getParameter(COMMAND_PARAM);
		if (command == null)
		{
			nextPage = PAGE_NOT_FOUND; 
		} else {
			switch (command) {
			
				case LOGIN_COMMAND: {
					nextPage = login(request);
					break;
				}
			
				case PROPERTIES_COMMAND: {
					nextPage = goToProperties(request);
					break;
				}
				
				case LOGOUT_COMMAND: {
					nextPage = logout(request);
					break;
				}
				
				case DEPOSITS_COMMAND: {
					nextPage = goToDeposits(request);
					break;
				}
				
				case ACTIVITIES_COMMAND: {
					nextPage = goToActivities(request);
					break;
				}
				
				case CLIENT_DETAILS_COMMAND: {
					nextPage = goToClientDetails(request);
					break;
				}
				
				case ACCOUNT_COMMAND: {
					nextPage = goToClientMain(request);
					break;
				}
				
				case UPDATE_DETAILS_COMMAND: {
					
					nextPage = editClientDetails(request);
					break;
				}
				
				case DEPOSIT_COMMAND: {
					nextPage = depositToAccount(request);
					break;
				}
				
				case WITHDRAW_COMMAND: {
					nextPage = withdrawFromAccount(request);
					break;
				}
				
				case NEW_DEPOSIT_COMMAND: {
					nextPage = createNewDeposit(request);
					break;
				}
				
				case PREOPEN_DEPOSIT_COMMAND: {
					nextPage = preOpenDeposit(request);
					break;
				}
				
				default:
				{
					nextPage = PAGE_NOT_FOUND;
					break;
				}
		
			}
			
			this.getServletContext().getRequestDispatcher(nextPage).forward(request, response);
		}
		
		
	}
	
	
	private String login(HttpServletRequest request) { 
		try {
			Long userId = Long.parseLong(request.getParameter(USER_ID_PARAM));
			String password = request.getParameter(PASSWORD_PARAM);
			
			MBank mbank = (MBank) this.getServletContext().getAttribute(MBANK_ATTR);
			ClientAction clientAction = mbank.clientLogin(userId, password);
			long clientId = clientAction.getId();
			System.out.println("successful login, created client action for client: " + clientId + ".");
			request.getSession(true);
			request.getSession().setAttribute(CLIENT_ACTION_ATTR, clientAction);
			request.getSession().setAttribute(CLIENT_NAME_ATTR, clientAction.viewClientDetails(clientId).getClient_name());
			
			return goToClientMain(request);
			
			
		} catch (MBankException e) {
			request.setAttribute(ERROR_ATTR, e.getLocalizedMessage());
			request.getSession().invalidate();
			System.out.println("login failed");
			return LOGIN_JSP;
			
		} catch (NumberFormatException e) {
			request.setAttribute(ERROR_ATTR, "Please enter a valid user ID (numbers only) and your password.");
			request.getSession().invalidate();
			System.out.println("login failed");
			return LOGIN_JSP;
			
		} catch (NullPointerException e) {
			request.setAttribute(ERROR_ATTR, "Please enter your user and password.");
			request.getSession().invalidate();
			System.out.println("login failed");
			return LOGIN_JSP;
			
		}
		
	}
	
	private String logout(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction)request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		MBank mbank = (MBank) this.getServletContext().getAttribute(MBANK_ATTR);
		try {
			mbank.clientLogout(clientAction);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getSession().invalidate();
		return LOGIN_JSP;
	}
	
	
	private String goToProperties(HttpServletRequest request){
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		ArrayList<Property> systemProperties = null;
		try {
			systemProperties = clientAction.viewAllSystemProperties();
			
			request.setAttribute(SYSTEM_PROPERTIES_ATTR, systemProperties);
		} catch (MBankException e) {
			request.setAttribute(ERROR_ATTR, e.getLocalizedMessage());
			e.printStackTrace();
		} 
		
		return SYSTEM_PROPERTIES_JSP;
	}
	
	private String goToActivities(HttpServletRequest request){
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		ArrayList<Activity> activities = null;
		try {
			activities = clientAction.viewClientActivities(clientAction.getId());
			request.setAttribute(CLIENT_ACTIVITIES_ATTR, activities);
		} catch (MBankException e) {
			request.setAttribute(ERROR_ATTR, e.getLocalizedMessage());
		}
		
		return ACTIVITIES_JSP;
	}
	
	private String goToDeposits(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		ArrayList<Deposit> deposits = null;
		double commission = 0;
		try {
			commission = (Double.parseDouble(clientAction.viewSystemProperty("pre_open_fee").getProp_value()))/100;
			deposits = clientAction.viewClientDeposits(clientAction.getId());
			request.setAttribute(CLIENT_DEPOSITS_ATTR, deposits);
			request.setAttribute(COMMISSION_ATTR, commission);
			
		} catch (MBankException e) {
			request.setAttribute(ERROR_ATTR, e.getLocalizedMessage());
		}
		
		return DEPOSITS_JSP;
	}

	private String goToClientMain(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Account account;
		Client client;
		String commission;
		try {
			commission = clientAction.viewSystemProperty("commission_rate").getProp_value();
			client = clientAction.viewClientDetails(clientAction.getId());
			account = clientAction.viewClientAccountDetails(clientAction.getId());
			request.setAttribute(CLIENT_ATTR, client);
			request.setAttribute(ACCOUNT_ATTR, account);
			request.setAttribute(COMMISSION_ATTR, commission);
		} catch (MBankException e) {
			request.setAttribute(ERROR_ATTR, e.getLocalizedMessage());
		}
		
		return CLIENT_MAIN_JSP;
		
	}
	
	private String goToClientDetails(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Client client;
		try {
			client = clientAction.viewClientDetails(clientAction.getId());
			request.setAttribute(CLIENT_ATTR, client);
		} catch (MBankException e) {
			request.setAttribute(ERROR_ATTR, e.getLocalizedMessage());
		}
		
		return CLIENT_DETAILS_JSP;
	}
	
	private String editClientDetails(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		Client client;
		String newAddress = request.getParameter(NEW_ADDRESS_PARAM);
		String newEmail = request.getParameter(NEW_EMAIL_PARAM);
		String newPhone = request.getParameter(NEW_PHONE_PARAM);
		if (isEmptyString(newAddress) && isEmptyString(newEmail) && isEmptyString(newPhone)) {
			request.setAttribute(UPDATE_DETAILS_ERROR_ATTR, "Please choose at least one detail to update.");
		} else {
			try {
				client = clientAction.viewClientDetails(clientAction.getId());
			
				if(!isEmptyString(newAddress)){
					client.setAddress(newAddress);
				}
				if(!isEmptyString(newEmail)){
					client.setEmail(newEmail);
				}
				if(!isEmptyString(newPhone)){
					client.setPhone(newPhone);
				}
			
				clientAction.updateClientDetails(client);
				request.setAttribute(UPDATE_DETAILS_SUCCESS_ATTR, "Successfully updated your details");
			
			
			} catch (MBankException e) {
			request.setAttribute(UPDATE_DETAILS_ERROR_ATTR, e.getLocalizedMessage());
			}
		}
		return goToClientDetails(request);
		
	}
	
	private String withdrawFromAccount(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		double withdrawAmount;
		try {
			withdrawAmount = amountValidation(request.getParameter(WITHDRAW_AMOUNT_PARAM));
			clientAction.withdrawFromAccount(withdrawAmount);
			request.setAttribute(WITHDRAW_SUCCESS_ATTR, "Successfully withdrew " + withdrawAmount + " from account.");
		} catch (MBankException e) {
			request.setAttribute(WITHDRAW_ERROR_ATTR, e.getLocalizedMessage());
		} catch (NumberFormatException e1) {
			request.setAttribute(NEW_DEPOSIT_ERROR_ATTR, "Please enter a valid amount to withdraw.");
		}
		return goToClientMain(request);
		
	}
	
	private String depositToAccount(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		try {
			double depositAmount = amountValidation(request.getParameter(DEPOSIT_AMOUNT_PARAM));
			clientAction.depositToAccount(depositAmount);
			request.setAttribute(DEPOSIT_SUCCESS_ATTR, "Successfully deposited " + depositAmount + " to account.");
		} catch (MBankException e) {
			request.setAttribute(DEPOSIT_ERROR_ATTR, e.getLocalizedMessage());
		} catch (NumberFormatException e1) {
			request.setAttribute(NEW_DEPOSIT_ERROR_ATTR, "Please enter a valid amount to deposit.");
		}
		return goToClientMain(request);
	}
	
	private String preOpenDeposit(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		String depositIdStr = request.getParameter(DEPOSIT_ID_PARAM);
		try {
			Long depositId = Long.parseLong(depositIdStr);	
			clientAction.preOpenDeposit(depositId);
			System.out.println("Preopened deopsit " + depositId + ".");
			request.setAttribute(PRE_OPEN_DEPOSIT_SUCCESS_ATTR, "Successfully pre-opened deposit " + depositId + " and added the current balance to your account.");
		} catch (MBankException e) {
			request.setAttribute(PRE_OPEN_DEPOSIT_ERROR_ATTR, e.getLocalizedMessage());
		} catch (NumberFormatException e1) {
			request.setAttribute(PRE_OPEN_DEPOSIT_ERROR_ATTR, "Please enter ID for and existing long-term deposit.");
		}
		return goToDeposits(request);
	}
	
	private String createNewDeposit(HttpServletRequest request) {
		ClientAction clientAction = (ClientAction) request.getSession().getAttribute(CLIENT_ACTION_ATTR);
		try {
			Double amount = amountValidation(request.getParameter(NEW_DEPOSIT_AMOUNT_PARAM));
			String depositType = request.getParameter(NEW_DEPOSIT_TYPE_PARAM);
			if (isEmptyString(depositType)) {
				request.setAttribute(NEW_DEPOSIT_ERROR_ATTR, "Please choose the type of deposit you wish to open.");
			} else {
				switch (depositType) {
				
					case LONG_DEPOSIT_TYPE: {
						clientAction.createNewLongDeposit(amount);
						request.setAttribute(NEW_DEPOSIT_SUCCESS_ATTR, "Successfully created a new long deposit.");
						break;
					}
				
					case SHORT_DEPOSIT_TYPE: {
						clientAction.createNewShortDeposit(amount);
						request.setAttribute(NEW_DEPOSIT_SUCCESS_ATTR, "Successfully created a new short deposit.");
						break;
					}
				
					default: {
						request.setAttribute(NEW_DEPOSIT_ERROR_ATTR, "Please choose the type of deposit you wish to open.");
					}
				}
			}	
		} catch (MBankException e) {
			request.setAttribute(NEW_DEPOSIT_ERROR_ATTR, e.getLocalizedMessage());
		} catch (NumberFormatException e1) {
			request.setAttribute(NEW_DEPOSIT_ERROR_ATTR, "Please enter a valid amount to deposit.");
		}
		
		
		return goToDeposits(request);
	}
	
	//javascript only checks for number, here I check for positive double
	private double amountValidation(String amount) throws MBankException
	{
		double amountValue = 0;
		if (isEmptyString(amount)) {
			throw new MBankException("Please enter amount.");
		}
		try
		{
			amountValue = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (amountValue <= 0) {
			throw new MBankException("Amount needs to be positive.");
		}
		return amountValue;
	}
	
	//check for null or empty string or just white spaces
	private boolean isEmptyString(String s) {
		return s == null || s.trim().isEmpty();
	}
	

	
    

}
