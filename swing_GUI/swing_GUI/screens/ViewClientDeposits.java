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



import bank_beans.Deposit;
import bank_exceptions.MBankException;
import actions_classes.AdminAction;

public class ViewClientDeposits extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AdminAction adminAction;

	public ViewClientDeposits(MainScreen mainScreen) throws MBankException {
		this.adminAction = mainScreen.getAdminAction();

		final String[] columnNames = {"Deposit ID", "Balance", "Deposit Type", "Estimated Balance", "Opening Date", "Closing Date"};
		ArrayList<Deposit> clientDeposits = adminAction.viewClientDeposits(mainScreen.getCurrentClientId());
		if (clientDeposits != null) {
			final String[][] allData = new String[clientDeposits.size()][6];
			for (int i = 0; i < clientDeposits.size(); i++) {
				String depositId = Long.toString(clientDeposits.get(i).getDeposit_id());
				String balance = Double.toString(clientDeposits.get(i).getBalance());
				String type = clientDeposits.get(i).getType().toString();
				String estimatedBalance = Double.toString(clientDeposits.get(i).getEstimated_balance());
				String openingDate = new SimpleDateFormat("yyyy-MM-dd").format(clientDeposits.get(i).getOpening_date().getTime());
				String closingDate = new SimpleDateFormat("yyyy-MM-dd").format(clientDeposits.get(i).getClosing_date().getTime());
				allData[i][0] = depositId;
				allData[i][1] = balance;
				allData[i][2] = type;
				allData[i][3] = estimatedBalance;
				allData[i][4] = openingDate;
				allData[i][5] = closingDate;
			}

			class ClientDepositsTableModel extends AbstractTableModel {

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
					return allData[rowIndex][columnIndex];
				}

				@Override
				public String getColumnName(int column) {
					return columnNames[column].toString();
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			}
			JTable depositsTable = new JTable(new ClientDepositsTableModel());
			depositsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			for (int column = 0; column < depositsTable.getColumnCount(); column++) {
				TableColumn tableColumn = depositsTable.getColumnModel().getColumn(
						column);
				int preferredWidth = tableColumn.getMinWidth();
				int maxWidth = tableColumn.getMaxWidth();
				TableCellRenderer renderer = tableColumn.getHeaderRenderer();
				if (renderer == null) {
					renderer = depositsTable.getTableHeader().getDefaultRenderer();
				}
				java.awt.Component comp = renderer.getTableCellRendererComponent(
						depositsTable, tableColumn.getHeaderValue(), false, false,
						0, 0);
				int headerWidth = comp.getPreferredSize().width;

				for (int row = 0; row < depositsTable.getRowCount(); row++) {
					TableCellRenderer cellRenderer = depositsTable.getCellRenderer(
							row, column);
					Component c = depositsTable.prepareRenderer(cellRenderer, row,
							column);
					int width = c.getPreferredSize().width
							+ depositsTable.getIntercellSpacing().width;
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

			depositsTable.setPreferredScrollableViewportSize(depositsTable.getPreferredSize());
			depositsTable.setAutoCreateRowSorter(true);
			JScrollPane scrollPane = new JScrollPane(depositsTable);
			setTitle("Client Deposits");
			
			JButton jbCancel = new JButton("OK");
			jbCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					
				}
			});
			JPanel bp = new JPanel();
	        bp.add(jbCancel);
			JLabel head = new JLabel("All existing deposits for client " + mainScreen.getCurrentClientId() + ":");
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(head, BorderLayout.NORTH);
			getContentPane().add(scrollPane,BorderLayout.CENTER);
			getContentPane().add(bp,BorderLayout.PAGE_END);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        pack();
	        setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "This client has no deposits.", "No Deposits To Show", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	public AdminAction getAdminAction() {
		return adminAction;
	}

	public void setAdminAction(AdminAction adminAction) {
		this.adminAction = adminAction;
	}

}
