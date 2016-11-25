package hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class NewPatientWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	JFrame parentWindow;


	public NewPatientWindow(JFrame parentWindow) {
		this.parentWindow = parentWindow;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(76dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblName = new JLabel("Name:");
		contentPane.add(lblName, "2, 2, left, default");
		
		textField = new JTextField();
		contentPane.add(textField, "4, 2, fill, default");
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Age:");
		contentPane.add(lblNewLabel, "2, 4, left, default");
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, "4, 4, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		contentPane.add(lblBirthday, "2, 6, left, default");
		
		textField_2 = new JTextField();
		contentPane.add(textField_2, "4, 6, fill, default");
		textField_2.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender:");
		contentPane.add(lblGender, "2, 8, left, default");
		
		textField_3 = new JTextField();
		contentPane.add(textField_3, "4, 8, fill, default");
		textField_3.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		contentPane.add(lblAddress, "2, 10, left, default");
		
		textField_4 = new JTextField();
		contentPane.add(textField_4, "4, 10, fill, default");
		textField_4.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone:");
		contentPane.add(lblPhone, "2, 12, left, default");
		
		textField_5 = new JTextField();
		contentPane.add(textField_5, "4, 12, fill, default");
		textField_5.setColumns(10);
		
		JLabel lblProblem = new JLabel("Problem:");
		contentPane.add(lblProblem, "2, 14");
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, "4, 14, fill, fill");
		
		JButton btnNewButton = new JButton("New button");
		
		JRadioButton rdbtnInpatient = new JRadioButton("Inpatient");
		contentPane.add(rdbtnInpatient, "4, 16");
		
		JRadioButton rdbtOutpatient = new JRadioButton("Outpatient");
		contentPane.add(rdbtOutpatient, "4, 18");
		contentPane.add(btnNewButton, "4, 20, default, bottom");
		

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnInpatient.isSelected()) {
					Inpatient p = new Inpatient();
					p.setName(textField.getText());
					p.setAge(Integer.parseInt(textField_2.getText()));
					p.setProblem(textArea.getText());
				}
				
			}
		});
	}

}
