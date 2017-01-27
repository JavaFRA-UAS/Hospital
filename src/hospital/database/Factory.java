package hospital.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import hospital.model.Doctor;

public abstract class Factory<T extends DatabaseRow> {

	String tableName;
	int initialId;
	HashMap<Integer, T> data = new HashMap<Integer, T>();

	public Factory(String tableName, int initialId) {
		this.tableName = tableName;
		this.initialId = initialId;
	}

	public int size() {
		synchronized (data) {
			int size = 0;
			for (T o : data.values())
				size += o.isDeleted() ? 0 : 1;
			return size;
		}
	}

	public Integer[] getAllIds() {
		synchronized (data) {
			List<Integer> keys = new ArrayList<Integer>();
			for (Entry<Integer, T> e : data.entrySet()) {
				if (!e.getValue().isDeleted()) {
					keys.add(e.getKey());
				}
			}
			return keys.toArray(new Integer[0]);
		}
	}

	public boolean exists(int id) {
		if (id == 0)
			return false;

		synchronized (data) {
			return data.containsKey(id) && !data.get(id).isDeleted();
		}
	}

	public synchronized T get(int id) {
		if (id == 0)
			return null;

		synchronized (data) {
			if (data.containsKey(id)) {
				return data.get(id);
			} else {
				T obj = create(id);
				data.put(id, obj);
				return obj;
			}
		}
	}

	public synchronized T get(String name) {
		if (name == null)
			return null;
		synchronized (data) {
			for (Entry<Integer, T> e : data.entrySet()) {
				if (e.getValue().getName().equals(name)) {
					return e.getValue();
				}
			}
			return null;
		}
	}

	public List<T> list() {
		synchronized (data) {
			List<T> values = new ArrayList<T>();
			for (Entry<Integer, T> e : data.entrySet()) {
				if (!e.getValue().isDeleted()) {
					values.add(e.getValue());
				}
			}
			return values;
		}
	}

	protected abstract T create(int id);

	public int getFirstId() {
		synchronized (data) {
			return data.size() > 0 ? Collections.min(data.keySet()) : 0;
		}
	}

	public int getLastId() {
		synchronized (data) {
			return data.size() > 0 ? Collections.max(data.keySet()) : 0;
		}
	}

	public int getInitialId() {
		return initialId;
	}

	public int getNewId() {
		synchronized (data) {
			return data.size() > 0 ? (getLastId() + 1) : getInitialId();
		}
	}

	public void loadAll() {
		try {
			Connection connection = DatabaseConnection.getConnection();
			Statement statement = connection.createStatement();

			ResultSet resultset = statement.executeQuery("select * from " + tableName);
			while (resultset.next()) {
				T obj = this.get(Integer.parseInt(resultset.getString("id")));
				obj.load(resultset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(int id) {
		save(get(id));
	}

	public void save(T obj) {
		if (obj == null)
			return;
		try {
			Connection connection = DatabaseConnection.getConnection();

			obj.save(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAll() {
		try {
			Connection connection = DatabaseConnection.getConnection();

			for (T obj : data.values()) {
				obj.save(connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void createTable(Statement statement) throws SQLException;
}
