package hospital.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;

import hospital.model.Inpatient;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Room;
import hospital.model.Vitals;

public class AlertTableModel extends AbstractTableModel {

	private String[] columnNames = { "Time", "Patient", "Vital sign", "Current", "Min", "Max", "Problem", "Room" };

	public List<Alert> getData() {
		return AlertHelper.getInstance().getAlerts();
	}

	public Object[] getRow(Alert a) {
		int patientId = a.getPatientId();
		Patient p;
		Room room;
		String roomString;
		if (Inpatient.getFactory().exists(patientId)) {
			p = Inpatient.getFactory().get(patientId);
			room = ((Inpatient) p).getRoom();
			roomString = (room != null ? room.getName() : "(unassigned)");
		} else if (Outpatient.getFactory().exists(patientId)) {
			p = Outpatient.getFactory().get(patientId);
			room = null;
			roomString = "-";
		} else {
			p = null;
			room = null;
			roomString = null;
		}

		if (a.getEntityName().length() > 0) {
			return new Object[] { a.getFormattedTime(), roomString, a.getEntityName(),
					String.format("%.2f", a.getEntityValue()), String.format("%.2f", a.getExpectedMin()),
					String.format("%.2f", a.getExpectedMax()), a.getProblem(), roomString };
		} else {
			return new Object[] { a.getFormattedTime(), (p != null ? p.getName() : ""), "", "", "", "", a.getProblem(),
					roomString };
		}
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
		Alert p = getData().get(row);
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

}
