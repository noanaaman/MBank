package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import screens.MainScreen;
import screens.ViewClientActivities;

public class ClientActivitiesListener implements ActionListener {

	private MainScreen mainScreen;
	
	public ClientActivitiesListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			long clientId = Long.parseLong(mainScreen.getActivitiesClientId().getText());
			mainScreen.setActivityClientId(clientId);
			new ViewClientActivities(mainScreen);
			
		} catch (MBankException e) {
			JOptionPane.showMessageDialog(mainScreen, "Unable to display client activities. Please make sure you entered a valid client ID.", "Fetching Client Activities Failed", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
