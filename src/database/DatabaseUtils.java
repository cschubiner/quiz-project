package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

	public static ResultSet getResultSetFromDatabase(DBConnection db, String query) {
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			return r;
		}
		catch(Exception e){}
		return null;
	}
	
	public static void updateDatabase(DBConnection db, String query){
		Statement stmt = db.getStatement();
		try{
			stmt.executeUpdate(query);
		}catch(SQLException e){
			//do nothing
		}
	}
	
}
