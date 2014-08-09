package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import screens.MainScreen;
import screens.ViewAllActivities;

public class AllActivitiesListener implements ActionListener {

	private MainScreen mainScreen;
	
	public AllActivitiesListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			new ViewAllActivities(mainScreen);
			
		} catch (MBankException e) {
			JOptionPane.showMessageDialog(mainScreen, "Unable to display bank activities.", "Fetching Activities Failed", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
