package hospital.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseRow {
	
	public boolean isDeleted();
	
	public void delete();

	public void load(ResultSet resultset) throws SQLException;

	public void save(Connection connection) throws SQLException;

}
