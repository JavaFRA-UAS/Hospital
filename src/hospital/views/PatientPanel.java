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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import hospital.RefreshListener;
import hospital.VitalsSimulation;
import hospital.helper.CustomHeaderRenderer;
import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.tablemodel.PatientTableModel;
import hospital.window.EditPatientWindow;
import hospital.window.MainWindow;
import hospital.window.NewPatientWindow;

public class PatientPanel extends JPanel implements RefreshListener {

	final MainWindow parentWindow;
	final PatientTableModel tableModel;
	final JTable table;
	boolean isInsideMouseEvent;
	boolean hasOpenPopupMenu;

	public PatientPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;
		VitalsSimulation.addRefreshListener(this);
		final PatientPanel that = this;

		tableModel = new PatientTableModel();
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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				that.isInsideMouseEvent = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());

				if (r >= 0) {
					final Patient[] patients = tableModel.getData().toArray(new Patient[0]);
					final Patient p = r < patients.length ? patients[r] : null;
					if (p != null) {
						JPopupMenu popup = new JPopupMenu();

						JMenuItem item = new JMenuItem("Edit");
						item.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								EditPatientWindow w = new EditPatientWindow(parentWindow);
								w.setPatient(p);
								w.setVisible(true);
							}
						});
						popup.add(item);

						item = new JMenuItem("Delete");
						item.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								p.delete();
								if (p instanceof Inpatient) {
									Inpatient.getFactory().save((Inpatient) p);
								} else if (p instanceof Outpatient) {
									Outpatient.getFactory().save((Outpatient) p);
								}
								parentWindow.refresh();
							}
						});
						popup.add(item);

						popup.addPopupMenuListener(new PopupMenuListener() {
							public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
								that.hasOpenPopupMenu = false;
							}

							public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
								that.hasOpenPopupMenu = false;
							}

							public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
								that.hasOpenPopupMenu = true;
							}
						});
						popup.show(e.getComponent(), e.getX(), e.getY());
					}
				}

				that.isInsideMouseEvent = false;
			}
		});

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

		JButton btnNew = new JButton("New Patient");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewPatientWindow w = new NewPatientWindow(parentWindow);
				w.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		this.add(btnNew, gbc_btnNew);

		JButton btnDelete = new JButton("X");
		btnDelete.setVisible(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// delete button is now in context menu
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		this.add(btnDelete, gbc_btnDelete);

	}

	void fillList() {
		if (!this.hasOpenPopupMenu && !this.isInsideMouseEvent) {
			tableModel.fireTableDataChanged();
		}
	}

	public void refresh() {
		fillList();
	}
}
