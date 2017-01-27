package hospital.tablemodel;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import hospital.helper.SearchListener;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Room;
import hospital.model.Vitals;

public class PatientTableModel extends AbstractTableModel implements SearchListener {

	private String[] columnNames = { "Name", "Gender", "Date of birth", "Address", "State", "Room", "Doctor" };

	private String filter;

	public List<Patient> getData() {
		String[] words = filter != null ? filter.split("\\s+") : new String[0];

		List<Patient> l = new ArrayList<Patient>();
		for (Patient p : Inpatient.getFactory().list()) {
			if (filter == null) {
				l.add(p);
				continue;
			}

			boolean isFiltered = true;
			for (String word : words) {
				isFiltered &= p.getSearchString().toLowerCase().contains(word.toLowerCase());
			}
			if (isFiltered) {
				l.add(p);
			}
		}
		for (Patient p : Outpatient.getFactory().list()) {
			if (filter == null) {
				l.add(p);
				continue;
			}

			boolean isFiltered = true;
			for (String word : words) {
				isFiltered &= p.getSearchString().toLowerCase().contains(word.toLowerCase());
			}
			if (isFiltered) {
				l.add(p);
			}
		}
		return l;
	}

	public Object[] getRow(Patient p) {
		Vitals v = p.getVitals();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String timeOfBirth = dtf.format(p.getTimeOfBirthAsLocalDate()) + " (age " + p.getAge() + ")";
		String timeOfDeath = p.isAlive() ? "" : dtf.format(p.getTimeOfDeathAsLocalDateTime());

		String address = p.getAddress();
		if (address != null)
			address = address.replaceAll("\n", "; ");

		String state = p.isAlive() ? "alive" : ("dead since " + timeOfDeath);

		String room = "";
		if (p instanceof Inpatient) {
			Room r = ((Inpatient) p).getRoom();
			if (r != null) {
				room = r.getName();
			} else {
				room = "unassigned";
			}
		} else {
			room = "-";
		}

		String doctor = "";
		Doctor doc = p.getDoctor();
		if (doc != null) {
			doctor = doc.getName();
		} else {
			doctor = "unassigned";
		}

		return new Object[] { p.getName(), p.getGender(), timeOfBirth, address, state, room, doctor };
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return getData().size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Patient p = getData().get(row);
		if (p != null) {
			Object[] r = getRow(p);
			if (col < r.length && r[col] != null) {
				return r[col];
			}
		}
		return "";
	}

	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col <= 1) {
			return false;
		} else {
			return false;
		}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		// data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	@Override
	public void onSearch(String text) {
		filter = text;
	}

}
