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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class DoctorWindow extends JFrame implements RefreshableWindow {

	private JPanel contentPane;
	JList<Patient> listPatients;
	DefaultListModel<Patient> listModelPatients;

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
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Patients", null, panel1, null);
		
		JPanel panel = new JPanel();
		panel1.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnNew = new JButton("New Patient");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewPatientWindow w = new NewPatientWindow(dw);
				w.setVisible(true);
			}
		});
		panel.add(btnNew, BorderLayout.EAST);
		
		JButton btnDelete = new JButton("Delete Patient");
		panel.add(btnDelete, BorderLayout.WEST);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Patient p = dw.listPatients.getSelectedValue();
				Database db = Database.getInstance();
				db.removePatient(p);
				refresh();
			}
		});
		
		listPatients = new JList<Patient>();
		panel.add(listPatients, BorderLayout.NORTH);
		listPatients.setModel(listModelPatients);
		
		listPatients.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		       System.out.println(evt.getClickCount());
		        if (evt.getClickCount() == 2) {
		            int index = dw.listPatients.locationToIndex(evt.getPoint());
		            
		            EditPatientWindow w = new EditPatientWindow(dw);
		            w.setPatient((Patient)listModelPatients.getElementAt(index));
		            w.setVisible(true);
		        }
		    }
		});
		
		JList list_1 = new JList();
		tabbedPane.addTab("...", null, list_1, null);
		
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
