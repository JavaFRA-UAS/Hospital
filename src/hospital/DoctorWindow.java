package hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import java.awt.Panel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorWindow extends JFrame {

	private JPanel contentPane;
	JList listPatients;
	DefaultListModel listModelPatients;

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
		
		listPatients = new JList();
		listModelPatients = new DefaultListModel<Patient>();
		listPatients.setModel(listModelPatients);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout(0, 0));
		panel1.add(listPatients);
		tabbedPane.addTab("Patients", null, panel1, null);
		
		JPanel panel = new JPanel();
		panel1.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("New Patient");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewPatientWindow w = new NewPatientWindow(dw);
				w.setVisible(true);
			}
		});
		panel.add(btnNewButton, BorderLayout.NORTH);
		
		JList list_1 = new JList();
		tabbedPane.addTab("...", null, list_1, null);
		
		fillList();
	}

	void fillList() {
		Database db = Database.getInstance();
		for (Patient pat : db.getPatients()) {
			listModelPatients.addElement(pat);
		}
	}

}
