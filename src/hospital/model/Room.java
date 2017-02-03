package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hospital.Log;
import hospital.database.DatabaseRow;
import hospital.database.Factory;

public class Room implements DatabaseRow {

	private static Factory<Room> factory = new Factory<Room>("room", 1) {
		@Override
		protected Room create(int id) {
			Room o = new Room(id);
			return o;
		}

		@Override
		public void createTable(Statement statement) throws SQLException {
			statement.executeUpdate(
					"create table if not exists room (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, capacity integer, nurseId integer)");
		}
	};

	public static Factory<Room> getFactory() {
		return factory;
	}

	int id;
	boolean isDeleted;
	String name;
	int capacity;
	int nurseId;

	private Room(int id) {
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void delete() {
		isDeleted = true;
	}

	public String getName() {
		return name != null ? name : "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNurseId() {
		return nurseId;
	}

	public void setNurseId(int nurseId) {
		if (nurseId > 0) {
			Nurse n = Nurse.getFactory().get(nurseId);
			List<Room> nurseRooms = n.getRooms();
			if (nurseRooms.size() >= 10) {
				Log.showError("Nurse " + n.getName() + " is already assigned to " + nurseRooms.size() + " rooms.");
			}
		}
		this.nurseId = nurseId;
	}

	public Nurse getNurse() {
		return Nurse.getFactory().get(nurseId);
	}

	public void setNurse(Nurse nurse) {
		if (nurse == null)
			setNurseId(0);
		else
			setNurseId(nurse.getId());
	}

	public List<Inpatient> getInpatients() {
		List<Inpatient> inpatients = new ArrayList<Inpatient>();
		for (Inpatient p : Inpatient.getFactory().list()) {
			if (p.getRoomId() == id) {
				inpatients.add(p);
			}
		}
		return inpatients;
	}

	@Override
	public void load(ResultSet resultset) throws SQLException {
		setDeleted(resultset.getInt("isDeleted") == 0 ? false : true);
		setName(resultset.getString("name"));
		setCapacity(resultset.getInt("capacity"));
		setNurseId(resultset.getInt("nurseId"));
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("REPLACE INTO room (id, isDeleted, name, capacity, nurseId) VALUES (?, ?, ?, ?, ?)");
		statement.setInt(1, getId());
		statement.setInt(2, isDeleted() ? 1 : 0);
		statement.setString(3, getName());
		statement.setInt(4, getCapacity());
		statement.setInt(5, getNurseId());
		statement.executeUpdate();
	}

	public String getSearchString() {
		return name != null ? name : "";
	}

	@Override
	public String toString() {
		return name != null ? name : "";
	}

}
