package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
					"create table if not exists room (id INTEGER PRIMARY KEY AUTOINCREMENT, isDeleted integer, name string, capacity integer)");
		}
	};

	public static Factory<Room> getFactory() {
		return factory;
	}

	int id;
	boolean isDeleted;
	String name;
	int capacity;

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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void load(ResultSet resultset) throws SQLException {
		setDeleted(resultset.getInt("isDeleted") == 0 ? false : true);
		setCapacity(resultset.getInt("capacity"));
		setName(resultset.getString("name"));
	}

	@Override
	public void save(Connection connection) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("REPLACE INTO room (id, isDeleted, name, capacity) VALUES (?, ?, ?, ?)");
		statement.setInt(1, getId());
		statement.setInt(2, isDeleted() ? 1 : 0);
		statement.setString(3, getName());
		statement.setInt(4, getCapacity());
		statement.executeUpdate();
	}
	
	public String getSearchString() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
