package hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	private boolean isLoaded = false;
	private ArrayList<Doctor> doctors;
	private ArrayList<Patient> patients;
	private ArrayList<Nurse> nurses;
	private ArrayList<Room> rooms;

	public Database() {
		doctors = new ArrayList<Doctor>();
		patients = new ArrayList<Patient>();
		nurses = new ArrayList<Nurse>();
		rooms = new ArrayList<Room>();

	}

	private static Database instance = new Database();

	public static Database getInstance() {
		return instance;
	}

	public Doctor[] getDoctors() {
		return doctors.toArray(new Doctor[0]);
	}

	public Patient[] getPatients() {
		return patients.toArray(new Patient[0]);
	}

	public Nurse[] getNurses() {
		return nurses.toArray(new Nurse[0]);
	}

	public Room[] getRooms() {
		return rooms.toArray(new Room[0]);
	}

	public void addPatient(Patient p) {
		patients.add(p);
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
				patients.add(p);
			}

			resultset = statement.executeQuery("select * from outpatient");
			while (resultset.next()) {
				Outpatient p = new Outpatient();
				p.setId(Integer.parseInt(resultset.getString("id")));
				p.setName(resultset.getString("name"));
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

			for (Doctor d : doctors) {
				PreparedStatement statement = connection
						.prepareStatement("REPLACE INTO doctor (id, name) VALUES (?, ?)");
				statement.setInt(1, d.getId());
				statement.setString(2, d.getName());
				statement.executeUpdate();
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
