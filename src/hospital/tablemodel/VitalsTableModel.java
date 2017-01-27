package hospital.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import hospital.model.Inpatient;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Vitals;

public class VitalsTableModel extends AbstractTableModel {

	private String[] columnNames = { "Name", "Body temp. °C", "Blood pressure", "Pulse rate", "Breathing rate",
			"State", };

	public List<Patient> getData() {
		List<Patient> l = new ArrayList<Patient>();
		l.addAll(Inpatient.getFactory().list());
		l.addAll(Outpatient.getFactory().list());
		return l;
	}

	public Object[] getRow(Patient p) {
		Vitals v = p.getVitals();
		return new Object[] { p.getName(), String.format("%.2f", v.getBodytemperature()),
				v.getBloodpressure().toString(), String.format("%.0f", v.getPulserate()),
				String.format("%.0f", v.getRatebreathing()), p.isAlive() ? "alive" : "dead" };
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

}
