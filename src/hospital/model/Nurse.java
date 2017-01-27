package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hospital.database.DatabaseRow;
import hospital.database.Factory;

public class Nurse extends Employee implements DatabaseRow {

	private static Factory<Nurse> factory = new Factory<Nurse>("nurse", 4000) {
		@Override
		protected Nurse create(int id) {
			Nurse o = new Nurse(id);
			return o;
		}

		@Override
		public void createTable(Statement statement) throws SQLException {
			statement.executeUpdate(
					"create table if not exists nurse (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, address string, timeOfBirth integer, gender string, phone string, password string)");
		}
	};

	public static Factory<Nurse> getFactory() {
		return factory;
	}

	private Nurse(int id) {
		super(id);
	}

	public List<Room> getRooms() {
		List<Room> rooms = new ArrayList<Room>();
		for (Room r : Room.getFactory().list()) {
			if (r.getNurseId() == id) {
				rooms.add(r);
			}
		}
		return rooms;
	}

	@Override
	public void load(ResultSet resultset) throws SQLException {
		setDeleted(resultset.getInt("isDeleted") == 0 ? false : true);
		setName(resultset.getString("name"));
		setAddress(resultset.getString("address"));
		setTimeOfBirth(resultset.getLong("timeOfBirth"));
		setGender(resultset.getString("gender"));
		setPhone(resultset.getString("phone"));
		setPassword(resultset.getString("password"));
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"REPLACE INTO nurse (id, isDeleted, name, address, timeOfBirth, gender, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		statement.setInt(1, getId());
		statement.setInt(2, isDeleted() ? 1 : 0);
		statement.setString(3, getName());
		statement.setString(4, getAddress());
		statement.setLong(5, getTimeOfBirth());
		statement.setString(6, getGender());
		statement.setString(7, getPhone());
		statement.setString(8, getPassword());
		statement.executeUpdate();
	}
}
