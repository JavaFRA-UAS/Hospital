package hospital.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hospital.Main;

import hospital.helper.RefreshableWindow;
import hospital.model.Administrator;
import hospital.model.Doctor;
import hospital.model.Employee;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.views.AdministratorPanel;
import hospital.views.AlertPanel;
import hospital.views.DoctorPanel;
import hospital.views.NursePanel;
import hospital.views.PatientPanel;
import hospital.views.RoomPanel;
import hospital.views.VitalsPanel;

import javax.swing.JTabbedPane;
import javax.swing.JList;
import java.awt.Panel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class MainWindow extends JFrame implements RefreshableWindow {

	private JPanel contentPane;
	private PatientPanel panelPatient;
	private DoctorPanel panelDoctor;
	private NursePanel panelNurse;
	private AdministratorPanel panelAdministrator;
	private RoomPanel panelRoom;
	private VitalsPanel panelVitals;
	private AlertPanel panelAlert;
	private Employee currentUser;

	public MainWindow(Employee user) {
		setTitle("Hospital Management System (" + user.getName() + ")");
		final MainWindow dw = this;
		this.currentUser = user;

		final int displayWidth = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().width;
		final int displayHeight = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().height;

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, (displayHeight - 550) / 2, displayWidth - 100, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		if (user instanceof Administrator) {

			panelPatient = new PatientPanel(this);
			tabbedPane.addTab("Patients", null, panelPatient, null);

			panelDoctor = new DoctorPanel(this);
			tabbedPane.addTab("Doctors", null, panelDoctor, null);

			panelNurse = new NursePanel(this);
			tabbedPane.addTab("Nurses", null, panelNurse, null);

			panelAdministrator = new AdministratorPanel(this);
			tabbedPane.addTab("Admins", null, panelAdministrator, null);

			panelRoom = new RoomPanel(this);
			tabbedPane.addTab("Rooms", null, panelRoom, null);

			panelVitals = new VitalsPanel(this);
			tabbedPane.addTab("Vitals", null, panelVitals, null);

			panelAlert = new AlertPanel(this);
			tabbedPane.addTab("Alerts", null, panelAlert, null);

		} else if (user instanceof Doctor) {

			panelPatient = new PatientPanel(this);
			tabbedPane.addTab("Patients", null, panelPatient, null);

			panelRoom = new RoomPanel(this);
			tabbedPane.addTab("Rooms", null, panelRoom, null);

			panelVitals = new VitalsPanel(this);
			tabbedPane.addTab("Vitals", null, panelVitals, null);

			panelAlert = new AlertPanel(this);
			tabbedPane.addTab("Alerts", null, panelAlert, null);

		} else if (user instanceof Nurse) {

			panelPatient = new PatientPanel(this);
			tabbedPane.addTab("Patients", null, panelPatient, null);

			panelRoom = new RoomPanel(this);
			tabbedPane.addTab("Rooms", null, panelRoom, null);

			panelVitals = new VitalsPanel(this);
			tabbedPane.addTab("Vitals", null, panelVitals, null);

			panelAlert = new AlertPanel(this);
			tabbedPane.addTab("Alerts", null, panelAlert, null);

		}

		fillList();
		

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt){
                if (panelVitals != null) {
                	panelVitals.onWindowClosing();
                }
            }
        });
	}

	public Employee getCurrentUser() {
		return this.currentUser;
	}

	void fillList() {
		if (panelPatient != null)
			panelPatient.refresh();
		if (panelDoctor != null)
			panelDoctor.refresh();
		if (panelNurse != null)
			panelNurse.refresh();
		if (panelRoom != null)
			panelRoom.refresh();
		if (panelVitals != null)
			panelVitals.refresh();
		if (panelAlert != null)
			panelAlert.refresh();
	}

	public void refresh() {
		fillList();
	}
}
