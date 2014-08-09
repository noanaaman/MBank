package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import action_listeners.AllActivitiesListener;
import action_listeners.ClientActivitiesListener;
import action_listeners.LogoutListener;
import action_listeners.OpenClientEditListener;
import action_listeners.OpenPropertiesScreen;
import action_listeners.RemoveClientListener;
import action_listeners.ViewAllClientsListener;
import action_listeners.ViewClientListener;
import action_listeners.ViewDepositsListener;
import actions_classes.AdminAction;


public class MainScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//data members
	private long currentClientId = -1;
	private long activityClientId = -1;
	private JLabel clientsManagement;
	private JButton viewClient;
	private JButton viewDeposits;
	private JButton removeClient;
	private JButton editClientDetails;
	private JButton addNewClient;
	private JButton viewAllClients;
	private JButton systemProperties;
	private JButton bankActivities;
	private JLabel activitiesManagement;
	private JLabel propertiesManagement;
	private JTextField clientId;
	private JTextField activitiesClientId;
	private AdminAction adminAction;
	private JButton viewAccount;
	private JLabel lbClientName = new JLabel("Client Name: ");
    private JLabel lbType = new JLabel("Client Type: ");
	private JLabel lbAddress = new JLabel("Address: ");
    private JLabel lbEmail = new JLabel("Email: ");
    private JLabel lbPhone = new JLabel("Phone: ");
    private JLabel lbComment = new JLabel("Comment: ");
    private JLabel lbBalance = new JLabel("Account Balance (in $): ");
    private JLabel resultClientName = new JLabel();
    private JLabel resultlbType = new JLabel();
	private JLabel resultlbAddress = new JLabel();
    private JLabel resultlbEmail = new JLabel();
    private JLabel resultlbPhone = new JLabel();
    private JLabel resultlbComment = new JLabel();
    private JLabel resultlbBalance = new JLabel();
    private JButton sendIdForActivities = new JButton("View");
    private JButton logout = new JButton("Logout");
	
	
	
	//constructor 
	public MainScreen(final AdminAction adminAction) {
		this.adminAction = adminAction;
		setTitle("MBank Administration Console");
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(Color.darkGray);
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.BOTH;
        cs.insets = new Insets(2, 2, 2, 2);
        
        //client management pane
        JPanel clientManagement = new JPanel(new GridBagLayout());
        clientManagement.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints cs1 = new GridBagConstraints();
        cs1.fill = GridBagConstraints.BOTH;
        cs1.insets = new Insets(1, 1, 2, 1);
        this.clientsManagement = new JLabel("Client Management");
        clientsManagement.setHorizontalAlignment(SwingConstants.CENTER);
        clientsManagement.setFont(new Font("ariel", Font.BOLD, 18));
        clientsManagement.setForeground(Color.BLUE);
        cs1.gridx = 0;
        cs1.gridy = 0;
        cs1.gridheight = 1;
        clientManagement.add(clientsManagement, cs1);
        
        JPanel viewEditClient = new JPanel(new BorderLayout());
        JLabel lb1 = new JLabel("View/Edit Client Details");
        lb1.setHorizontalAlignment(SwingConstants.CENTER);
        lb1.setFont(new Font("ariel", Font.BOLD, 14));
        JPanel details = new JPanel(new GridLayout(8, 3, 1, 1));
        this.clientId = new JTextField("Client ID");
        clientId.setForeground(Color.LIGHT_GRAY);
        
        this.viewClient = new JButton("View Client");
        details.add(clientId);
        details.add(viewClient);
        details.add(lbClientName);
        details.add(resultClientName);
        details.add(lbType);
        details.add(resultlbType);
        details.add(lbAddress);
        details.add(resultlbAddress);
        details.add(lbEmail);
        details.add(resultlbEmail);
        details.add(lbPhone);
        details.add(resultlbPhone);
        details.add(lbComment);
        details.add(resultlbComment);
        details.add(lbBalance);
        details.add(resultlbBalance);
        viewClient.addActionListener(new ViewClientListener(this));
        clientId.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clientId.setText("");
				clientId.setForeground(Color.BLACK);
				
			}
		});
        
       
        JPanel clientOptions = new JPanel();
        this.viewDeposits = new JButton("View Client Deposits");
        viewDeposits.setEnabled(false);
        this.editClientDetails = new JButton("Edit Client Details");
        editClientDetails.setEnabled(false);
        this.removeClient = new JButton("Remove Client");
        removeClient.setEnabled(false);
        clientOptions.add(viewDeposits);
        clientOptions.add(editClientDetails);
        clientOptions.add(removeClient);
        viewEditClient.add(lb1, BorderLayout.NORTH);
        viewEditClient.add(details, BorderLayout.CENTER);
        viewEditClient.add(clientOptions, BorderLayout.SOUTH);
        cs1.gridx = 0;
        cs1.gridy = 1;
        cs1.gridheight = 10;
        clientManagement.add(viewEditClient, cs1);
        editClientDetails.addActionListener(new OpenClientEditListener(this));
        removeClient.addActionListener(new RemoveClientListener(this));
        viewDeposits.addActionListener(new ViewDepositsListener(this));
        
        
        this.addNewClient = new JButton("Add New Client");
        this.viewAllClients = new JButton("View All Bank Clients");
        addNewClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewClientDialog(getAdminAction());
				
			}
		});
        
        viewAllClients.addActionListener(new ViewAllClientsListener(this));
        
        JLabel other = new JLabel("More Options: ");
        JPanel p1 = new JPanel();
        p1.add(other);
        p1.add(addNewClient);
        p1.add(viewAllClients);
        cs1.gridx = 0;
        cs1.gridy = 12;
        cs1.gridheight = 1;
        clientManagement.add(p1, cs1);
        
        
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridheight = 15;
        mainPanel.add(clientManagement, cs);
        
        //system properties management pane
        JPanel propertiesPane = new JPanel(new BorderLayout());
        this.propertiesManagement = new JLabel("Manage System Properties");
        propertiesManagement.setHorizontalAlignment(SwingConstants.CENTER);
        propertiesManagement.setFont(new Font("ariel", Font.BOLD, 18));
        propertiesManagement.setForeground(Color.BLUE);
        JPanel prop = new JPanel();
        this.systemProperties = new JButton("View/Edit All System Properties");
        systemProperties.addActionListener(new OpenPropertiesScreen(this));
        prop.add(systemProperties);
        JPanel end = new JPanel();
        propertiesPane.add(propertiesManagement, BorderLayout.NORTH);
        propertiesPane.add(prop, BorderLayout.CENTER);
        propertiesPane.add(end, BorderLayout.SOUTH);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridheight = 7;
        mainPanel.add(propertiesPane, cs);
        
        //bank activities management pane
        JPanel activitiesPane = new JPanel(new BorderLayout());
        this.activitiesManagement = new JLabel("Bank Activities");
        activitiesManagement.setHorizontalAlignment(SwingConstants.CENTER);
        activitiesManagement.setFont(new Font("ariel", Font.BOLD, 18));
        activitiesManagement.setForeground(Color.BLUE);
        this.activitiesClientId = new JTextField(10);
        activitiesClientId.setText("Client ID");
        activitiesClientId.setForeground(Color.LIGHT_GRAY);
        this.bankActivities = new JButton("View All Bank Activities");
        JLabel viewClientActivities = new JLabel("    View Client Activities:  ");
        JLabel space = new JLabel("     ");
        JLabel space2 = new JLabel("     ");
        JPanel clientActivitiesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs2 = new GridBagConstraints();
        cs2.gridx = 0;
        cs2.gridy = 0;
        clientActivitiesPanel.add(viewClientActivities, cs2);
        cs2.gridx = 1;
        cs2.gridy = 0;
        clientActivitiesPanel.add(activitiesClientId, cs2);
        cs2.gridx = 2;
        cs2.gridy = 0;
        clientActivitiesPanel.add(space, cs2);
        cs2.gridx = 1;
        cs2.gridy = 1;
        clientActivitiesPanel.add(space2, cs2);
        cs2.gridx = 0;
        cs2.gridy = 2;
        cs2.gridwidth = 2;
        clientActivitiesPanel.add(sendIdForActivities, cs2);
        JPanel viewAll = new JPanel();    
        viewAll.add(bankActivities);
        activitiesPane.add(activitiesManagement, BorderLayout.NORTH);
        activitiesPane.add(clientActivitiesPanel, BorderLayout.CENTER);
        activitiesPane.add(viewAll, BorderLayout.SOUTH);
        activitiesClientId.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				activitiesClientId.setText("");
				activitiesClientId.setForeground(Color.BLACK);
				
			}
		});
        sendIdForActivities.addActionListener(new ClientActivitiesListener(this));
        cs.gridx = 1;
        cs.gridy = 8;
        cs.gridheight = GridBagConstraints.RELATIVE;
        mainPanel.add(activitiesPane, cs);
        
        bankActivities.addActionListener(new AllActivitiesListener(this));
        
        //logout pane
        JPanel logoutPanel = new JPanel(new BorderLayout());
        logout.addActionListener(new LogoutListener(this));
        JLabel exitSystem = new JLabel("Exit administration console");
        exitSystem.setHorizontalAlignment(SwingConstants.CENTER);
        exitSystem.setFont(new Font("ariel", Font.BOLD, 18));
        exitSystem.setForeground(Color.BLUE);
        JPanel button = new JPanel();
        button.add(logout);
        logoutPanel.add(button, BorderLayout.CENTER);
        logoutPanel.add(exitSystem, BorderLayout.NORTH);
        cs.gridx = 1;
        cs.gridy = 14;
        cs.gridheight = GridBagConstraints.REMAINDER;
        mainPanel.add(logoutPanel, cs);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
        
        
        
	}
	
	
	
	//getters and setters
	public JButton getViewClient() {
		return viewClient;
	}
	
	public void setViewClient(JButton viewClient) {
		this.viewClient = viewClient;
	}
	
	public JButton getViewDeposits() {
		return viewDeposits;
	}
	
	public void setViewDeposits(JButton viewDeposits) {
		this.viewDeposits = viewDeposits;
	}
	
	public JButton getRemoveClient() {
		return removeClient;
	}
	
	public void setRemoveClient(JButton removeClient) {
		this.removeClient = removeClient;
	}
	
	public JButton getAddNewClient() {
		return addNewClient;
	}
	
	public void setAddNewClient(JButton addNewClient) {
		this.addNewClient = addNewClient;
	}
	
	public JButton getViewAllClients() {
		return viewAllClients;
	}
	
	public void setViewAllClients(JButton viewAllClients) {
		this.viewAllClients = viewAllClients;
	}
	
	public JButton getSystemProperties() {
		return systemProperties;
	}
	
	public void setSystemProperties(JButton systemProperties) {
		this.systemProperties = systemProperties;
	}
	
	public JButton getBankActivities() {
		return bankActivities;
	}
	
	public void setBankActivities(JButton bankActivities) {
		this.bankActivities = bankActivities;
	}

	public JLabel getActivitiesManagement() {
		return activitiesManagement;
	}

	public void setActivitiesManagement(JLabel activitiesManagement) {
		this.activitiesManagement = activitiesManagement;
	}



	public JLabel getPropertiesManagement() {
		return propertiesManagement;
	}



	public void setPropertiesManagement(JLabel propertiesManagement) {
		this.propertiesManagement = propertiesManagement;
	}



	public JTextField getClientId() {
		return clientId;
	}



	public void setClientId(JTextField clientId) {
		this.clientId = clientId;
	}



	public JTextField getActivitiesClientId() {
		return activitiesClientId;
	}



	public void setActivitiesClientId(JTextField activitiesClientId) {
		this.activitiesClientId = activitiesClientId;
	}



	public AdminAction getAdminAction() {
		return adminAction;
	}



	public void setAdminAction(AdminAction adminAction) {
		this.adminAction = adminAction;
	}



	public JLabel getClientsManagement() {
		return clientsManagement;
	}



	public void setClientsManagement(JLabel clientsManagement) {
		this.clientsManagement = clientsManagement;
	}



	public JButton getViewAccount() {
		return viewAccount;
	}



	public void setViewAccount(JButton viewAccount) {
		this.viewAccount = viewAccount;
	}



	public JLabel getLbClientName() {
		return lbClientName;
	}



	public void setLbClientName(JLabel lbClientName) {
		this.lbClientName = lbClientName;
	}



	public JLabel getLbType() {
		return lbType;
	}



	public void setLbType(JLabel lbType) {
		this.lbType = lbType;
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



	public JLabel getLbBalance() {
		return lbBalance;
	}



	public void setLbBalance(JLabel lbBalance) {
		this.lbBalance = lbBalance;
	}



	public JLabel getResultClientName() {
		return resultClientName;
	}



	public void setResultClientName(JLabel resultClientName) {
		this.resultClientName = resultClientName;
	}



	public JLabel getResultlbType() {
		return resultlbType;
	}



	public void setResultlbType(JLabel resultlbType) {
		this.resultlbType = resultlbType;
	}



	public JLabel getResultlbAddress() {
		return resultlbAddress;
	}



	public void setResultlbAddress(JLabel resultlbAddress) {
		this.resultlbAddress = resultlbAddress;
	}



	public JLabel getResultlbEmail() {
		return resultlbEmail;
	}



	public void setResultlbEmail(JLabel resultlbEmail) {
		this.resultlbEmail = resultlbEmail;
	}



	public JLabel getResultlbPhone() {
		return resultlbPhone;
	}



	public void setResultlbPhone(JLabel resultlbPhone) {
		this.resultlbPhone = resultlbPhone;
	}



	public JLabel getResultlbComment() {
		return resultlbComment;
	}



	public void setResultlbComment(JLabel resultlbComment) {
		this.resultlbComment = resultlbComment;
	}



	public JLabel getResultlbBalance() {
		return resultlbBalance;
	}



	public void setResultlbBalance(JLabel resultlbBalance) {
		this.resultlbBalance = resultlbBalance;
	}



	public JButton getEditClientDetails() {
		return editClientDetails;
	}



	public void setEditClientDetails(JButton editClientDetails) {
		this.editClientDetails = editClientDetails;
	}



	public long getCurrentClientId() {
		return currentClientId;
	}



	public void setCurrentClientId(long currentClientId) {
		this.currentClientId = currentClientId;
	}



	public JButton getSendIdForActivities() {
		return sendIdForActivities;
	}



	public void setSendIdForActivities(JButton sendIdForActivities) {
		this.sendIdForActivities = sendIdForActivities;
	}



	public long getActivityClientId() {
		return activityClientId;
	}



	public void setActivityClientId(long activityClientId) {
		this.activityClientId = activityClientId;
	}



	public JButton getLogout() {
		return logout;
	}



	public void setLogout(JButton logout) {
		this.logout = logout;
	}
	
	
	
	

}
