package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import enums.ClientType;
import bank_beans.Client;
import bank_exceptions.MBankException;
import action_listeners.EditClientListener;
import actions_classes.AdminAction;

public class EditClientDetails extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lbExplanation;
	private JTextField tfClientName;
    private JComboBox<String> boxType;
	private JTextField tfAddress;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JTextField tfComment;
    private JLabel lbClientName;
    private JLabel lbType;
    private JLabel lbAddress;
    private JLabel lbEmail;
    private JLabel lbPhone;
    private JLabel lbComment;
    private JButton btnEditClient;
    private JButton btnReset;
    private JButton btnCancel;
    private boolean succeeded;
    private AdminAction adminAction;
    private long clientId;
    private MainScreen mainScreen;
    
    
    public EditClientDetails(MainScreen mainScreen) throws MBankException {
    	
    	setTitle("Edit Client Details");
    	JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
    	this.setMainScreen(mainScreen);
        this.adminAction = mainScreen.getAdminAction();
    	this.clientId = mainScreen.getCurrentClientId();
    	final Client c = adminAction.viewClientDetails(clientId);

        cs.fill = GridBagConstraints.HORIZONTAL;
        
        lbExplanation = new JLabel("Edit details for client " + clientId + ":");
        lbExplanation.setFont(new Font("ariel", Font.BOLD, 14));
        lbExplanation.setForeground(Color.BLUE);
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 3;
        panel.add(lbExplanation, cs);
        
        lbClientName = new JLabel("Client Name: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbClientName, cs);

        tfClientName = new JTextField(20);
        tfClientName.setText(c.getClient_name());
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfClientName, cs);
        
        lbType = new JLabel("Client Type: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbType, cs); 
        
        String[] types = {ClientType.REGULAR.getTypeStringValue(), ClientType.GOLD.getTypeStringValue(), ClientType.PLATINUM.getTypeStringValue()};
        boxType = new JComboBox<>(types);
        ClientType type = c.getType();
        System.out.println(type.toString());
        switch (type) {
        	case REGULAR: {
        		boxType.setSelectedIndex(0);
        		break;
        	}
        	case GOLD: {
        		boxType.setSelectedIndex(1);
        		break;
        	}
        	case PLATINUM: {
        		boxType.setSelectedIndex(2);
        		break;
        	}
        }
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(boxType, cs);
        
        
        lbAddress = new JLabel("Address: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbAddress, cs);

        tfAddress = new JTextField(20);
        tfAddress.setText(c.getAddress());
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tfAddress, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbEmail = new JLabel("Email: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbEmail, cs);

        tfEmail = new JTextField(20);
        tfEmail.setText(c.getEmail());
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfEmail, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbPhone = new JLabel("Phone: ");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbPhone, cs);

        tfPhone = new JTextField(20);
        tfPhone.setText(c.getPhone());
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 2;
        panel.add(tfPhone, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbComment = new JLabel("Client Comment: ");
        cs.gridx = 0;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(lbComment, cs);

        tfComment = new JTextField(20);
        tfComment.setText(c.getComment());
        cs.gridx = 1;
        cs.gridy = 6;
        cs.gridwidth = 2;
        panel.add(tfComment, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        btnEditClient = new JButton("Edit Details");
        btnEditClient.addActionListener(new EditClientListener(this));
        
        
        btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tfClientName.setText(c.getClient_name());
                boxType.setSelectedItem(c.getType().toString());
                tfAddress.setText(c.getAddress());
                tfEmail.setText(c.getEmail());
                tfPhone.setText(c.getPhone());
                tfComment.setText(c.getComment());
            }
        });
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });
      
        JPanel bp = new JPanel();
        bp.add(btnEditClient);
        bp.add(btnReset);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        
        pack();
        setResizable(false);
        setVisible(true);
        
    }
    
    

	@Override
	public void dispose() {
		super.dispose();
		try {
			getMainScreen().getResultClientName().setText(adminAction.viewClientDetails(clientId).getClient_name());
			getMainScreen().getResultlbType().setText(adminAction.viewClientDetails(clientId).getType().toString());
			getMainScreen().getResultlbAddress().setText(adminAction.viewClientDetails(clientId).getAddress());
			getMainScreen().getResultlbEmail().setText(adminAction.viewClientDetails(clientId).getEmail());
			getMainScreen().getResultlbPhone().setText(adminAction.viewClientDetails(clientId).getPhone());
			getMainScreen().getResultlbComment().setText(adminAction.viewClientDetails(clientId).getComment());
		} catch (MBankException e) {
			JOptionPane.showMessageDialog(mainScreen, "Failed to update client details in main screen. Please check your DB connection.", "Failed To Update Main Screen", JOptionPane.ERROR_MESSAGE);
			System.err.println("Failed to update client details in main screen. Please check your DB connection.");
			e.printStackTrace();
		}
	}



	public JButton getBtnEditClient() {
		return btnEditClient;
	}

	public void setBtnEditClient(JButton btnEditClient) {
		this.btnEditClient = btnEditClient;
	}

	public JButton getBtnReset() {
		return btnReset;
	}

	public void setBtnReset(JButton btnReset) {
		this.btnReset = btnReset;
	}

	public JTextField getTfClientName() {
		return tfClientName;
	}

	public void setTfClientName(JTextField tfClientName) {
		this.tfClientName = tfClientName;
	}

	public JTextField getTfAddress() {
		return tfAddress;
	}

	public void setTfAddress(JTextField tfAddress) {
		this.tfAddress = tfAddress;
	}

	public JTextField getTfEmail() {
		return tfEmail;
	}

	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	public JTextField getTfPhone() {
		return tfPhone;
	}

	public void setTfPhone(JTextField tfPhone) {
		this.tfPhone = tfPhone;
	}

	public JTextField getTfComment() {
		return tfComment;
	}

	public void setTfComment(JTextField tfComment) {
		this.tfComment = tfComment;
	}

	public JLabel getLbClientName() {
		return lbClientName;
	}

	public void setLbClientName(JLabel lbClientName) {
		this.lbClientName = lbClientName;
	}

	public JLabel getLbAddress() {
		return lbAddress;
	}

	public void setLbAddress(JLabel lbAddress) {
		this.lbAddress = lbAddress;
	}

	public JLabel getLbEmail() {
		return lbEmail;
	}

	public void setLbEmail(JLabel lbEmail) {
		this.lbEmail = lbEmail;
	}

	public JLabel getLbPhone() {
		return lbPhone;
	}

	public void setLbPhone(JLabel lbPhone) {
		this.lbPhone = lbPhone;
	}

	public JLabel getLbComment() {
		return lbComment;
	}

	public void setLbComment(JLabel lbComment) {
		this.lbComment = lbComment;
	}


	public JButton getBtnAddClient() {
		return btnEditClient;
	}

	public void setBtnAddClient(JButton btnAddClient) {
		this.btnEditClient = btnAddClient;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(JButton btnCancel) {
		this.btnCancel = btnCancel;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}
	
	public AdminAction getAdminAction() {
		return adminAction;
	}

	public void setAdminAction(AdminAction adminAction) {
		this.adminAction = adminAction;
	}

	public JLabel getLbExplanation() {
		return lbExplanation;
	}

	public void setLbExplanation(JLabel lbExplanation) {
		this.lbExplanation = lbExplanation;
	}

	public JLabel getLbType() {
		return lbType;
	}

	public void setLbType(JLabel lbType) {
		this.lbType = lbType;
	}

	public JComboBox<String> getBoxType() {
		return boxType;
	}

	public void setBoxType(JComboBox<String> boxType) {
		this.boxType = boxType;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}



	public MainScreen getMainScreen() {
		return mainScreen;
	}



	public void setMainScreen(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
    
    

	
}
