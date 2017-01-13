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

import hospital.database.Database;
import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.window.EditPatientWindow;
import hospital.window.MainWindow;
import hospital.window.NewPatientWindow;

public class PatientPanel extends JPanel {

	final MainWindow parentWindow;
	final DefaultListModel<Patient> listModelPatients;
	final JList<Patient> listPatients;

	public PatientPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;

		listModelPatients = new DefaultListModel<Patient>();
		listPatients = new JList<Patient>();
		listPatients.setModel(listModelPatients);

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
		this.add(listPatients, gbc_listPatients);

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

		JButton btnDelete = new JButton("Delete Patient");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Patient p = listPatients.getSelectedValue();
				Database db = Database.getInstance();
				db.removePatient(p);
				parentWindow.refresh();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		this.add(btnDelete, gbc_btnDelete);
		listPatients.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				System.out.println(evt.getClickCount());
				if (evt.getClickCount() == 2) {
					EditPatientWindow w = new EditPatientWindow(parentWindow);
					w.setPatient(listPatients.getSelectedValue());
					w.setVisible(true);
				}
			}
		});

	}

	void fillList() {
		Database db = Database.getInstance();
		
		listModelPatients.clear();
		for (Patient pat : db.getPatients()) {
			System.out.println("load patient: " + pat.toString());
			listModelPatients.addElement(pat);
		}
	}

	public void refresh() {
		fillList();
	}
}
