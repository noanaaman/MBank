package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import actions_classes.AdminAction;
import screens.MainScreen;

public class RemoveClientListener implements ActionListener {

	MainScreen mainScreen;
	AdminAction adminAction;
	
	public RemoveClientListener(MainScreen mainScreen){
		this.mainScreen = mainScreen;
		this.adminAction = mainScreen.getAdminAction();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		long clientId = mainScreen.getCurrentClientId();
		try {
			adminAction.removeClient(clientId);
			JOptionPane.showMessageDialog(mainScreen, "Successfully removed client " + clientId + " from MBank.", "Client Removed Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (MBankException e) {
			JOptionPane.showMessageDialog(mainScreen, "Failed to remove client " + clientId + " from MBank.", "Failed To Remove Client", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
