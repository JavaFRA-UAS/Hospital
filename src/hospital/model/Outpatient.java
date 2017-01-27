package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hospital.database.DatabaseRow;
import hospital.database.Factory;

public class Outpatient extends Patient implements DatabaseRow {

	private static Factory<Outpatient> factory = new Factory<Outpatient>("outpatient", 2000) {
		@Override
		protected Outpatient create(int id) {
			Outpatient o = new Outpatient(id);
			return o;
		}

		@Override
		public void createTable(Statement statement) throws SQLException {
			statement.executeUpdate(
					"create table if not exists outpatient (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, address string, timeOfBirth integer, timeOfDeath integer, gender string, problem string, phone string)");
		}
	};

	public static Factory<Outpatient> getFactory() {
		return factory;
	}

	private Outpatient(int id) {
		super(id);
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
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"REPLACE INTO outpatient (id, isDeleted, name, address, timeOfBirth, timeOfDeath, gender, problem, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		statement.setInt(1, getId());
		statement.setInt(2, isDeleted() ? 1 : 0);
		statement.setString(3, getName());
		statement.setString(4, getAddress());
		statement.setLong(5, getTimeOfBirth());
		statement.setLong(6, getTimeOfDeath());
		statement.setString(7, getGender());
		statement.setString(8, getProblem());
		statement.setString(9, getPhone());
		statement.executeUpdate();
	}
}
