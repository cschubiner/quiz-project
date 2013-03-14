package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class DatabaseUtils {
	
	public static DBConnection getDatabaseConnectionFromHttpServlet(HttpServlet servlet){
		return (DBConnection) servlet.getServletContext().getAttribute("database");
	}

	public static ResultSet getResultSetFromDatabase(DBConnection db, String query) {
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			return r;
		}
		catch(Exception e){System.out.println("could not get result set");}
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
	public static String getTimestamp() {
		Date d = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
}
