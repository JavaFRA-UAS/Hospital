package hospital.tablemodel;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import hospital.helper.SearchListener;
import hospital.model.Doctor;
import hospital.model.Employee;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Room;
import hospital.model.Vitals;

public class RoomTableModel extends AbstractTableModel implements SearchListener {

	private Employee currentUser;

	public RoomTableModel(Employee currentUser) {
		this.currentUser = currentUser;
	}

	private String[] columnNames = { "Room No.", "Nurse", "Patients", "Capacity" };

	private String filter;

	public List<Room> getData() {
		String[] words = filter != null ? filter.split("\\s+") : new String[0];

		List<Room> l = new ArrayList<Room>();
		for (Room r : Room.getFactory().list()) {

			if (currentUser instanceof Doctor) {
				Doctor d = (Doctor) currentUser;
				boolean found = false;
				for (Inpatient pi : r.getInpatients()) {
					if (pi.getDoctorId() == d.getId()) {
						found = true;
					}
				}
				if (!found) {
					continue;
				}
			}

			if (currentUser instanceof Nurse) {
				Nurse n = (Nurse) currentUser;
				if (r.getNurseId() != n.getId()) {
					continue;
				}
			}

			if (filter == null) {
				l.add(r);
				continue;
			}

			String searchString = r.getSearchString().toLowerCase();
			Nurse n = r.getNurse();
			if (n != null) {
				searchString += n.getName().toLowerCase();
			}
			for (Inpatient p : r.getInpatients()) {
				searchString += p.getName().toLowerCase();
			}

			boolean isFiltered = true;
			for (String word : words) {
				isFiltered &= searchString.contains(word.toLowerCase());
			}
			if (isFiltered) {
				l.add(r);
			}
		}
		return l;
	}

	public Object[] getRow(Room r) {

		Nurse n = r.getNurse();
		String nurseName = n != null ? n.getName() : "unassigned";

		String patients = "";
		for (Inpatient p : r.getInpatients()) {
			if (patients.length() > 0) {
				patients += ", ";
			}
			patients += p.getName();
		}
		if (patients.length() == 0) {
			patients = "none";
		}

		String capacity = r.getCapacity() + " (" + (100 * r.getInpatients().size() / r.getCapacity()) + " %)";

		return new Object[] { r.getName(), nurseName, patients, capacity };
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
		Room n = getData().get(row);
		if (n != null) {
			Object[] r = getRow(n);
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
