package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import actions_classes.AdminAction;
import screens.NewClientDialog;

public class AddClientListener implements ActionListener {

	private NewClientDialog newClientDialog;
	private AdminAction adminAction;
	
	public AddClientListener(NewClientDialog newClientDialog){
		this.newClientDialog = newClientDialog;
		this.adminAction = newClientDialog.getAdminAction();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String clientName = newClientDialog.getTfClientName().getText();
		String password = new String (newClientDialog.getPfPassword().getPassword());
		String address = newClientDialog.getTfAddress().getText();
		String email = newClientDialog.getTfEmail().getText();
		String phone = newClientDialog.getTfPhone().getText();
		String clientComment = newClientDialog.getTfComment().getText();
		String accountComment = newClientDialog.getTfAccountComment().getText();
		double firstDeposit = Double.parseDouble(newClientDialog.getTfFirstDeposit().getText());
		try {
			long clientId = adminAction.addNewClient(clientName, password, address, email, phone, clientComment, accountComment, firstDeposit);
			JOptionPane.showMessageDialog(newClientDialog, "Successfully added " + clientName + " (ID: " + clientId + ") to MBank. " + firstDeposit + "$ were added to their account.",
                    "New Client Added Successfully", JOptionPane.INFORMATION_MESSAGE);
			newClientDialog.setSucceeded(true);
			newClientDialog.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(newClientDialog,
                    "Unable to add the client. Please check the details you entered and try again.",
                    "Failed To Add New Client",
                    JOptionPane.ERROR_MESSAGE);


            newClientDialog.setSucceeded(false);
			e1.printStackTrace();
		}
		
	}
	
	
}
