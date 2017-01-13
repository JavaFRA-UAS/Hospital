package hospital.views;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import hospital.database.Database;
import hospital.helper.RandomGenerator;
import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.tablemodel.VitalsTableModel;
import hospital.window.EditPatientWindow;
import hospital.window.MainWindow;
import hospital.window.NewPatientWindow;

public class VitalsPanel extends JPanel {

	final MainWindow parentWindow;
	final VitalsTableModel tableModel;
	final JTable table;

	public static VitalsPanel instance = null;

	public static void onRefresh() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (instance != null) {
					instance.refresh();
				}
			}
		});
	}

	public VitalsPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;
		VitalsPanel.instance = this;

		tableModel = new VitalsTableModel();
		table = new JTable(tableModel) {

			private static final long serialVersionUID = 1L;
			DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();

			{
				renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
			}

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				// return column > 0 ? renderRight : renderLeft;
				return renderCenter;
			}
		};
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(new CustomHeaderRenderer(SwingConstants.CENTER));
		}

		GridBagLayout gbl_panelPatients = new GridBagLayout();
		gbl_panelPatients.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelPatients.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelPatients.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelPatients.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_panelPatients);

		GridBagConstraints gbc_listPatients = new GridBagConstraints();
		gbc_listPatients.gridwidth = 2;
		gbc_listPatients.fill = GridBagConstraints.BOTH;
		gbc_listPatients.gridx = 0;
		gbc_listPatients.gridy = 0;
		this.add(new JScrollPane(table), gbc_listPatients);

		JButton btnNew = new JButton("A");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		this.add(btnNew, gbc_btnNew);

		JButton btnDelete = new JButton("B");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RandomGenerator.causeProblems();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		this.add(btnDelete, gbc_btnDelete);

	}

	void fillList() {
		tableModel.fireTableDataChanged();
	}

	public void refresh() {
		fillList();
	}

	class CustomHeaderRenderer implements TableCellRenderer {
		private int horizontalAlignment = SwingConstants.LEFT;

		public CustomHeaderRenderer(int horizontalAlignment) {
			this.horizontalAlignment = horizontalAlignment;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			TableCellRenderer r = table.getTableHeader().getDefaultRenderer();
			JLabel l = (JLabel) r.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			l.setHorizontalAlignment(horizontalAlignment);
			return l;
		}
	}
}
