package hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import java.awt.Panel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class DoctorWindow extends JFrame implements RefreshableWindow {

	private JPanel contentPane;
	DefaultListModel<Patient> listModelPatients;
	DefaultListModel<Doctor> listModelDoctors;
	DefaultListModel<Nurse> listModelNurses;
	JList<Patient> listPatients;
	JList<Doctor> listDoctors;
	JList<Nurse> listNurses;

	/**
	 * Create the frame.
	 */
	public DoctorWindow() {
		final DoctorWindow dw = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 797, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		listModelPatients = new DefaultListModel<Patient>();
		listModelDoctors = new DefaultListModel<Doctor>();
		listModelNurses = new DefaultListModel<Nurse>();

		JPanel panelPatients = new JPanel();
		tabbedPane.addTab("Patients", null, panelPatients, null);
		GridBagLayout gbl_panelPatients = new GridBagLayout();
		gbl_panelPatients.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelPatients.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelPatients.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelPatients.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panelPatients.setLayout(gbl_panelPatients);

		listPatients = new JList<Patient>();
		listPatients.setModel(listModelPatients);
		GridBagConstraints gbc_listPatients = new GridBagConstraints();
		gbc_listPatients.gridwidth = 2;
		gbc_listPatients.fill = GridBagConstraints.BOTH;
		gbc_listPatients.gridx = 0;
		gbc_listPatients.gridy = 0;
		panelPatients.add(listPatients, gbc_listPatients);

		JButton btnNew = new JButton("New Patient");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewPatientWindow w = new NewPatientWindow(dw);
				w.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 1;
		panelPatients.add(btnNew, gbc_btnNew);

		JButton btnDelete = new JButton("Delete Patient");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Patient p = dw.listPatients.getSelectedValue();
				Database db = Database.getInstance();
				db.removePatient(p);
				refresh();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 1;
		panelPatients.add(btnDelete, gbc_btnDelete);

		
		

		
		JPanel panelDoctors = new JPanel();
		tabbedPane.addTab("Doctors", null, panelDoctors, null);
		GridBagLayout gbl_panelDoctors = new GridBagLayout();
		gbl_panelDoctors.columnWidths = new int[] { 0 };
		gbl_panelDoctors.rowHeights = new int[] { 0 };
		gbl_panelDoctors.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelDoctors.rowWeights = new double[] { Double.MIN_VALUE };
		panelDoctors.setLayout(gbl_panelDoctors);

		listDoctors = new JList<Doctor>();
		listDoctors.setModel(listModelDoctors);
		GridBagConstraints gbc_listDoctors = new GridBagConstraints();
		gbc_listDoctors.gridwidth = 2;
		gbc_listDoctors.fill = GridBagConstraints.BOTH;
		gbc_listDoctors.gridx = 0;
		gbc_listDoctors.gridy = 0;
		panelDoctors.add(listDoctors, gbc_listDoctors);

		JButton btnNewDoctor = new JButton("New Doctor");
		btnNewDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewDoctorWindow w = new NewDoctorWindow(dw);
				w.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNewDoctor = new GridBagConstraints();
		gbc_btnNewDoctor.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNewDoctor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewDoctor.gridx = 1;
		gbc_btnNewDoctor.gridy = 1;
		panelDoctors.add(btnNewDoctor, gbc_btnNewDoctor);

		JButton btnDeleteDoctor = new JButton("Delete Doctor");
		btnDeleteDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Doctor p = dw.listDoctors.getSelectedValue();
				Database db = Database.getInstance();
				db.removeDoctor(p);
				refresh();
			}
		});
		GridBagConstraints gbc_btnDeleteDoctor = new GridBagConstraints();
		gbc_btnDeleteDoctor.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDeleteDoctor.gridx = 0;
		gbc_btnDeleteDoctor.gridy = 1;
		panelDoctors.add(btnDeleteDoctor, gbc_btnDeleteDoctor);

		

        JPanel panelNurses = new JPanel();
        tabbedPane.addTab("Nurses", null, panelNurses, null);
        GridBagLayout gbl_panelNurses = new GridBagLayout();
        gbl_panelNurses.columnWidths = new int[] { 0 };
        gbl_panelNurses.rowHeights = new int[] { 0 };
        gbl_panelNurses.columnWeights = new double[] { Double.MIN_VALUE };
        gbl_panelNurses.rowWeights = new double[] { Double.MIN_VALUE };
        panelNurses.setLayout(gbl_panelNurses);

        listNurses = new JList<Nurse>();
        listNurses.setModel(listModelNurses);
        GridBagConstraints gbc_listNurses = new GridBagConstraints();
        gbc_listNurses.gridwidth = 2;
        gbc_listNurses.fill = GridBagConstraints.BOTH;
        gbc_listNurses.gridx = 0;
        gbc_listNurses.gridy = 0;
        panelNurses.add(listNurses, gbc_listNurses);

        JButton btnNewNurse = new JButton("New Nurse");
        btnNewNurse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                        NewNurseWindow w = new NewNurseWindow(dw);
                        w.setVisible(true);
                }
        });
        GridBagConstraints gbc_btnNewNurse = new GridBagConstraints();
        gbc_btnNewNurse.anchor = GridBagConstraints.SOUTHEAST;
        gbc_btnNewNurse.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewNurse.gridx = 1;
        gbc_btnNewNurse.gridy = 1;
        panelNurses.add(btnNewNurse, gbc_btnNewNurse);

        JButton btnDeleteNurse = new JButton("Delete Nurse");
        btnDeleteNurse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                        Nurse p = dw.listNurses.getSelectedValue();
                        Database db = Database.getInstance();
                        db.removeNurse(p);
                        refresh();
                }
        });
        GridBagConstraints gbc_btnDeleteNurse = new GridBagConstraints();
        gbc_btnDeleteNurse.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnDeleteNurse.gridx = 0;
        gbc_btnDeleteNurse.gridy = 1;
        panelNurses.add(btnDeleteNurse, gbc_btnDeleteNurse);




		
		
		fillList();
	}

	void fillList() {
		Database db = Database.getInstance();
		
		listModelPatients.clear();
		for (Patient pat : db.getPatients()) {
			System.out.println("load patient: " + pat.toString());
			listModelPatients.addElement(pat);
		}

		listModelDoctors.clear();
		for (Doctor d : db.getDoctors()) {
			System.out.println("load doctor: " + d.toString());
			listModelDoctors.addElement(d);
		}

		listModelNurses.clear();
		for (Nurse n : db.getNurses()) {
			System.out.println("load nurse: " + n.toString());
			listModelNurses.addElement(n);
		}
	}

	public void refresh() {
		fillList();
	}
}
