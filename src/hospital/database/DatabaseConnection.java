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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import hospital.model.Administrator;
import hospital.model.Doctor;
import hospital.model.Inpatient;
import hospital.model.Nurse;
import hospital.model.Outpatient;
import hospital.model.Room;

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
			for (int i = 0; i < 3; i++) {
				String lastname = lastnames[r.nextInt(lastnames.length)];
				boolean isMale = r.nextInt(100) > 50;
				String firstname = isMale ? firstnamesMale[r.nextInt(firstnamesMale.length)]
						: firstnamesFemale[r.nextInt(firstnamesFemale.length)];

				Administrator a1 = Administrator.getFactory().get(Administrator.getFactory().getNewId());
				a1.setName("Administrator " + lastname);
				a1.setTimeOfBirth(getRandomTimeOfBirth());
				a1.setPhone(getRandomPhone());
				a1.setGender(isMale ? "male" : "female");
				a1.setPassword("admin");
				Administrator.getFactory().save(a1);
			}
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

			for (int i = 0; i < 7; i++) {
				String lastname = lastnames[r.nextInt(lastnames.length)];
				boolean isMale = r.nextInt(100) > 50;
				String firstname = isMale ? firstnamesMale[r.nextInt(firstnamesMale.length)]
						: firstnamesFemale[r.nextInt(firstnamesFemale.length)];

				Doctor d = Doctor.getFactory().get(Doctor.getFactory().getNewId());
				d.setName("Dr. " + lastname);
				d.setTimeOfBirth(getRandomTimeOfBirth());
				d.setPhone(getRandomPhone());
				d.setGender(isMale ? "male" : "female");
				Doctor.getFactory().save(d);
			}
		}

		List<Room> recentlyAddedRooms = new ArrayList<Room>();

		// if there are no rooms in the database
		if (Room.getFactory().size() == 0) {
			final String[] buildings = new String[] { "Building A", "Building B", "Building C" };

			final int countFloor = r.nextInt(4);
			final int countRoomNo = r.nextInt(15);

			for (String building : buildings) {
				for (int floor = 1; floor <= countFloor; floor++) {
					for (int roomNo = 1; roomNo <= countRoomNo; roomNo++) {

						Room r1 = Room.getFactory().get(Room.getFactory().getNewId());
						r1.setName(building + ", " + (roomNo >= 10 ? (floor + "" + roomNo) : (floor + "0" + roomNo)));
						r1.setCapacity(r.nextInt(10) >= 5 ? 4 : 2);
						Room.getFactory().save(r1);
						recentlyAddedRooms.add(r1);
					}
				}
			}
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
			pat1.setDoctor(Doctor.getFactory().get("Dr. Heisenberg"));
			Inpatient.getFactory().save(pat1);

			Outpatient pat2 = Outpatient.getFactory().get(Outpatient.getFactory().getNewId());
			pat2.setName("Fr. Esmeralda");
			pat2.setTimeOfBirth(getRandomTimeOfBirth());
			pat2.setPhone(getRandomPhone());
			pat2.setGender("female");
			pat2.setDoctor(Doctor.getFactory().get("Dr. Bergmann"));
			Outpatient.getFactory().save(pat2);

			Inpatient pat3 = Inpatient.getFactory().get(Inpatient.getFactory().getNewId());
			pat3.setName("Hr. Wagner");
			pat3.setTimeOfBirth(getRandomTimeOfBirth());
			pat3.setPhone(getRandomPhone());
			pat3.setGender("male");
			pat3.setRoomId(Room.getFactory().getLastId());
			pat3.setDoctor(Doctor.getFactory().get("Dr. Schwarzkopf"));
			Inpatient.getFactory().save(pat3);

			for (int i = 0; i < 40; i++) {
				String lastname = lastnames[r.nextInt(lastnames.length)];
				boolean isMale = r.nextInt(100) > 50;
				boolean isInpatient = r.nextInt(100) > 50;
				String firstname = isMale ? firstnamesMale[r.nextInt(firstnamesMale.length)]
						: firstnamesFemale[r.nextInt(firstnamesFemale.length)];

				Doctor d = Doctor.getFactory().list().get(r.nextInt(Doctor.getFactory().list().size()));
				Room room = Room.getFactory().list().get(r.nextInt(Room.getFactory().list().size()));

				if (isInpatient) {
					Inpatient p = Inpatient.getFactory().get(Inpatient.getFactory().getNewId());
					p.setName(firstname + " " + lastname);
					p.setTimeOfBirth(getRandomTimeOfBirth());
					p.setPhone(getRandomPhone());
					p.setGender(isMale ? "male" : "female");
					p.setRoom(room);
					p.setDoctor(d);
					Inpatient.getFactory().save(p);
				} else {
					Outpatient p = Outpatient.getFactory().get(Outpatient.getFactory().getNewId());
					p.setName(firstname + " " + lastname);
					p.setTimeOfBirth(getRandomTimeOfBirth());
					p.setPhone(getRandomPhone());
					p.setGender(isMale ? "male" : "female");
					p.setDoctor(d);
					Outpatient.getFactory().save(p);
				}
			}
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

			for (int i = 0; i < 15; i++) {
				String lastname = lastnames[r.nextInt(lastnames.length)];
				boolean isMale = r.nextInt(100) > 80;
				String firstname = isMale ? firstnamesMale[r.nextInt(firstnamesMale.length)]
						: firstnamesFemale[r.nextInt(firstnamesFemale.length)];

				Nurse n = Nurse.getFactory().get(Nurse.getFactory().getNewId());
				n.setName(firstname + " " + lastname);
				n.setTimeOfBirth(getRandomTimeOfBirth());
				n.setPhone(getRandomPhone());
				n.setGender(isMale ? "male" : "female");
				Nurse.getFactory().save(n);
			}
		}

		for (Room room : recentlyAddedRooms) {
			Nurse n = null;
			for (int _try = 0; _try < 10; _try++) {
				n = Nurse.getFactory().list().get(r.nextInt(Nurse.getFactory().list().size()));
				if (n.getRooms().size() < 10) {
					break;
				} else {
					n = null;
				}
			}

			if (n != null) {
				room.setNurse(n);
				Room.getFactory().save(room);
			}
		}
	}

	private static Random r = new Random();

	private static long getRandomTimeOfBirth() {
		return new Date(40 + (int) Math.abs(r.nextDouble() * 50), 0, 0).getTime() / 1000 + (r.nextLong() % 10000000);
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

	private static String[] lastnames = new String[] { "Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer",
			"Wagner", "Becker", "Schulz", "Hoffmann", "Schäfer", "Koch", "Bauer", "Richter", "Klein",
			"Schröder (Schneider)", "Neumann", "Schwarz", "Zimmermann", "Krüger", "Hofmann", "Lange", "Schmitt",
			"Schmitz", "Krause", "Meier", "Lehmann", "Schmid", "Schulze", "Maier", "Köhler", "König", "Mayer", "Huber",
			"Kaiser", "Fuchs", "Lang", "Scholz", "Möller", "Weiß", "Jung", "Hahn", "Schubert", "Vogel", "Keller",
			"Frank", "Berger", "Winkler", "Roth", "Beck", "Baumann", "Franke", "Schuster", "Böhm", "Winter", "Kraus",
			"Schumacher", "Krämer", "Vogt", "Stein", "Jäger", "Sommer", "Groß", "Brandt", "Haas", "Schreiber", "Graf",
			"Schulte", "Ziegler", "Kühn", "Pohl", "Horn", "Busch", "Bergmann", "Voigt", "Sauer", "Pfeiffer" };

	private static String[] firstnamesMale = new String[] { "Peter", "Michael", "Thomas", "Andreas", "Wolfgang",
			"Claus", "Jürgen", "Günter", "Stefan", "Christian", "Uwe", "Werner", "Horst", "Frank", "Dieter", "Manfred",
			"Gerhard", "Hans", "Bernd", "Thorsten", "Mathias", "Helmut", "Walter", "Heinz", "Martin", "Jörg", "Rolf",
			"Jens", "Sven", "Alexander", "Jan", "Rainer", "Holger", "Carl", "Dirk", "Joachim", "Ralf", "Carsten",
			"Herbert", "Oliver", "Wilhelm", "Curt", "Marcus", "Heinrich", "Hermann", "Harald", "Gerd", "Paul", "Andre",
			"Norbert", "Daniel", "Olaf", "Rudolf", "Otto", "Marco", "Volker", "Ulrich", "Ernst", "Robert", "Willi",
			"Christoph", "Johannes", "Dennis", "Sebastian", "Alfred", "Friedrich", "Florian", "Georg", "Patrick",
			"Detlef", "Tobias", "Lars", "Reinhardt", "Erich", "Marc", "Ingo", "Nils", "Bernhard", "Axel", "Heiko",
			"Philipp", "Maik", "Siegfried", "Kai", "Björn", "Fritz", "Rüdiger", "Richard", "Tim", "Franz", "René",
			"Marcel", "Lothar", "Benjamin", "Hartmut", "Johann", "Jörn", "Erwin", "Wilfried", "Mario" };

	private static String[] firstnamesFemale = new String[] { "Ursula", "Carin", "Helga", "Sabine", "Ingrid", "Renate",
			"Monica", "Susanne", "Gisela", "Petra", "Birgit", "Andrea", "Anna", "Brigitte", "Claudia", "Erica",
			"Christa", "Elke", "Stefanie", "Gertrud", "Elisabeth", "Maria", "Angelika", "Heike", "Gabriele", "Cathrin",
			"Ilse", "Nicole", "Anja", "Barbara", "Hildegard", "Martina", "Ingeborg", "Gerda", "Marion", "Jutta", "Ute",
			"Hannelore", "Irmgard", "Christine", "Inge", "Christina", "Silvia", "Margarete", "Kerstin", "Marianne",
			"Edith", "Marta", "Waltraud", "Catharina", "Anke", "Christel", "Bärbel", "Julia", "Erna", "Tanja", "Silke",
			"Elfriede", "Ruth", "Lieselotte", "Angela", "Regina", "Frida", "Melanie", "Christiane", "Bettina", "Ulrike",
			"Britta", "Käte", "Sonja", "Anneliese", "Rita", "Cornelia", "Eva", "Sigrid", "Herta", "Johanna", "Manuela",
			"Doris", "Kirsten", "Maike", "Astrid", "Rosemarie", "Beate", "Margot", "Dagmar", "Katja", "Daniela",
			"Charlotte", "Heidi", "Marlies", "Antje", "Gudrun", "Nadin", "Helene", "Ivonne", "Anette", "Maren",
			"Marie" };
}
