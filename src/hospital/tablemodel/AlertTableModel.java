package hospital.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import hospital.alert.Alert;
import hospital.alert.AlertHelper;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Room;
import hospital.model.Vitals;

public class AlertTableModel extends AbstractTableModel {

	private String[] columnNames = { "Time", "Patient", "Vital sign", "Current", "Min", "Max", "Problem", "Room",
			"Nurse", "Doctor" };

	public List<Alert> getData() {
		return AlertHelper.getInstance().getAlerts();
	}

	public Object[] getRow(Alert a) {
		int patientId = a.getPatientId();

		Patient p;
		if (Inpatient.getFactory().exists(patientId)) {
			p = Inpatient.getFactory().get(patientId);
		} else if (Outpatient.getFactory().exists(patientId)) {
			p = Outpatient.getFactory().get(patientId);
		} else {
			p = null;
		}

		String room = "-";
		String nurse = "-";
		if (p instanceof Inpatient) {
			Room r = ((Inpatient) p).getRoom();
			if (r != null) {
				room = r.getName();
				Nurse n = r.getNurse();
				if (n != null) {
					nurse = n.getName();
				}
			} else {
				room = "unassigned";
			}
		}

		String doctor = "";
		Doctor doc = p != null ? p.getDoctor() : null;
		if (doc != null) {
			doctor = doc.getName();
		} else {
			doctor = "unassigned";
		}

		if (a.getEntityName().length() > 0) {
			return new Object[] { a.getFormattedTime(), p.getName(), a.getEntityName(),
					String.format("%.2f", a.getEntityValue()), String.format("%.2f", a.getExpectedMin()),
					String.format("%.2f", a.getExpectedMax()), a.getProblem(), room, nurse, doctor };
		} else {
			return new Object[] { a.getFormattedTime(), (p != null ? p.getName() : ""), "", "", "", "", a.getProblem(),
					room, nurse, doctor };
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
