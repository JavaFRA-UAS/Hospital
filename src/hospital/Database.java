package hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	private boolean isLoaded = false;
	private HashMap<Integer, Doctor> doctors;
	private HashMap<Integer, Patient> patients;
	private HashMap<Integer, Nurse> nurses;
	private HashMap<Integer, Room> rooms;

	public Database() {
		doctors = new HashMap<Integer, Doctor>();
		patients = new HashMap<Integer, Patient>();
		nurses = new HashMap<Integer, Nurse>();
		rooms = new HashMap<Integer, Room>();

	}

	private static Database instance = new Database();

	public static Database getInstance() {
		return instance;
	}

	public Doctor[] getDoctors() {
		return doctors.values().toArray(new Doctor[0]);
	}

	public Patient[] getPatients() {
		return patients.values().toArray(new Patient[0]);
	}

	public Nurse[] getNurses() {
		return nurses.values().toArray(new Nurse[0]);
	}

	public Room[] getRooms() {
		return rooms.values().toArray(new Room[0]);
	}

	public void addPatient(Patient p) {
		patients.put(p.getId(), p);
	}

	public void addNurse(Nurse n) {
		nurses.add(n);
	}

	public void addDoctor(Doctor d) {
		doctors.add(d);
	}

	public void addRoom(Room r) {
		rooms.add(r);
	}

	public void load() {
		try {
			Connection connection = DatabaseConnection.getConnection();
			Statement statement = connection.createStatement();

			doctors.clear();
			nurses.clear();
			patients.clear();
			rooms.clear();

			ResultSet resultset = statement.executeQuery("select * from doctor");
			while (resultset.next()) {
				Doctor d = new Doctor();
				d.setId(Integer.parseInt(resultset.getString("id")));
				d.setName(resultset.getString("name"));
				doctors.add(d);
			}

			resultset = statement.executeQuery("select * from nurse");
			while (resultset.next()) {
				Nurse n = new Nurse();
				n.setId(Integer.parseInt(resultset.getString("id")));
				n.setName(resultset.getString("name"));
				nurses.add(n);
			}

			resultset = statement.executeQuery("select * from inpatient");
			while (resultset.next()) {
				Inpatient p = new Inpatient();
				p.setId(Integer.parseInt(resultset.getString("id")));
				p.setName(resultset.getString("name"));
				p.setAddress(resultset.getString("address"));
				p.setBirthday(resultset.getLong("birthday"));
				p.setGender(resultset.getString("gender"));
				p.setProblem(resultset.getString("problem"));
				p.setPhone(resultset.getString("phone"));
				p.setRoomId(resultset.getInt("room_id"));
				patients.add(p);
			}

			resultset = statement.executeQuery("select * from outpatient");
			while (resultset.next()) {
				Outpatient p = new Outpatient();
				p.setId(Integer.parseInt(resultset.getString("id")));
				p.setName(resultset.getString("name"));
				p.setAddress(resultset.getString("address"));
				p.setBirthday(resultset.getLong("birthday"));
				p.setGender(resultset.getString("gender"));
				p.setProblem(resultset.getString("problem"));
				p.setPhone(resultset.getString("phone"));
				patients.add(p);
			}

			resultset = statement.executeQuery("select * from room");
			while (resultset.next()) {
				Room r = new Room();
				r.setId(Integer.parseInt(resultset.getString("id")));
				rooms.add(r);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			isLoaded = true;
		}
	}

	public void save() {
		try {
			Connection connection = DatabaseConnection.getConnection();

			for (Doctor d : doctors) {
				PreparedStatement statement = connection
						.prepareStatement("REPLACE INTO doctor (id, name) VALUES (?, ?)");
				statement.setInt(1, d.getId());
				statement.setString(2, d.getName());
				statement.executeUpdate();
			}

			for (Patient p : patients) {
				if (p instanceof Inpatient) {
					PreparedStatement statement = connection.prepareStatement(
							"REPLACE INTO inpatient (id, name, address, birthday, gender, problem, phone, room_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
					statement.setInt(1, p.getId());
					statement.setString(2, p.getName());
					statement.setString(3, p.getAddress());
					statement.setLong(4, p.getBirthday());
					statement.setString(5, p.getGender());
					statement.setString(6, p.getProblem());
					statement.setString(7, p.getPhone());
					Room room = ((Inpatient)p).getRoom();
					statement.setInt(8, room != null ? room.getId() : 0);
					statement.executeUpdate();
				} else if (p instanceof Outpatient) {
					PreparedStatement statement = connection.prepareStatement(
							"REPLACE INTO inpatient (id, name, address, birthday, gender, problem, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
					statement.setInt(1, p.getId());
					statement.setString(2, p.getName());
					statement.setString(3, p.getAddress());
					statement.setLong(4, p.getBirthday());
					statement.setString(5, p.getGender());
					statement.setString(6, p.getProblem());
					statement.setString(7, p.getPhone());
					statement.executeUpdate();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void waitUntilReady() {
		while (!isLoaded) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}
}
