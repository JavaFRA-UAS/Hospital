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
import hospital.model.Administrator;
import hospital.model.Room;
import hospital.model.Vitals;

public class AdministratorTableModel extends AbstractTableModel implements SearchListener {

	private Employee currentUser;

	public AdministratorTableModel(Employee currentUser) {
		this.currentUser = currentUser;
	}

	private String[] columnNames = { "Name", "Gender", "Date of birth", "Address" };

	private String filter;

	public List<Administrator> getData() {
		String[] words = filter != null ? filter.split("\\s+") : new String[0];

		List<Administrator> l = new ArrayList<Administrator>();
		for (Administrator n : Administrator.getFactory().list()) {
			if (filter == null) {
				l.add(n);
				continue;
			}

			boolean isFiltered = true;
			for (String word : words) {
				isFiltered &= n.getSearchString().toLowerCase().contains(word.toLowerCase());
			}
			if (isFiltered) {
				l.add(n);
			}
		}
		return l;
	}

	public Object[] getRow(Administrator n) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String timeOfBirth = dtf.format(n.getTimeOfBirthAsLocalDate()) + " (age " + n.getAge() + ")";

		String address = n.getAddress();
		if (address != null)
			address = address.replaceAll("\n", "; ");

		return new Object[] { n.getName(), n.getGender(), timeOfBirth, address };
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
		Administrator n = getData().get(row);
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
