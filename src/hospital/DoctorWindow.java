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
	JList<Patient> listPatients;

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
		
		
		
		
		JPanel panelPatients = new JPanel();
		tabbedPane.addTab("Patients", null, panelPatients, null);
		GridBagLayout gbl_panelPatients = new GridBagLayout();
		gbl_panelPatients.columnWidths = new int[]{0, 0};
		gbl_panelPatients.rowHeights = new int[]{0, 0};
		gbl_panelPatients.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelPatients.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelPatients.setLayout(gbl_panelPatients);
		
		listPatients = new JList<Patient>();
		listPatients.setModel(listModelPatients);
		GridBagConstraints gbc_listPatients = new GridBagConstraints();
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
		gbc_btnNew.fill = GridBagConstraints.BOTH;
		gbc_btnNew.gridx = 0;
		gbc_btnNew.gridy = 0;
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
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 0;
		panelPatients.add(btnDelete, gbc_btnDelete);
		

		JPanel panelDoctors = new JPanel();
		tabbedPane.addTab("Doctors", null, panelDoctors, null);
		GridBagLayout gbl_panelDoctors = new GridBagLayout();
		gbl_panelDoctors.columnWidths = new int[]{0};
		gbl_panelDoctors.rowHeights = new int[]{0};
		gbl_panelDoctors.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelDoctors.rowWeights = new double[]{Double.MIN_VALUE};
		panelDoctors.setLayout(gbl_panelDoctors);
		
		fillList();
	}

	void fillList() {
		listModelPatients.clear();
		Database db = Database.getInstance();
		for (Patient pat : db.getPatients()) {
			System.out.println("load patient: "+pat.toString());
			listModelPatients.addElement(pat);
		}
	}

	public void refresh() {
		fillList();
	}
}
