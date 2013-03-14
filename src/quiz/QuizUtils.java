package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import question2.Question;
import database.DBConnection;
import database.DatabaseUtils;

public class QuizUtils {
	public static void removeMQuizFromDatabase(DBConnection db, int id){
		DatabaseUtils.updateDatabase(db, "DELETE FROM tQuiz WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "DELETE FROM FillQuestion WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "DELETE FROM ResponseQuestion WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "DELETE FROM ReportedQuizzes WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "DELETE FROM mQuiz WHERE mQuizID="+id+";");
	}
	
	public static void removeHistoryOfMQuizFromDatabase(DBConnection db, int id){
		DatabaseUtils.updateDatabase(db, "DELETE FROM tQuiz WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "UPDATE mQuiz SET NumTaken = 0 where mQuizID = "+id+";");
	}
	
	public static int getNumberOfQuizzesCreated(DBConnection db) {
		String query = "SELECT * FROM mQuiz;";
		return DatabaseUtils.getNumberOfResultsForQuery(db, query);
	}
	
	public static int getNumberOfQuizzesTaken(DBConnection db) {
		String query = "SELECT * FROM tQuiz;";
		return DatabaseUtils.getNumberOfResultsForQuery(db, query);
	}

	public static ArrayList<Quiz> getAllQuizzes(DBConnection db) {
		String query = "SELECT * FROM mQuiz;";
		return getMQuizzesFromDatabaseWithQuery(db, query);
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
		return getMQuizzesFromDatabaseWithQuery(db, query);		
	}

	public static ArrayList<Quiz> getXMostRecentlyCreatedQuizzes(DBConnection db, int howManyToGet) {
		String query = "(select * from mQuiz order by LastModified DESC limit "+howManyToGet+");";
		return getMQuizzesFromDatabaseWithQuery(db, query);		
	}
	
	public static ArrayList<Quiz> getAllQuizzesCreatedByUser(DBConnection db, String userName) {
		String query = "SELECT * FROM mQuiz where Author = '"+userName+"';";
		return getMQuizzesFromDatabaseWithQuery(db, query);
	}

	public static ArrayList<Quiz> getXMostRecentlyCreatedQuizzesByUser(DBConnection db, int howManyToGet, String user) {
		String query = "(select * from mQuiz where Author = '"+user+"' order by LastModified DESC limit "+howManyToGet+");";
		return getMQuizzesFromDatabaseWithQuery(db, query);		
	}
	public static ArrayList<TQuiz> getXHighestScoringtQuizzes(DBConnection db, int quizID, int limit) {
		String query = "SELECT * FROM tQuiz WHERE mQuizID=" +quizID + " ORDER BY Score DESC, Duration LIMIT " + limit+";";
		System.out.println(query);
		return QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
	}
	public static ArrayList<TQuiz> getXRecenttQuizzes(DBConnection db, int quizID, int limit) {
		String query = "SELECT * FROM tQuiz WHERE mQuizID=" + quizID + " ORDER BY TimeTaken DESC LIMIT " + limit+";";
		System.out.println(query);
		return QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
	}
	public static ArrayList<Quiz> getXMostRecentQuizzesTakenByUser(DBConnection db, String user, int howManyToGet) {
		String query = "(select * from tQuiz where TakenBy = '"+user+"' group by mQuizID) order by TimeTaken DESC limit "+howManyToGet+";";
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		ArrayList<TQuiz> tQuizzes = getTQuizzesFromDatabaseWithQuery(db, query);
		for (int i = 0 ; i < tQuizzes.size(); i++){
			Quiz quizToAdd = getQuizByID(db, tQuizzes.get(i).getmQuizID());
			if (quizToAdd!=null)
				quizzes.add(quizToAdd);
		}

		return quizzes;
	}
	
	private static ArrayList<TQuiz> getTQuizzesFromDatabaseWithQuery(DBConnection db, String query) {
		ArrayList<TQuiz> quizzes = new ArrayList<TQuiz>();
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while (r.next()) {
				TQuiz q = new TQuiz(r);
				if (q!=null)
				quizzes.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes;
	}

	private static ArrayList<Quiz> getMQuizzesFromDatabaseWithQuery(DBConnection db,
			String query) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while (r.next()) {
				Quiz q = new Quiz(r);
				if (q!=null)
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
	public static int getNextQuestionID(DBConnection db) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < Question.QUESTION_TABLES.length; i++) {
			int m = getMaxQuestionID(db, Question.QUESTION_TABLES[i]);
			if (m > max) {
				max = m;
			}
		}
		return max + 1;
	}
	private static int getMaxQuestionID(DBConnection db, String table) {
		//get current max id
		String query = "SELECT MAX(QuestionID) FROM " + table + ";";

		try {
			ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
			if (r.next()) {
				return r.getInt(1);
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
