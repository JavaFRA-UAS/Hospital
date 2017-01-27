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
import hospital.window.EditNurseWindow;
import hospital.window.MainWindow;
import hospital.window.NewNurseWindow;

public class NursePanel extends JPanel {

	final MainWindow parentWindow;
	final DefaultListModel<Nurse> listModelNurses;
	final JList<Nurse> listNurses;

	public NursePanel(final MainWindow parentWindow) {
		this.parentWindow = parentWindow;

		listModelNurses = new DefaultListModel<Nurse>();
		listNurses = new JList<Nurse>();
		listNurses.setModel(listModelNurses);

		GridBagLayout gbl_panelNurses = new GridBagLayout();
		gbl_panelNurses.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelNurses.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelNurses.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelNurses.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_panelNurses);

		GridBagConstraints gbc_listNurses = new GridBagConstraints();
		gbc_listNurses.gridwidth = 2;
		gbc_listNurses.fill = GridBagConstraints.BOTH;
		gbc_listNurses.gridx = 0;
		gbc_listNurses.gridy = 0;
		this.add(listNurses, gbc_listNurses);

		JButton btnNew = new JButton("New Nurse");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewNurseWindow w = new NewNurseWindow(parentWindow);
				w.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		this.add(btnNew, gbc_btnNew);

		JButton btnDelete = new JButton("Delete Nurse");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Nurse p = listNurses.getSelectedValue();
				p.delete();
				Nurse.getFactory().save(p);
				parentWindow.refresh();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		this.add(btnDelete, gbc_btnDelete);
		listNurses.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				System.out.println(evt.getClickCount());
				if (evt.getClickCount() == 2) {
					EditNurseWindow w = new EditNurseWindow(parentWindow);
					w.setNurse(listNurses.getSelectedValue());
					w.setVisible(true);
				}
			}
		});

	}

	void fillList() {
		Nurse.getFactory().loadAll();
		
		listModelNurses.clear();
		for (Nurse n : Nurse.getFactory().list()) {
			System.out.println("load nurse: " + n.toString());
			listModelNurses.addElement(n);
		}
	}

	public void refresh() {
		fillList();
	}
}
