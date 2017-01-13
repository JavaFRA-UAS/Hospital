package hospital.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Patient;
import hospital.model.Room;

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

	public HashMap<Integer, Doctor> getDoctorMap() {
		return doctors;
	}

	public HashMap<Integer, Patient> getPatientMap() {
		return patients;
	}

	public HashMap<Integer, Nurse> getNurseMap() {
		return nurses;
	}

	public HashMap<Integer, Room> getRoomMap() {
		return rooms;
	}

	public void addPatient(Patient p) {
		patients.put(p.getId(), p);
	}

	public void addNurse(Nurse n) {
		nurses.put(n.getId(), n);
	}

	public void addDoctor(Doctor d) {
		doctors.put(d.getId(), d);
	}

	public void addRoom(Room r) {
		rooms.put(r.getId(), r);
	}

	public void removePatient(Patient p) {
		patients.remove(p.getId());

		try {
			Connection connection = DatabaseConnection.getConnection();
			if (p instanceof Inpatient) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM inpatient WHERE id = ?");
				statement.setInt(1, p.getId());
				statement.executeUpdate();
			} else if (p instanceof Outpatient) {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM outpatient WHERE id = ?");
				statement.setInt(1, p.getId());
				statement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeNurse(Nurse n) {
		nurses.remove(n.getId());

		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM nurse WHERE id = ?");
			statement.setInt(1, n.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeDoctor(Doctor d) {
		doctors.remove(d.getId());

		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM doctor WHERE id = ?");
			statement.setInt(1, d.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRoom(Room r) {
		rooms.remove(r.getId());

		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM room WHERE id = ?");
			statement.setInt(1, r.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				d.setAddress(resultset.getString("address"));
				d.setBirthday(resultset.getLong("birthday"));
				d.setGender(resultset.getString("gender"));
				d.setPhone(resultset.getString("phone"));
				doctors.put(d.getId(), d);
			}

			resultset = statement.executeQuery("select * from nurse");
			while (resultset.next()) {
				Nurse n = new Nurse();
				n.setId(Integer.parseInt(resultset.getString("id")));
				n.setName(resultset.getString("name"));
				n.setAddress(resultset.getString("address"));
				n.setBirthday(resultset.getLong("birthday"));
				n.setGender(resultset.getString("gender"));
				n.setPhone(resultset.getString("phone"));
				nurses.put(n.getId(), n);
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
				patients.put(p.getId(), p);
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
				patients.put(p.getId(), p);
			}

			resultset = statement.executeQuery("select * from room");
			while (resultset.next()) {
				Room r = new Room();
				r.setId(Integer.parseInt(resultset.getString("id")));
				rooms.put(r.getId(), r);
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

			for (Doctor d : doctors.values()) {
				PreparedStatement statement = connection
						.prepareStatement("REPLACE INTO doctor (id, name, address, birthday, gender, phone) VALUES (?, ?, ?, ?, ?, ?)");
				statement.setInt(1, d.getId());
				statement.setString(2, d.getName());
				statement.setString(3, d.getAddress());
				statement.setLong(4, d.getBirthday());
				statement.setString(5, d.getGender());
				statement.setString(6, d.getPhone());
				statement.executeUpdate();
			}

			for (Nurse n : nurses.values()) {
				PreparedStatement statement = connection
						.prepareStatement("REPLACE INTO nurse (id, name, address, birthday, gender, phone) VALUES (?, ?, ?, ?, ?, ?)");
				statement.setInt(1, n.getId());
				statement.setString(2, n.getName());
				statement.setString(3, n.getAddress());
				statement.setLong(4, n.getBirthday());
				statement.setString(5, n.getGender());
				statement.setString(6, n.getPhone());
				statement.executeUpdate();
			}

			for (Patient p : patients.values()) {
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
					Room room = ((Inpatient) p).getRoom();
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
