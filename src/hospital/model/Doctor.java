package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hospital.database.DatabaseRow;
import hospital.database.Factory;

public class Doctor extends Employee implements DatabaseRow {

	private static Factory<Doctor> factory = new Factory<Doctor>("doctor", 1000) {
		@Override
		protected Doctor create(int id) {
			Doctor o = new Doctor(id);
			return o;
		}

		@Override
		public void createTable(Statement statement) throws SQLException {
			statement.executeUpdate(
					"create table if not exists doctor (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, address string, timeOfBirth integer, gender string, phone string, password string)");
		}
	};

	public static Factory<Doctor> getFactory() {
		return factory;
	}

	private Doctor(int id) {
		super(id);
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
				"REPLACE INTO doctor (id, isDeleted, name, address, timeOfBirth, gender, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
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
