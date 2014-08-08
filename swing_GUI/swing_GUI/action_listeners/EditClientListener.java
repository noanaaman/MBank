package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bank_beans.Client;
import bank_exceptions.MBankException;
import enums.ClientType;
import screens.EditClientDetails;

public class EditClientListener implements ActionListener {

	private EditClientDetails editClient;
	
	public EditClientListener(EditClientDetails editClient) {
		this.editClient = editClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			String clientName = editClient.getTfClientName().getText();
			System.out.println(clientName);
			ClientType type = ClientType.getEnumFromString((String) editClient.getBoxType().getSelectedItem());
			String address = editClient.getTfAddress().getText();
			String email = editClient.getTfEmail().getText();
			String phone = editClient.getTfPhone().getText();
			String clientComment = editClient.getTfComment().getText();
			String password = editClient.getAdminAction().viewClientDetails(editClient.getClientId()).getPassword();
			if (clientName.equals("")){
				JOptionPane.showMessageDialog(editClient, "Please enter client name.", "Missing Client Name", JOptionPane.ERROR_MESSAGE);
				editClient.getTfClientName().requestFocusInWindow();
			}
			else if (address.equals("")){
				JOptionPane.showMessageDialog(editClient, "Please enter client address.", "Missing Client Address", JOptionPane.ERROR_MESSAGE);
				editClient.getTfAddress().requestFocusInWindow();
			}
			else if (email.equals("")){
				JOptionPane.showMessageDialog(editClient, "Please enter client email.", "Missing Client Email", JOptionPane.ERROR_MESSAGE);
				editClient.getTfEmail().requestFocusInWindow();
			}
			else if (phone.equals("")){
				JOptionPane.showMessageDialog(editClient, "Please enter client phone.", "Missing Client Phone", JOptionPane.ERROR_MESSAGE);
				editClient.getTfPhone().requestFocusInWindow(); 
			}
			else {
				Client c = new Client(editClient.getClientId(), clientName, password, type, address, email, phone, clientComment);
				editClient.getAdminAction().updateClientDetails(c);
				
				JOptionPane.showMessageDialog(editClient, "Successfully updated the details for client: " + editClient.getClientId() + ".", "Successfully Updated Client Details", JOptionPane.INFORMATION_MESSAGE);
				editClient.dispose();
			}
			
			
		} catch (MBankException e) {
			JOptionPane.showMessageDialog(editClient,
                    "Unable to update the client details. Please check the details you entered and try again.",
                    "Failed To Update Client",
                    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		
		

	}

}
