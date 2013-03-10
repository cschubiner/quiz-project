package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import database.DatabaseUtils;

public class QuizUtils {
	public static ArrayList<Quiz> getAllQuizzes(DBConnection db) {
		String query = "SELECT * FROM mQuiz;";
		return getQuizzesFromDatabaseWithQuery(db, query);
	}
	
	public static Quiz getQuizByID(DBConnection db, int i) {
		return getQuizByID(db, Integer.toString(i));
	}
	
	public static Quiz getQuizByID(DBConnection db, String i) {
		Statement stmt = db.getStatement();
		String query = "SELECT * FROM mQuiz WHERE mQuizID = "+i+";";

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

	public static ArrayList<Quiz> getXMostPopularQuizzes(DBConnection db, int howManyToGet) {
		String query = "(select * from mQuiz order by NumTaken DESC limit "+howManyToGet+");";
		return getQuizzesFromDatabaseWithQuery(db, query);		
	}
	
	public static ArrayList<Quiz> getXMostRecentlyCreatedQuizzes(DBConnection db, int howManyToGet) {
		String query = "(select * from mQuiz order by LastModified DESC limit "+howManyToGet+");";
		return getQuizzesFromDatabaseWithQuery(db, query);		
	}
	
	public static ArrayList<Quiz> getXMostRecentlyCreatedQuizzesByUser(DBConnection db, int howManyToGet, String user) {
		String query = "(select * from mQuiz where Author = '"+user+"' order by LastModified DESC limit "+howManyToGet+");";
		return getQuizzesFromDatabaseWithQuery(db, query);		
	}
	
	public static ArrayList<Quiz> getXMostRecentQuizzesTakenByUser(DBConnection db, String user, int howManyToGet) {
		String query = "(select * from tQuiz where TakenBy = '"+user+"' group by mQuizID) order by TimeTaken DESC limit "+howManyToGet+";";
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		ArrayList<Quiz> tQuizzes = getQuizzesFromDatabaseWithQuery(db, query);
		for (int i = 0 ; i < tQuizzes.size(); i++){
			quizzes.add(getQuizByID(db, tQuizzes.get(i).getID()));
		}
		
		return quizzes;
	}

	private static ArrayList<Quiz> getQuizzesFromDatabaseWithQuery(DBConnection db,
			String query) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		Statement stmt = db.getStatement();
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

	public static String getQuizLinkString(String quizName, int quizID){
		return "<a href=\"QuizOverviewServlet?id="+quizID+"\">"+quizName+"</a>";
	}
	public static int getNextQuizID(DBConnection db) {
		//get current max id
		String query = "SELECT MAX(mQuizID) FROM mQuiz;";
		
		try {
			ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
			if (r.next()) {
				return r.getInt(1) + 1;
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static int getNextQuestionID(DBConnection db, String table) {
		//get current max id
		String id = table +"ID";
		String query = "SELECT MAX(QuestionID) FROM " + table + ";";
		
		try {
			ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
			if (r.next()) {
				return r.getInt(1) + 1;
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			//do nothing
		}
		return 0;
	}

}
