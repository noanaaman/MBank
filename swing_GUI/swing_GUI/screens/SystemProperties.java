package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import bank_beans.Property;
import bank_exceptions.MBankException;
import action_listeners.EditSystemPropertiesListener;
import actions_classes.AdminAction;


public class SystemProperties extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames= {"Property Name", "Property Value", "Property Description"};
	private String[][] allData;
	private JTable propertyTable;
	private JButton jbSave;
	private JButton jbCancel;
	private AdminAction adminAction;
		
	public SystemProperties(MainScreen mainScreen) throws MBankException {
		
		this.adminAction = mainScreen.getAdminAction();
		setTitle("System Properties");
		ArrayList<Property> allPropertis = adminAction.viewAllSystemProperties();
		this.allData = new String[allPropertis.size()][3];
		for (int i = 0; i < allPropertis.size(); i++) {
			String name = allPropertis.get(i).getProp_key();
			String value = allPropertis.get(i).getProp_value();
			String description = allPropertis.get(i).getDescription();
			allData[i][0] = name;
			allData[i][1] = value;
			allData[i][2] = description;
			}
			
		class PropertiesTableModel extends AbstractTableModel {

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
			public boolean isCellEditable(int row, int column) {
				if (column == 1 && row != 8) {
					return true;
				}
				else { 
					return false;		
				}
			}
				
			@Override
			public String getValueAt(int rowIndex, int columnIndex) {
				return allData[rowIndex][columnIndex];
			}
			
			@Override
			public void setValueAt(Object aValue, int rowIndex,	int columnIndex) {
				if (columnIndex == 1){
					System.out.println(aValue);
					allData[rowIndex][columnIndex] = (String)aValue;
				}
			}
				
		}
			
		
		class PropertyCellEditor extends DefaultCellEditor implements TableCellEditor {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public PropertyCellEditor() {
				super(new JTextField());
			}

			@Override
			public Object getCellEditorValue() {
				return super.getCellEditorValue();
			}

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				JTextField tf = (JTextField)super.getTableCellEditorComponent(table, value, isSelected, row, column);
		        tf.setText((String)value);
		        return tf;
		    }

			
						
			@Override
			public boolean stopCellEditing() {
				JTextField tf = (JTextField)getComponent();
				boolean verified = false;
		        String text = tf.getText();
		        try {
		        	Double.parseDouble(text);
		            tf.setBackground(Color.WHITE);
		            verified = true && super.stopCellEditing();		            
		        } catch (NumberFormatException e) {
		            tf.setBackground(Color.RED);
		        }
		        return verified;
			}
			
			
		}
		
		
		this.propertyTable = new JTable(new PropertiesTableModel()){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public TableCellEditor getCellEditor(int row, int column) {
				int modelColumn = convertColumnIndexToModel(column);
				int modelRow = convertRowIndexToModel(row);
				if ((modelColumn == 1) && (modelRow < 15) && (modelRow != 8)) {
					TableCellEditor propertyEditor = new PropertyCellEditor();
					return propertyEditor;
				} else {
					return super.getCellEditor();
				}
			}

			//explaining to the user why they can't edit platinum credit limit
			@Override
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                if (rowIndex == 8) {
                	return "Editing this property requires further actions. Please contact system support.";
                } else if (rowIndex == 15 || rowIndex == 16 ){
                	return "Please contant system support in order to change the admin username/password.";
                }
                else {return super.getToolTipText(e);}
			}
			
			
		};
		propertyTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		propertyTable.setAutoCreateRowSorter(true);
			 
		//setting the JTable column sizes to fit the largest value
		for (int column = 0; column < propertyTable.getColumnCount(); column++)	{
			TableColumn tableColumn = propertyTable.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();
			TableCellRenderer renderer = tableColumn.getHeaderRenderer();
			if (renderer == null) {
				renderer = propertyTable.getTableHeader().getDefaultRenderer();
			}
			java.awt.Component comp = renderer.getTableCellRendererComponent(
			propertyTable, tableColumn.getHeaderValue(), false, false, 0, 0);
			int headerWidth = comp.getPreferredSize().width;
			 
			for (int row = 0; row < propertyTable.getRowCount(); row++) {
				TableCellRenderer cellRenderer = propertyTable.getCellRenderer(row, column);
			    Component c = propertyTable.prepareRenderer(cellRenderer, row, column);
			    int width = c.getPreferredSize().width + propertyTable.getIntercellSpacing().width;
			    preferredWidth = Math.max(preferredWidth, width);
			    preferredWidth = Math.max(preferredWidth, headerWidth) + 1;
			    //  We've exceeded the maximum width, no need to check other rows
			 
			    if (preferredWidth >= maxWidth) {
			    	preferredWidth = maxWidth;
			        break;
			    }
			}
			 
			tableColumn.setPreferredWidth( preferredWidth );
		}
			
		propertyTable.setPreferredScrollableViewportSize(propertyTable.getPreferredSize()); //setting scroll pane to fit the table
		propertyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
		
		
		propertyTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
			    int column = e.getColumn();
			    TableModel model = (TableModel)e.getSource();
			    Object data = model.getValueAt(row, column);
			    model.setValueAt(data, row, column);
			}
		});
		
			
			
		
		JScrollPane scrollPane = new JScrollPane(propertyTable);
			
		this.jbSave = new JButton("Save");
		jbSave.addActionListener(new EditSystemPropertiesListener(this));
		this.jbCancel = new JButton("Cancel");
		jbCancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});
			
		JPanel bp = new JPanel();
	    bp.add(jbSave);
	    bp.add(jbCancel);
			
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		getContentPane().add(bp,BorderLayout.PAGE_END);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();
	    setVisible(true);
			
	
	}
	
	//getters and setters
	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String[][] getAllData() {
		return allData;
	}

	public void setAllData(String[][] allData) {
		this.allData = allData;
	}

	public JTable getPropertyTable() {
		return propertyTable;
	}

	public void setPropertyTable(JTable propertyTable) {
		this.propertyTable = propertyTable;
	}

	public JButton getJbSave() {
		return jbSave;
	}

	public void setJbSave(JButton jbSave) {
		this.jbSave = jbSave;
	}

	public JButton getJbCancel() {
		return jbCancel;
	}

	public void setJbCancel(JButton jbCancel) {
		this.jbCancel = jbCancel;
	}

	public AdminAction getAdminAction() {
		return adminAction;
	}

	public void setAdminAction(AdminAction adminAction) {
		this.adminAction = adminAction;
	}

	
}

