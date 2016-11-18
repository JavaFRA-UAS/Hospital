package hospital;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

public class LoginAfterNext {

	private JFrame frmLogin;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginAfterNext window = new LoginAfterNext();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginAfterNext() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 450, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username: ");
		txtUsername.setBounds(10, 42, 66, 20);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password:");
		txtPassword.setBounds(10, 82, 58, 20);
		frmLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(112, 82, 78, 20);
		frmLogin.getContentPane().add(passwordField);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(112, 42, 78, 20);
		frmLogin.getContentPane().add(textPane);
	}
}
