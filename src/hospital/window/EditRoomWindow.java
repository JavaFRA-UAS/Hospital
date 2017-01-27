package hospital.window;

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


import hospital.helper.RefreshableWindow;
import hospital.model.Room;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class EditRoomWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textCapacity;
	final RefreshableWindow parentWindow;
	private Room row;

	public EditRoomWindow(RefreshableWindow parentWindow) {
		this.parentWindow = parentWindow;
		final EditRoomWindow window = this;

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

		JLabel lblName = new JLabel("Number:");
		contentPane.add(lblName, "2, 2, left, default");

		textName = new JTextField();
		contentPane.add(textName, "4, 2, fill, default");
		textName.setColumns(10);

		JLabel lblCapacity = new JLabel("Capacity:");
		contentPane.add(lblCapacity, "2, 4, left, default");

		textCapacity = new JTextField();
		contentPane.add(textCapacity, "4, 4, fill, default");
		textCapacity.setColumns(10);

		JButton btnSave = new JButton("Save");
		contentPane.add(btnSave, "4, 20, default, bottom");

		ButtonGroup buttonGroup = new ButtonGroup();

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				row.setName(textName.getText());
				row.setCapacity(Integer.parseInt(textCapacity.getText()));

				Room.getFactory().save(row);

				window.setVisible(false);
				window.parentWindow.refresh();
			}
		});
	}

	public void setRoom(Room p) {
		this.row = p;
		textName.setText(p.getName());
		textCapacity.setText(p.getCapacity() + "");
	}

}
