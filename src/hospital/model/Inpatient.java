package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hospital.database.DatabaseRow;
import hospital.database.Factory;

public class Inpatient extends Patient implements DatabaseRow {

	private static Factory<Inpatient> factory = new Factory<Inpatient>("inpatient", 3000) {
		@Override
		protected Inpatient create(int id) {
			Inpatient o = new Inpatient(id);
			return o;
		}

		@Override
		public void createTable(Statement statement) throws SQLException {
			statement.executeUpdate(
					"create table if not exists inpatient (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, address string, timeOfBirth integer, timeOfDeath integer, gender string, problem string, phone string, roomId integer, doctorId integer)");
		}
	};

	public static Factory<Inpatient> getFactory() {
		return factory;
	}

	private Inpatient(int id) {
		super(id);
	}

	int roomId;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int room_id) {
		this.roomId = room_id;
	}

	public Room getRoom() {
		return Room.getFactory().get(roomId);
	}

	public void setRoom(Room r) {
		if (r == null)
			roomId = 0;
		else
			roomId = r.getId();
	}

	public Nurse getNurse() {
		Room r = getRoom();
		if (r != null) {
			return r.getNurse();
		} else {
			return null;
		}
	}

	@Override
	public void load(ResultSet resultset) throws SQLException {
		setDeleted(resultset.getInt("isDeleted") == 0 ? false : true);
		setName(resultset.getString("name"));
		setAddress(resultset.getString("address"));
		setTimeOfBirth(resultset.getLong("timeOfBirth"));
		setTimeOfDeath(resultset.getLong("timeOfDeath"));
		setGender(resultset.getString("gender"));
		setProblem(resultset.getString("problem"));
		setPhone(resultset.getString("phone"));
		setRoomId(resultset.getInt("roomId"));
		setDoctorId(resultset.getInt("doctorId"));
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"REPLACE INTO inpatient (id, isDeleted, name, address, timeOfBirth, timeOfDeath, gender, problem, phone, roomId, doctorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		statement.setInt(1, getId());
		statement.setInt(2, isDeleted() ? 1 : 0);
		statement.setString(3, getName());
		statement.setString(4, getAddress());
		statement.setLong(5, getTimeOfBirth());
		statement.setLong(6, getTimeOfDeath());
		statement.setString(7, getGender());
		statement.setString(8, getProblem());
		statement.setString(9, getPhone());
		statement.setInt(10, getRoomId());
		statement.setInt(11, getDoctorId());
		statement.executeUpdate();
	}

	public String getSearchString() {
		Room r = this.getRoom();
		Nurse n = r != null ? r.getNurse() : null;
		return super.getSearchString() + (r != null ? r.getName() : "") + (n != null ? n.getName() : "");
	}
}
