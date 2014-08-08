package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import screens.MainScreen;
import screens.ViewClientDeposits;

public class ViewDepositsListener implements ActionListener {

	private MainScreen mainScreen;
	
	public ViewDepositsListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			new ViewClientDeposits(mainScreen);
		} catch (MBankException e) {
			JOptionPane.showMessageDialog(mainScreen, "Unable to display client deposits. Please make sure you entered a valid client ID.", "Fetching Client Deposits Failed", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
