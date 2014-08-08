package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import screens.EditClientDetails;
import screens.MainScreen;

public class OpenClientEditListener implements ActionListener {
	
	private MainScreen mainScreen;
	
	public OpenClientEditListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			new EditClientDetails(mainScreen);
		} catch (MBankException e1) {
			JOptionPane.showMessageDialog(mainScreen, "Error occured while populating client details. Please make sure you have selected an existing MBank client.", "Unable To Edit Client Details", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

	}

}
