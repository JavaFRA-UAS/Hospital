package hospital;

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
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.imageio.*;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.DropMode;
import java.awt.SystemColor;

public class LoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelWithBackground contentPane;
	private JComboBox<String> comboBox;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */

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
		contentPane.add(comboBox, "6, 2, fill, default");

		// add items to the combo box
		fillCombobox();

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() > 0) {
					DoctorWindow w = new DoctorWindow();
					w.setVisible(true);
					lw.setVisible(false);
				}
			}
		});

		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBackground(Color.WHITE);
		contentPane.add(passwordField, "6, 4");
		contentPane.add(btnLogin, "6, 6");
	}

	void fillCombobox() {
		comboBox.addItem("");

		Database db = Database.getInstance();
		for (Doctor doc : db.getDoctors()) {
			comboBox.addItem(doc.getName());
		}
		for (Nurse nurse : db.getNurses()) {
			comboBox.addItem(nurse.getName());
		}
	}
}
