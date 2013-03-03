package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;

public class QuizUtils {
	public static ArrayList<Quiz> getAllQuizzes(DBConnection db) {
		
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		Statement stmt = db.getStatement();
		String query = "SELECT * FROM mQuiz;";
		
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while (r.next()) {
				Quiz q = new Quiz(r);
				quizzes.add(q);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes;
		
	}
	
	public static Quiz getQuizByID(DBConnection db, String id) {
		Statement stmt = db.getStatement();
		String query = "SELECT * FROM mQuiz WHERE mQuizID = "+id+";";
		
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while (r.next()) {
				return new Quiz(r);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String getQuizLinkString(String quizName, int quizID){
		return "<a href=\"QuizOverviewServlet?id="+quizID+"\">"+quizName+"</a>";
	}
	
	
}
