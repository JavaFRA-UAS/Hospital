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
					"create table if not exists inpatient (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, address string, timeOfBirth integer, timeOfDeath integer, gender string, problem string, phone string, room_id integer)");
		}
	};

	public static Factory<Inpatient> getFactory() {
		return factory;
	}

	private Inpatient(int id) {
		super(id);
	}

	int room_id;

	public Room getRoom() {
		return Room.getFactory().get(room_id);
	}

	public int getRoomId() {
		return room_id;
	}

	public void setRoomId(int room_id) {
		this.room_id = room_id;
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
		setRoomId(resultset.getInt("room_id"));
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"REPLACE INTO inpatient (id, isDeleted, name, address, timeOfBirth, timeOfDeath, gender, problem, phone, room_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
		statement.executeUpdate();
	}
}
