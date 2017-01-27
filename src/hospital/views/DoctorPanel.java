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


import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.window.EditDoctorWindow;
import hospital.window.MainWindow;
import hospital.window.NewDoctorWindow;

public class DoctorPanel extends JPanel {

	final MainWindow parentWindow;
	final DefaultListModel<Doctor> listModelDoctors;
	final JList<Doctor> listDoctors;

	public DoctorPanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;

		listModelDoctors = new DefaultListModel<Doctor>();
		listDoctors = new JList<Doctor>();
		listDoctors.setModel(listModelDoctors);

		GridBagLayout gbl_panelDoctors = new GridBagLayout();
		gbl_panelDoctors.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelDoctors.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelDoctors.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelDoctors.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_panelDoctors);

		GridBagConstraints gbc_listDoctors = new GridBagConstraints();
		gbc_listDoctors.gridwidth = 2;
		gbc_listDoctors.fill = GridBagConstraints.BOTH;
		gbc_listDoctors.gridx = 0;
		gbc_listDoctors.gridy = 0;
		this.add(listDoctors, gbc_listDoctors);

		JButton btnNew = new JButton("New Doctor");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewDoctorWindow w = new NewDoctorWindow(parentWindow);
				w.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		this.add(btnNew, gbc_btnNew);

		JButton btnDelete = new JButton("Delete Doctor");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Doctor p = listDoctors.getSelectedValue();
				p.delete();
				Doctor.getFactory().save(p);
				parentWindow.refresh();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		this.add(btnDelete, gbc_btnDelete);
		listDoctors.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				System.out.println(evt.getClickCount());
				if (evt.getClickCount() == 2) {
					EditDoctorWindow w = new EditDoctorWindow(parentWindow);
					w.setDoctor(listDoctors.getSelectedValue());
					w.setVisible(true);
				}
			}
		});

	}

	void fillList() {
		Doctor.getFactory().loadAll();

		listModelDoctors.clear();
		for (Doctor d : Doctor.getFactory().list()) {
			System.out.println("load doctor: " + d.toString());
			listModelDoctors.addElement(d);
		}
	}

	public void refresh() {
		fillList();
	}
}
