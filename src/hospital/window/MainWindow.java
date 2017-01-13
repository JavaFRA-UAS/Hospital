package hospital.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hospital.database.Database;
import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.views.DoctorPanel;
import hospital.views.NursePanel;
import hospital.views.PatientPanel;
import hospital.views.VitalsPanel;

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

public class MainWindow extends JFrame implements RefreshableWindow {

	private JPanel contentPane;
	private PatientPanel panelPatient;
	private DoctorPanel panelDoctor;
	private NursePanel panelNurse;
	private VitalsPanel panelVitals;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		final MainWindow dw = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 797, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		

		panelPatient = new PatientPanel(this);
		tabbedPane.addTab("Patients", null, panelPatient, null);

		panelDoctor = new DoctorPanel(this);
		tabbedPane.addTab("Doctors", null, panelDoctor, null);

		panelNurse = new NursePanel(this);
		tabbedPane.addTab("Nurses", null, panelNurse, null);

		panelVitals = new VitalsPanel(this);
		tabbedPane.addTab("Vitals", null, panelVitals, null);
		

		
		fillList();
	}

	void fillList() {
		Database db = Database.getInstance();

		panelPatient.refresh();
		panelDoctor.refresh();
		panelNurse.refresh();
		panelVitals.refresh();
	}

	public void refresh() {
		fillList();
	}
}
