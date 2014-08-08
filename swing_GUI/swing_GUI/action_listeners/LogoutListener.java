package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import mank_main.MBank;
import screens.MainScreen;

public class LogoutListener implements ActionListener {
	
	private MainScreen mainScreen;
	
	public LogoutListener(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			MBank.getInstance().adminLogout(mainScreen.getAdminAction());
			JOptionPane.showMessageDialog(mainScreen, "Thank you for visiting the MBank administration console.", "You Have Logged Out of MBank", JOptionPane.INFORMATION_MESSAGE);
			mainScreen.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainScreen, "Unable to logout of MBank administration console.", "Logout Failed", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
