package hospital.database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import hospital.model.*;

public class DatabaseConnection {

	private static String url = "jdbc:sqlite:hospital.db";
	private static Connection conn;

	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			// log an exception
			System.out.println("Failed to create the database connection: \n\n" + ex);
		}
		return conn;

	}

	public static void initialize() {
		try {
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			System.out.println("working directory: " + s);

			// create a database connection
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			System.out.println("create tables if necessary...");

			Administrator.getFactory().createTable(statement);
			Doctor.getFactory().createTable(statement);
			Room.getFactory().createTable(statement);
			Nurse.getFactory().createTable(statement);
			Inpatient.getFactory().createTable(statement);
			Outpatient.getFactory().createTable(statement);

			System.out.println("load data...");

			Administrator.getFactory().loadAll();
			Doctor.getFactory().loadAll();
			Room.getFactory().loadAll();
			Nurse.getFactory().loadAll();
			Inpatient.getFactory().loadAll();
			Outpatient.getFactory().loadAll();

			System.out.println("insert default data if necessary...");

			insertDefaultDataIfNeeded();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void insertDefaultDataIfNeeded() {

		// if there are no administrators in the database
		if (Administrator.getFactory().size() == 0) {
			// ... add some administrators
			Administrator a1 = Administrator.getFactory().get(Administrator.getFactory().getNewId());
			a1.setName("Head of Department 1");
			a1.setTimeOfBirth(getRandomTimeOfBirth());
			Administrator.getFactory().save(a1);
		}

		// if there are no doctors in the database
		if (Doctor.getFactory().size() == 0) {
			// ... add some doctors
			Doctor doc1 = Doctor.getFactory().get(Doctor.getFactory().getNewId());
			doc1.setName("Dr. Heisenberg");
			doc1.setTimeOfBirth(getRandomTimeOfBirth());
			doc1.setPhone(getRandomPhone());
			doc1.setGender("male");
			Doctor.getFactory().save(doc1);

			Doctor doc2 = Doctor.getFactory().get(Doctor.getFactory().getNewId());
			doc2.setName("Dr. Bergmann");
			doc2.setTimeOfBirth(getRandomTimeOfBirth());
			doc2.setPhone(getRandomPhone());
			doc2.setGender("male");
			Doctor.getFactory().save(doc2);

			Doctor doc3 = Doctor.getFactory().get(Doctor.getFactory().getNewId());
			doc3.setName("Dr. Schwarzkopf");
			doc3.setTimeOfBirth(getRandomTimeOfBirth());
			doc3.setPhone(getRandomPhone());
			doc3.setGender("male");
			Doctor.getFactory().save(doc3);
		}

		// if there are no rooms in the database
		if (Room.getFactory().size() == 0) {
			// ... add some rooms
			Room r1 = Room.getFactory().get(Room.getFactory().getNewId());
			r1.setName("101");
			Room.getFactory().save(r1);

			Room r2 = Room.getFactory().get(Room.getFactory().getNewId());
			r2.setName("102");
			Room.getFactory().save(r2);

			Room r3 = Room.getFactory().get(Room.getFactory().getNewId());
			r3.setName("103");
			Room.getFactory().save(r3);

			Room r4 = Room.getFactory().get(Room.getFactory().getNewId());
			r4.setName("201");
			Room.getFactory().save(r4);
		}

		// if there are no patients in the database
		if (Inpatient.getFactory().size() == 0 && Outpatient.getFactory().size() == 0) {
			// ... add some patients
			Inpatient pat1 = Inpatient.getFactory().get(Inpatient.getFactory().getNewId());
			pat1.setName("Fr. Hildegard");
			pat1.setTimeOfBirth(getRandomTimeOfBirth());
			pat1.setPhone(getRandomPhone());
			pat1.setGender("female");
			pat1.setRoomId(Room.getFactory().getFirstId());
			Inpatient.getFactory().save(pat1);

			Outpatient pat2 = Outpatient.getFactory().get(Outpatient.getFactory().getNewId());
			pat2.setName("Fr. Esmeralda");
			pat2.setTimeOfBirth(getRandomTimeOfBirth());
			pat2.setPhone(getRandomPhone());
			pat2.setGender("female");
			Outpatient.getFactory().save(pat2);

			Inpatient pat3 = Inpatient.getFactory().get(Inpatient.getFactory().getNewId());
			pat3.setName("Hr. Wagner");
			pat3.setTimeOfBirth(getRandomTimeOfBirth());
			pat3.setPhone(getRandomPhone());
			pat3.setGender("male");
			pat3.setRoomId(Room.getFactory().getLastId());
			Inpatient.getFactory().save(pat3);
		}

		if (Nurse.getFactory().size() == 0) {
			// ... add some patients
			Nurse nur1 = Nurse.getFactory().get(Nurse.getFactory().getNewId());
			nur1.setName("Fr. Müller");
			nur1.setTimeOfBirth(getRandomTimeOfBirth());
			nur1.setPhone(getRandomPhone());
			nur1.setGender("female");
			Nurse.getFactory().save(nur1);

			Nurse nur2 = Nurse.getFactory().get(Nurse.getFactory().getNewId());
			nur2.setName("Fr. Gieselmann");
			nur2.setTimeOfBirth(getRandomTimeOfBirth());
			nur2.setPhone(getRandomPhone());
			nur2.setGender("female");
			Nurse.getFactory().save(nur2);

			Nurse nur3 = Nurse.getFactory().get(Nurse.getFactory().getNewId());
			nur3.setName("Fr. Otto");
			nur3.setTimeOfBirth(getRandomTimeOfBirth());
			nur3.setPhone(getRandomPhone());
			nur3.setGender("female");
			Nurse.getFactory().save(nur3);
		}

	}

	private static Random r = new Random();

	private static long getRandomTimeOfBirth() {
		return new Date(40 + (int)Math.abs(r.nextDouble() * 50), 0, 0).getTime() / 1000 + (r.nextLong() % 10000000);
	}

	private static String getRandomPhone() {
		return (100000000 + r.nextLong() % 100000000) + "";
	}

	public static void writeData(ResultSet resultSet) throws SQLException {
		// Now get some data from the database
		// Result set get the result of the SQL query

		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();

		for (int i = 1; i <= columnsNumber; i++) {

			System.out.print(rsmd.getColumnName(i) + ",\t");
			if (i == columnsNumber) {
				System.out.print("\r\n");
			}

		}

		while (resultSet.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = resultSet.getString(i);
				System.out.print(columnValue + " ");
			}
			System.out.println("");
		}
	}
}
