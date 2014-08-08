package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import screens.MainScreen;
import screens.ViewClients;

public class ViewAllClientsListener implements ActionListener {

	private MainScreen mainScreen;
	
	public ViewAllClientsListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			new ViewClients(mainScreen);
		} catch (MBankException e1) {
			JOptionPane.showMessageDialog(mainScreen, "Error occured while populating the clients table.", "Unable To Open Clients List", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

	}

}
