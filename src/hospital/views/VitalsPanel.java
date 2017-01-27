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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import hospital.Main;
import hospital.RefreshListener;
import hospital.VitalsSimulation;
import hospital.helper.CustomHeaderRenderer;
import hospital.helper.RandomGenerator;
import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.tablemodel.VitalsTableModel;
import hospital.window.EditPatientWindow;
import hospital.window.MainWindow;
import hospital.window.NewPatientWindow;

public class VitalsPanel extends JPanel implements RefreshListener {

	final MainWindow parentWindow;
	final VitalsTableModel tableModel;
	final JTable table;
	boolean isInsideMouseEvent;
	boolean hasOpenPopupMenu;

	public VitalsPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;
		VitalsSimulation.addRefreshListener(this);
		final VitalsPanel that = this;

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

						if (p.isAlive()) {

							JMenuItem item;

							item = new JMenuItem("Use Defibrillator");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.getHeart().beat();
								}
							});
							popup.add(item);

							item = new JMenuItem("Artificial respiration");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.getLungs().breathe();
								}
							});
							popup.add(item);

							item = new JMenuItem("Raise blood pressure");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.getVitals().getBloodpressure()
											.setSys((int) (p.getVitals().getBloodpressure().getSys() * 1.10));
									p.getVitals().getBloodpressure()
											.setDias((int) (p.getVitals().getBloodpressure().getDias() * 1.10));
								}
							});
							popup.add(item);

							item = new JMenuItem("Lower blood pressure");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.getVitals().getBloodpressure()
											.setSys((int) (p.getVitals().getBloodpressure().getSys() * 0.9));
									p.getVitals().getBloodpressure()
											.setDias((int) (p.getVitals().getBloodpressure().getDias() * 0.9));
								}
							});
							popup.add(item);

							item = new JMenuItem("Heat");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.getVitals().setBodytemperature(p.getVitals().getBodytemperature() + 1);
								}
							});
							popup.add(item);

							item = new JMenuItem("Cool");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.getVitals().setBodytemperature(p.getVitals().getBodytemperature() - 1);
								}
							});
							popup.add(item);

							item = new JMenuItem("Kill");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.die("was killed by " + parentWindow.getCurrentUser().getName());
								}
							});
							popup.add(item);

						} else {
							JMenuItem item = new JMenuItem("Reanimate");
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									p.revive("was reanimated by " + parentWindow.getCurrentUser().getName());
								}
							});
							popup.add(item);
						}

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
		if (!this.hasOpenPopupMenu && !this.isInsideMouseEvent) {
			tableModel.fireTableDataChanged();
		}
	}

	public void refresh() {
		fillList();
	}
}
