package screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import actions_classes.AdminAction;
import bank_beans.Activity;
import bank_exceptions.MBankException;

public class ViewClientActivities extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AdminAction adminAction;

	public ViewClientActivities(MainScreen mainScreen) throws MBankException {
		this.adminAction = mainScreen.getAdminAction();
		
		System.out.println(mainScreen.getActivityClientId());
		
		final String[] columnNames = {"Activity ID", "Amount", "Activity Date", "Commission", "Description"};
		ArrayList<Activity> clientActivities = adminAction.viewClientActivities(mainScreen.getActivityClientId());
		if (clientActivities != null) {
			final String[][] allData = new String[clientActivities.size()][5];
			for (int i = 0; i < clientActivities.size(); i++) {
				String activityId = Long.toString(clientActivities.get(i).getId());
				String amount = Double.toString(clientActivities.get(i).getAmount());
				String activityDate = new SimpleDateFormat("yyyy-MM-dd").format(clientActivities.get(i).getActivity_date().getTime());
				String commission = Double.toString(clientActivities.get(i).getCommission());
				String description = clientActivities.get(i).getDescription();
				
				allData[i][0] = activityId;
				allData[i][1] = amount;
				allData[i][2] = activityDate;
				allData[i][3] = commission;
				allData[i][4] = description;
			}

			class ClientActivitiesTableModel extends AbstractTableModel {

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
			JTable clientActivitiesTable = new JTable(new ClientActivitiesTableModel());
			clientActivitiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			for (int column = 0; column < clientActivitiesTable.getColumnCount(); column++) {
				TableColumn tableColumn = clientActivitiesTable.getColumnModel().getColumn(
						column);
				int preferredWidth = tableColumn.getMinWidth();
				int maxWidth = tableColumn.getMaxWidth();
				TableCellRenderer renderer = tableColumn.getHeaderRenderer();
				if (renderer == null) {
					renderer = clientActivitiesTable.getTableHeader().getDefaultRenderer();
				}
				java.awt.Component comp = renderer.getTableCellRendererComponent(
						clientActivitiesTable, tableColumn.getHeaderValue(), false, false,
						0, 0);
				int headerWidth = comp.getPreferredSize().width;

				for (int row = 0; row < clientActivitiesTable.getRowCount(); row++) {
					TableCellRenderer cellRenderer = clientActivitiesTable.getCellRenderer(
							row, column);
					Component c = clientActivitiesTable.prepareRenderer(cellRenderer, row,
							column);
					int width = c.getPreferredSize().width
							+ clientActivitiesTable.getIntercellSpacing().width;
					preferredWidth = Math.max(preferredWidth, width);
					preferredWidth = Math.max(preferredWidth, headerWidth) + 1;
					// We've exceeded the maximum width, no need to check other rows

					if (preferredWidth >= maxWidth) {
						preferredWidth = maxWidth;
						break;
					}
				}

				tableColumn.setPreferredWidth(preferredWidth);
			}

			clientActivitiesTable.setPreferredScrollableViewportSize(clientActivitiesTable.getPreferredSize());
			clientActivitiesTable.setAutoCreateRowSorter(true);
			JScrollPane scrollPane = new JScrollPane(clientActivitiesTable);
			setTitle("Client Activities");
			
			JButton jbCancel = new JButton("OK");
			jbCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					
				}
			});
			JPanel bp = new JPanel();
	        bp.add(jbCancel);
			JLabel head = new JLabel("All activities done by client " + mainScreen.getActivityClientId() + ":");
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(head, BorderLayout.NORTH);
			getContentPane().add(scrollPane,BorderLayout.CENTER);
			getContentPane().add(bp,BorderLayout.PAGE_END);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        pack();
	        setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "There are no recorded activities for this client.", "No Activities", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	public AdminAction getAdminAction() {
		return adminAction;
	}

	public void setAdminAction(AdminAction adminAction) {
		this.adminAction = adminAction;
	}


}
