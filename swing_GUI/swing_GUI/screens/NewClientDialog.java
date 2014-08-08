package screens;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import action_listeners.AddClientListener;
import actions_classes.AdminAction;

public class NewClientDialog extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfClientName;
    private JPasswordField pfPassword;
    private JTextField tfAddress;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JTextField tfComment;
    private JTextField tfAccountComment;
    private JTextField tfFirstDeposit;
    private JLabel lbClientName;
    private JLabel lbPassword;
    private JLabel lbAddress;
    private JLabel lbEmail;
    private JLabel lbPhone;
    private JLabel lbComment;
    private JLabel lbAccountComment;
    private JLabel lbFirstDeposit;
    private JButton btnAddClient;
    private JButton btnClear;
    private JButton btnCancel;
    private boolean succeeded;
    private AdminAction adminAction;
    
    public NewClientDialog(AdminAction adminAction) {
    	JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
    	this.adminAction = adminAction;

        cs.fill = GridBagConstraints.HORIZONTAL;
        
        lbClientName = new JLabel("Client Name: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbClientName, cs);

        tfClientName = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfClientName, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbAddress = new JLabel("Address: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbAddress, cs);

        tfAddress = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfAddress, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbEmail = new JLabel("Email: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbEmail, cs);

        tfEmail = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tfEmail, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbPhone = new JLabel("Phone: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbPhone, cs);

        tfPhone = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfPhone, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbComment = new JLabel("Client Comment: ");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbComment, cs);

        tfComment = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 2;
        panel.add(tfComment, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbAccountComment = new JLabel("Account Comment: ");
        cs.gridx = 0;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(lbAccountComment, cs);

        tfAccountComment = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 6;
        cs.gridwidth = 2;
        panel.add(tfAccountComment, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbFirstDeposit = new JLabel("First Deposit: ");
        cs.gridx = 0;
        cs.gridy = 7;
        cs.gridwidth = 1;
        panel.add(lbFirstDeposit, cs);

        tfFirstDeposit = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 7;
        cs.gridwidth = 2;
        panel.add(tfFirstDeposit, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        btnAddClient = new JButton("Add Client");
        btnAddClient.addActionListener(new AddClientListener(this));
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tfClientName.setText("");
                pfPassword.setText("");
                tfAddress.setText("");
                tfEmail.setText("");
                tfPhone.setText("");
                tfComment.setText("");
                tfAccountComment.setText("");
                tfFirstDeposit.setText("");
            }
        });
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });
      
        JPanel bp = new JPanel();
        bp.add(btnAddClient);
        bp.add(btnClear);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        
        pack();
        setResizable(false);
        setVisible(true);
        
    }

    
    
	public JTextField getTfAccountComment() {
		return tfAccountComment;
	}

	public void setTfAccountComment(JTextField tfAccountComment) {
		this.tfAccountComment = tfAccountComment;
	}

	public JLabel getLbAccountComment() {
		return lbAccountComment;
	}

	public void setLbAccountComment(JLabel lbAccountComment) {
		this.lbAccountComment = lbAccountComment;
	}

	public JTextField getTfClientName() {
		return tfClientName;
	}

	public void setTfClientName(JTextField tfClientName) {
		this.tfClientName = tfClientName;
	}

	public JPasswordField getPfPassword() {
		return pfPassword;
	}

	public void setPfPassword(JPasswordField pfPassword) {
		this.pfPassword = pfPassword;
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

	public JTextField getTfFirstDeposit() {
		return tfFirstDeposit;
	}

	public void setTfFirstDeposit(JTextField tfFirstDeposit) {
		this.tfFirstDeposit = tfFirstDeposit;
	}

	public JLabel getLbClientName() {
		return lbClientName;
	}

	public void setLbClientName(JLabel lbClientName) {
		this.lbClientName = lbClientName;
	}

	public JLabel getLbPassword() {
		return lbPassword;
	}

	public void setLbPassword(JLabel lbPassword) {
		this.lbPassword = lbPassword;
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

	public JLabel getLbFirstDeposit() {
		return lbFirstDeposit;
	}

	public void setLbFirstDeposit(JLabel lbFirstDeposit) {
		this.lbFirstDeposit = lbFirstDeposit;
	}

	public JButton getBtnAddClient() {
		return btnAddClient;
	}

	public void setBtnAddClient(JButton btnAddClient) {
		this.btnAddClient = btnAddClient;
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
    
    



	
}
