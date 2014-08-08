package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_beans.Account;
import bank_beans.Client;
import actions_classes.AdminAction;
import screens.MainScreen;

public class ViewClientListener implements ActionListener {

	MainScreen mainScreen;
	AdminAction adminAction;
	
	public ViewClientListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
		this.adminAction = mainScreen.getAdminAction(); 
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		try {
			long clientId = Long.parseLong(mainScreen.getClientId().getText());
			Client c = adminAction.viewClientDetails(clientId);
			Account acc = adminAction.viewAccountByClient(clientId);
			mainScreen.getResultClientName().setText(c.getClient_name());
			mainScreen.getResultlbType().setText(c.getType().getTypeStringValue());
			mainScreen.getResultlbAddress().setText(c.getAddress());
			mainScreen.getResultlbEmail().setText(c.getEmail());
			mainScreen.getResultlbPhone().setText(c.getPhone());
			mainScreen.getResultlbComment().setText(c.getComment());
			mainScreen.getResultlbBalance().setText(Double.toString(acc.getBalance()));
			
			mainScreen.setCurrentClientId(clientId);
			
			mainScreen.getViewDeposits().setEnabled(true);
			mainScreen.getEditClientDetails().setEnabled(true);
			mainScreen.getRemoveClient().setEnabled(true);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainScreen, mainScreen.getClientId().getText() + " is not a valid client ID.", "Client ID Does Not Exist", JOptionPane.ERROR_MESSAGE);
			mainScreen.getViewDeposits().setEnabled(false);
			mainScreen.getEditClientDetails().setEnabled(false);
			mainScreen.getRemoveClient().setEnabled(false);
			mainScreen.getResultClientName().setText(null);
			mainScreen.getResultlbType().setText(null);
			mainScreen.getResultlbAddress().setText(null);
			mainScreen.getResultlbEmail().setText(null);
			mainScreen.getResultlbPhone().setText(null);
			mainScreen.getResultlbComment().setText(null);
			mainScreen.getResultlbBalance().setText(null);
			mainScreen.setCurrentClientId(-1);
			
			
			
			e.printStackTrace();
		}
		

	}

}
