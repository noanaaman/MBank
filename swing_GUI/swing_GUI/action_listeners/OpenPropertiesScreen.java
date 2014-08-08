package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_exceptions.MBankException;
import screens.MainScreen;
import screens.SystemProperties;

public class OpenPropertiesScreen implements ActionListener {

	private MainScreen mainScreen;
	
	public OpenPropertiesScreen(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			new SystemProperties(mainScreen);
		} catch (MBankException e1) {
			JOptionPane.showMessageDialog(mainScreen, "Error occured while populating the properties table.", "Unable To View System Properties", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}


	}

}
