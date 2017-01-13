package hospital.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import hospital.database.Database;
import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.model.table.VitalsTableModel;
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
			DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();
			
			{
				renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
			}

			@Override
			public TableCellRenderer getCellRenderer(int arg0, int arg1) {
				return renderRight;
			}
		};

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
				// NewPatientWindow w = new NewPatientWindow(parentWindow);
				// w.setVisible(true);
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
				// Patient p = listPatients.getSelectedValue();
				// Database db = Database.getInstance();
				// db.removePatient(p);
				parentWindow.refresh();
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
}
