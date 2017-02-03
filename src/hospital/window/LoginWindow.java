package hospital.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import hospital.Log;
import hospital.Main;

import hospital.helper.JPanelWithBackground;
import hospital.model.Administrator;
import hospital.model.Doctor;
import hospital.model.Employee;
import hospital.model.Nurse;

import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.imageio.*;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.DropMode;
import java.awt.SystemColor;

public class LoginWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanelWithBackground contentPane;
	private JComboBox<String> comboBox;
	private JPasswordField passwordField;
	private JLabel lblBenutzer;
	private JLabel lblPassword;

	public LoginWindow() {
		final LoginWindow lw = this;

		setTitle("Hospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 330);
		contentPane = new JPanelWithBackground();
		try {
			contentPane.setImage(ImageIO.read(new File("img/Bild1.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		lblBenutzer = new JLabel("User:");
		contentPane.add(lblBenutzer, "2, 2");
		contentPane.add(comboBox, "6, 2, fill, default");

		// add items to the combo box
		fillCombobox();

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);

		lblPassword = new JLabel("Password:");
		contentPane.add(lblPassword, "2, 4");

		passwordField = new JPasswordField();
		passwordField.addActionListener(this);
		passwordField.setForeground(Color.BLACK);
		passwordField.setBackground(Color.WHITE);
		contentPane.add(passwordField, "6, 4");
		contentPane.add(btnLogin, "6, 6");

	}

	public void actionPerformed(ActionEvent e) {
		if (comboBox.getSelectedIndex() > 0) {

			String name = (String) comboBox.getSelectedItem();
			name = name != null ? name : "";

			Employee user = null;
			for (Administrator a : Administrator.getFactory().list()) {
				if (name.equals("Admin " + a.getName())) {
					user = a;
				}
			}
			for (Doctor doc : Doctor.getFactory().list()) {
				if (name.equals(doc.getName())) {
					user = doc;
				}
			}
			for (Nurse nurse : Nurse.getFactory().list()) {
				if (name.equals("Nurse " + nurse.getName())) {
					user = nurse;
				}
			}

			String password = new String(passwordField.getPassword());
			if (password == null)
				password = "";

			if (user != null && user.getPassword().equals(password)) {
				MainWindow w = new MainWindow(user);
				w.setVisible(true);
				// lw.setVisible(false);

			} else {
				Log.showError("Wrong password");
			}

		}
	}

	synchronized void fillCombobox() {
		System.out.println("fill combobox...");

		Administrator.getFactory().loadAll();
		Doctor.getFactory().loadAll();
		Nurse.getFactory().loadAll();

		comboBox.removeAllItems();
		comboBox.addItem("");

		for (Administrator doc : Administrator.getFactory().list()) {
			comboBox.addItem("Admin " + doc.getName());
		}
		for (Doctor doc : Doctor.getFactory().list()) {
			comboBox.addItem(doc.getName());
		}
		for (Nurse nurse : Nurse.getFactory().list()) {
			comboBox.addItem("Nurse " + nurse.getName());
		}
	}
}
