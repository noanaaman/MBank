package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import screens.SystemProperties;
import bank_beans.Property;
import bank_exceptions.MBankException;

public class EditSystemPropertiesListener implements ActionListener {

	private SystemProperties systemProperties;
	
	public EditSystemPropertiesListener (SystemProperties systemProperties) {
		this.systemProperties = systemProperties;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int confirmResult = JOptionPane.showConfirmDialog(systemProperties, "Saving will affect entire MBank system. Are you sure you want to change system properties?", "Warning", JOptionPane.YES_NO_OPTION);
		if (confirmResult == JOptionPane.YES_OPTION) {
			int rows = systemProperties.getPropertyTable().getRowCount();
			for (int row = 0; row < rows; row++) {
				
				String propKey = systemProperties.getPropertyTable().getValueAt(row, 0).toString();
				String PropValue = systemProperties.getPropertyTable().getValueAt(row, 1).toString();
				String description = systemProperties.getPropertyTable().getValueAt(row, 2).toString();
				
				Property p = new Property(propKey, PropValue, description);
				try {
					systemProperties.getAdminAction().updateSystemProperty(p);
				} catch (MBankException e1) {
					System.err.println("Error while updating " + propKey);
					e1.printStackTrace();
				}
				
				
		} 
		
		
		}

	}

}
