package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
					"create table if not exists nurse (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, address string, timeOfBirth integer, gender string, phone string)");
		}
	};

	public static Factory<Nurse> getFactory() {
		return factory;
	}

	private Nurse(int id) {
		super(id);
	}

	ArrayList<Room> rooms;

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public void load(ResultSet resultset) throws SQLException {
		setDeleted(resultset.getInt("isDeleted") == 0 ? false : true);
		setName(resultset.getString("name"));
		setAddress(resultset.getString("address"));
		setTimeOfBirth(resultset.getLong("timeOfBirth"));
		setGender(resultset.getString("gender"));
		setPhone(resultset.getString("phone"));
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"REPLACE INTO nurse (id, isDeleted, name, address, timeOfBirth, gender, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
		statement.setInt(1, getId());
		statement.setInt(2, isDeleted() ? 1 : 0);
		statement.setString(3, getName());
		statement.setString(4, getAddress());
		statement.setLong(5, getTimeOfBirth());
		statement.setString(6, getGender());
		statement.setString(7, getPhone());
		statement.executeUpdate();
	}
}
