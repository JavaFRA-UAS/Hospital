package hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class EditNurseWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textGender;
	private JTextField textAddress;
	private JTextField textPhone;
	final RefreshableWindow parentWindow;
	private Nurse patient;
	DatePicker datePickerBirthday;

	public EditNurseWindow(RefreshableWindow parentWindow) {
		this.parentWindow = parentWindow;
		final EditNurseWindow window = this;

		setBounds(100, 100, 576, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(76dlu;default):grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));

		JLabel lblName = new JLabel("Name:");
		contentPane.add(lblName, "2, 2, left, default");

		textName = new JTextField();
		contentPane.add(textName, "4, 2, fill, default");
		textName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Age:");
		contentPane.add(lblNewLabel, "2, 6, left, default");

		JLabel lblBirthday = new JLabel("Birthday:");
		contentPane.add(lblBirthday, "2, 4, left, default");

		final JLabel labelAge = new JLabel("");
		contentPane.add(labelAge, "4, 6");

		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
		datePickerBirthday = new DatePicker(dateSettings);
		datePickerBirthday.addDateChangeListener(new DateChangeListener() {

			@Override
			public void dateChanged(DateChangeEvent arg0) {
				labelAge.setText(java.time.temporal.ChronoUnit.YEARS.between(arg0.getNewDate(), LocalDate.now()) + "");
			}

		});
		contentPane.add(datePickerBirthday, "4, 4");

		JLabel lblGender = new JLabel("Gender:");
		contentPane.add(lblGender, "2, 8, left, default");

		textGender = new JTextField();
		contentPane.add(textGender, "4, 8, fill, default");
		textGender.setColumns(10);

		JLabel lblAddress = new JLabel("Address:");
		contentPane.add(lblAddress, "2, 10, left, default");

		textAddress = new JTextField();
		contentPane.add(textAddress, "4, 10, fill, default");
		textAddress.setColumns(10);

		JLabel lblPhone = new JLabel("Phone:");
		contentPane.add(lblPhone, "2, 12, left, default");

		textPhone = new JTextField();
		contentPane.add(textPhone, "4, 12, fill, default");
		textPhone.setColumns(10);

		JButton btnSave = new JButton("Save");
		contentPane.add(btnSave, "4, 20, default, bottom");

		ButtonGroup buttonGroup = new ButtonGroup();

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Nurse p = patient;
				p.setName(textName.getText());
				p.setBirthdayAsLocalDate(datePickerBirthday.getDate());
				p.setAddress(textAddress.getText());
				p.setPhone(textPhone.getText());
				p.setGender(textGender.getText());

				Database db = Database.getInstance();
				db.addNurse(p);
				db.save();

				window.setVisible(false);
				window.parentWindow.refresh();
			}
		});
	}

	public void setNurse(Nurse p) {
		this.patient = p;
		textName.setText(p.getName());
		datePickerBirthday.setDate(p.getBirthdayAsLocalDate());
		textAddress.setText(p.getAddress());
		textPhone.setText(p.getPhone());
		textGender.setText(p.getGender());
	}

}
