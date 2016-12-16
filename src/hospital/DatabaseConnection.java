package hospital;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;

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
			System.out.println("Working directory: " + s);

			// create a database connection
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			statement.executeUpdate("create table if not exists doctor (id INTEGER PRIMARY KEY AUTOINCREMENT, name string)");
			statement.executeUpdate("create table if not exists room (id INTEGER PRIMARY KEY AUTOINCREMENT, name string)");
			statement.executeUpdate("create table if not exists nurse (id INTEGER PRIMARY KEY AUTOINCREMENT, name string)");
			statement.executeUpdate("create table if not exists inpatient (id INTEGER PRIMARY KEY AUTOINCREMENT, name string, address string, birthday integer, gender string, problem string, phone string, room_id integer)");
			statement.executeUpdate("create table if not exists outpatient (id INTEGER PRIMARY KEY AUTOINCREMENT, name string, address string, birthday integer, gender string, problem string, phone string)");
			

			
		} catch (Exception e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
	}
	
	//	statement.executeUpdate("insert into person values(2, 'yui')");

	public static ResultSet runMyQuery(Statement statement, Connection connection, String query) {
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement(); // Usage of statement
														// makes your code prone
														// to SQL injection
														// attacks
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;

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
