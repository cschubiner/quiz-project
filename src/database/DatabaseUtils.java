package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;

import quiz.QuizUtils;
import user.UserUtils;

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
	public static String getPreviousTime(int days) {
		Date d = Calendar.getInstance().getTime();
		Date dateBefore = new Date(d.getTime() - days * 24 * 3600 * 1000 );
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dateBefore);
	}
	public static String getTimestamp() {
		Date d = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	
	//for users and quizzes only
	public static ArrayList<String> search(String search,String table, String field, DBConnection db){
		ArrayList<String> result = new ArrayList<String>();
		
		String queryPartial = "Select * From " + table + " Where "+field+" Like \"%" + search + "%\";";
		
		ResultSet partialMatch = DatabaseUtils.getResultSetFromDatabase(db, queryPartial);
		
		try {
			while(partialMatch.next()){
				if(field.equals("UserName")){
					result.add(UserUtils.getUserLinkString(partialMatch.getString(1)));
				}else if(field.equals("QuizName")){
					result.add(QuizUtils.getQuizLinkString(partialMatch.getString(2),partialMatch.getInt(1)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
