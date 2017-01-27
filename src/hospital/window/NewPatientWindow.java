package hospital.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import hospital.helper.RefreshableWindow;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Outpatient;
import hospital.model.Room;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class NewPatientWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textGender;
	private JTextField textAddress;
	private JTextField textPhone;
	private JRadioButton rdbtnInpatient;
	private JRadioButton rdbtnOutpatient;
	private JLabel lblRoom;
	private JComboBox<String> boxRoom;
	private JLabel lblDoctor;
	private JComboBox<String> boxDoctor;
	final RefreshableWindow parentWindow;

	public NewPatientWindow(RefreshableWindow parentWindow) {
		this.parentWindow = parentWindow;
		final NewPatientWindow window = this;

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

		JLabel lblTimeOfBirth = new JLabel("TimeOfBirth:");
		contentPane.add(lblTimeOfBirth, "2, 4, left, default");

		final JLabel labelAge = new JLabel("");
		contentPane.add(labelAge, "4, 6");

		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
		final DatePicker datePickerTimeOfBirth = new DatePicker(dateSettings);
		datePickerTimeOfBirth.addDateChangeListener(new DateChangeListener() {

			@Override
			public void dateChanged(DateChangeEvent arg0) {
				labelAge.setText(java.time.temporal.ChronoUnit.YEARS.between(arg0.getNewDate(), LocalDate.now()) + "");
			}

		});
		contentPane.add(datePickerTimeOfBirth, "4, 4");

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

		JLabel lblProblem = new JLabel("Problem:");
		contentPane.add(lblProblem, "2, 14");

		final JTextArea textProblem = new JTextArea();
		textProblem.setLineWrap(true);
		contentPane.add(textProblem, "4, 14, fill, fill");

		JButton btnSave = new JButton("Save");

		rdbtnInpatient = new JRadioButton("Inpatient");
		contentPane.add(rdbtnInpatient, "4, 16");

		rdbtnOutpatient = new JRadioButton("Outpatient");
		contentPane.add(rdbtnOutpatient, "4, 18");
		contentPane.add(btnSave, "4, 20, default, bottom");

		lblRoom = new JLabel("Room:");
		contentPane.add(lblRoom, "2, 20, left, default");

		boxRoom = new JComboBox<String>();
		contentPane.add(boxRoom, "4, 20, fill, default");

		lblDoctor = new JLabel("Doctor");
		contentPane.add(lblDoctor, "2, 22, left, default");

		boxDoctor = new JComboBox<String>();
		contentPane.add(boxDoctor, "4, 22, fill, default");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnInpatient);
		buttonGroup.add(rdbtnOutpatient);

		rdbtnInpatient.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changEvent) {
				updateVisibility();
			}
		});
		rdbtnOutpatient.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changEvent) {
				updateVisibility();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnInpatient.isSelected()) {
					Inpatient p = Inpatient.getFactory().get(Inpatient.getFactory().getNewId());
					p.setName(textName.getText());
					p.setTimeOfBirthAsLocalDate(datePickerTimeOfBirth.getDate());
					p.setProblem(textProblem.getText());
					p.setAddress(textAddress.getText());
					p.setPhone(textPhone.getText());
					p.setGender(textGender.getText());
					p.setDoctor(Doctor.getFactory().get((String) boxDoctor.getSelectedItem()));
					p.setRoom(Room.getFactory().get((String) boxRoom.getSelectedItem()));

					Inpatient.getFactory().save(p);

					window.setVisible(false);
					window.parentWindow.refresh();

				} else if (rdbtnOutpatient.isSelected()) {
					Outpatient p = Outpatient.getFactory().get(Outpatient.getFactory().getNewId());
					p.setName(textName.getText());
					p.setTimeOfBirthAsLocalDate(datePickerTimeOfBirth.getDate());
					p.setProblem(textProblem.getText());
					p.setAddress(textAddress.getText());
					p.setPhone(textPhone.getText());
					p.setGender(textGender.getText());
					p.setDoctor(Doctor.getFactory().get((String) boxDoctor.getSelectedItem()));

					Outpatient.getFactory().save(p);

					window.setVisible(false);
					window.parentWindow.refresh();

				} else {
					JOptionPane.showMessageDialog(window, "The patient must be an inpatient or an outpatient.");
				}
			}
		});

		boxRoom.addItem("");
		for (Room room : Room.getFactory().list()) {
			boxRoom.addItem(room.getName());
		}

		boxDoctor.addItem("");
		for (Doctor doc : Doctor.getFactory().list()) {
			boxDoctor.addItem(doc.getName());
		}
	}

	private void updateVisibility() {
		lblRoom.setVisible(rdbtnInpatient.isSelected());
		boxRoom.setVisible(rdbtnInpatient.isSelected());
	}

}
