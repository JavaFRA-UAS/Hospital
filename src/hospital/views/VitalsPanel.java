package hospital.views;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

import hospital.Log;
import hospital.Main;
import hospital.RefreshListener;
import hospital.VitalsSimulation;
import hospital.alert.Alert;
import hospital.alert.AlertHelper;
import hospital.helper.CustomHeaderRenderer;
import hospital.helper.RandomGenerator;
import hospital.helper.RefreshableWindow;
import hospital.helper.SearchListener;
import hospital.helper.SearchPanel;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Room;
import hospital.tablemodel.VitalsTableModel;
import hospital.window.EditPatientWindow;
import hospital.window.MainWindow;
import hospital.window.NewPatientWindow;

public class VitalsPanel extends JPanel implements RefreshListener, SearchListener {

	final MainWindow parentWindow;
	final VitalsTableModel tableModel;
	final JTable table;
	boolean isInsideMouseEvent;
	boolean hasOpenPopupMenu;

	public VitalsPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;
		VitalsSimulation.addRefreshListener(this);
		final VitalsPanel that = this;

		tableModel = new VitalsTableModel(parentWindow.getCurrentUser());
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
					final List<Patient> patients = tableModel.getData();
					final Patient p = r < patients.size() ? patients.get(r) : null;
					if (p != null) {
						JPopupMenu popup = new JPopupMenu();

						if (p.isAlive()) {

							JMenuItem item;

							final Nurse nurse = (p instanceof Inpatient) ? ((Inpatient) p).getNurse() : null;
							final String nurseName = nurse != null ? nurse.getName() : "";

							if(parentWindow.getCurrentUser() instanceof Doctor){
							item = new JMenuItem("Alert Nurse " + nurseName);
							item.addActionListener(new java.awt.event.ActionListener() {
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									if (nurse != null) {
										String alertStr = "alert by " + parentWindow.getCurrentUser().getName();
										AlertHelper.getInstance()
												.createAlert(new Alert(p.getId(), alertStr, 0, 0, 0, alertStr));
									} else {
										if (p instanceof Outpatient) {
											Log.showError("Patient " + p.getName()
													+ " is an outpatient. Outpatients don't have rooms, so there are no nurses which can be notified.");
										} else if (p instanceof Inpatient) {
											Inpatient pi = (Inpatient) p;
											Room room = pi.getRoom();
											if (room != null) {
												Log.showError(
														"Patient " + p.getName() + " is in room \"" + room.getName()
																+ "\", but no nurse is assigned to that room.");
											} else {
												Log.showError("Patient " + p.getName()
														+ " is not assigned to a room, so there is no nurse which could be notified.");
											}
										}
									}
								}

							});
							popup.add(item);
							}

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

		JButton btnNew = new JButton("VItalBooster");
		btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				RandomGenerator.causeProblems();
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		this.add(btnNew, gbc_btnNew);

		SearchPanel searchPanel = new SearchPanel();
		searchPanel.addSearchListener(this);
		GridBagConstraints gbc_searchPanel = new GridBagConstraints();
		gbc_searchPanel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_searchPanel.gridx = 0;
		gbc_searchPanel.gridy = 1;
		this.add(searchPanel, gbc_searchPanel);

	}

	void fillList() {
		if (!this.hasOpenPopupMenu && !this.isInsideMouseEvent) {
			tableModel.fireTableDataChanged();
		}
	}

	public synchronized void refresh() {
		fillList();
	}

	@Override
	public void onSearch(String text) {
		tableModel.onSearch(text);
		refresh();
	}

	public void onWindowClosing() {
		tableModel.onWindowClosing();
	}
}
