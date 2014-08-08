package screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import actions_classes.AdminAction;
import bank_beans.Client;
import bank_exceptions.MBankException;

public class ViewClients {
	
	private AdminAction adminAction;
		
	public ViewClients(MainScreen mainScreen) throws MBankException {
		this.adminAction = mainScreen.getAdminAction();
		
		final String[] columnNames= {"Client ID", "Client Name", "Password", "Client Type", "Address", "Email", "Phone Number", "Comment"};
		ArrayList<Client> allClients = adminAction.viewAllClientsDetails();
		final String[][] allData = new String[allClients.size()][8];
		for (int i = 0; i < allClients.size(); i++) {
			String clientId = Long.toString(allClients.get(i).getClient_id());
			String clientName = allClients.get(i).getClient_name();
			String password = allClients.get(i).getPassword();
			String type = allClients.get(i).getType().getTypeStringValue();
			String address = allClients.get(i).getAddress();
			String email = allClients.get(i).getEmail();
			String phone = allClients.get(i).getPhone();
			String comment = allClients.get(i).getComment();
			allData[i][0] = clientId;
			allData[i][1] = clientName;
			allData[i][2] = password;
			allData[i][3] = type;
			allData[i][4] = address;
			allData[i][5] = email;
			allData[i][6] = phone;
			allData[i][7] = comment;
			}
			
			class ClientsTableModel extends AbstractTableModel {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public int getColumnCount() {
					return columnNames.length;
				}

				@Override
				public int getRowCount() {
					return allData.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					// TODO Auto-generated method stub
					return allData[rowIndex][columnIndex];
				}
				
				@Override
				public String getColumnName(int column) {
					// TODO Auto-generated method stub
					return columnNames[column].toString();
				}
		

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
				
				
				
			}
			JTable clientsTable = new JTable(new ClientsTableModel());
			clientsTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			clientsTable.setAutoCreateRowSorter(true);
			
			 
			for (int column = 0; column < clientsTable.getColumnCount(); column++)
			{
			    TableColumn tableColumn = clientsTable.getColumnModel().getColumn(column);
			    int preferredWidth = tableColumn.getMinWidth();
			    int maxWidth = tableColumn.getMaxWidth();
			    TableCellRenderer renderer = tableColumn.getHeaderRenderer();
			    if (renderer == null) {
			        renderer = clientsTable.getTableHeader().getDefaultRenderer();
			    }
			    java.awt.Component comp = renderer.getTableCellRendererComponent(
			    		clientsTable, tableColumn.getHeaderValue(), false, false, 0, 0);
			   int headerWidth = comp.getPreferredSize().width;
			 
			    for (int row = 0; row < clientsTable.getRowCount(); row++)
			    {
			        TableCellRenderer cellRenderer = clientsTable.getCellRenderer(row, column);
			        Component c = clientsTable.prepareRenderer(cellRenderer, row, column);
			        int width = c.getPreferredSize().width + clientsTable.getIntercellSpacing().width;
			        preferredWidth = Math.max(preferredWidth, width);
			        preferredWidth = Math.max(preferredWidth, headerWidth) + 1;
			        //  We've exceeded the maximum width, no need to check other rows
			 
			        if (preferredWidth >= maxWidth)
			        {
			            preferredWidth = maxWidth;
			            break;
			        }
			    }
			 
			    tableColumn.setPreferredWidth( preferredWidth );
			}
			
			clientsTable.setPreferredScrollableViewportSize(clientsTable.getPreferredSize());
			
			
			final JFrame frame = new JFrame("All MBank Clients");
			
			JScrollPane scrollPane = new JScrollPane(clientsTable);
			
			JButton jbCancel = new JButton("OK");
			jbCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					
				}
			});
			JPanel bp = new JPanel();
	        bp.add(jbCancel);
			
			frame.getContentPane().setLayout(new BorderLayout());
			frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
			frame.getContentPane().add(bp,BorderLayout.PAGE_END);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.pack();
	        frame.setVisible(true);
			
		
	}
	
}
