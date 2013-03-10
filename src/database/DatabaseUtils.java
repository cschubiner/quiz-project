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
	
	public static int updateDatabase(DBConnection db, String query){
		Statement stmt = db.getStatement();
		try{
			return stmt.executeUpdate(query);
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	public static int getNumberOfResultsForQuery(DBConnection db, String query) {
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.last();
			return r.getRow();
		}
		catch(Exception e){}
		return 0;
	}
}
