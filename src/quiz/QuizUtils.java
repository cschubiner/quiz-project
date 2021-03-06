package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import question2.Question;
import database.DBConnection;
import database.DatabaseUtils;

public class QuizUtils {
	public static void removeMQuizFromDatabase(DBConnection db, int id){
		DatabaseUtils.updateDatabase(db, "DELETE FROM tQuiz WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "DELETE FROM ReportedQuizzes WHERE mQuizID="+id+";");
		for (String table : Question.QUESTION_TABLES){
			DatabaseUtils.updateDatabase(db, "DELETE FROM "+table+" WHERE mQuizID="+id+";");
		}
		DatabaseUtils.updateDatabase(db, "DELETE FROM mQuiz WHERE mQuizID="+id+";");
	}

	public static void removeHistoryOfMQuizFromDatabase(DBConnection db, int id){
		DatabaseUtils.updateDatabase(db, "DELETE FROM tQuiz WHERE mQuizID="+id+";");
		DatabaseUtils.updateDatabase(db, "UPDATE mQuiz SET NumTaken = 0 where mQuizID = "+id+";");
	}

	public static String getHowLongAgo(String pastTime){
		String now = DatabaseUtils.getTimestamp();
		int diffSeconds = secondsFromTime(now) - secondsFromTime(pastTime);

		if (diffSeconds <= 7)
			return "Just seconds ago";
		
		if (diffSeconds < 7200)
			return getDurationString(diffSeconds) + " ago";
		
		if (diffSeconds < 86400)
			return "" + Integer.toString((int)(diffSeconds/3600))+ " hours ago";

		if (diffSeconds < 172800)
			return "Yesterday";

		if (diffSeconds < 2592000)
			return "" + Integer.toString((int)(diffSeconds/86400))+ " days ago";

		int monthsAgo = diffSeconds/2592000;
		return "" + monthsAgo + " month" + (monthsAgo == 1 ? "" : "s") + " ago";
	}

	public static int secondsFromTime(String time) {
		//yyyy-MM-dd HH:mm:ss
		String delims = "[- :]+";
		String[] tokens = time.split(delims);
		int seconds = Integer.parseInt(tokens[0]) * 31536000; //year
		seconds += Integer.parseInt(tokens[1]) * 2592000; //month
		seconds += Integer.parseInt(tokens[2]) * 86400; //day
		seconds += Integer.parseInt(tokens[3]) * 3600; //hours
		seconds += Integer.parseInt(tokens[4]) * 60; //minutes
		seconds += (int)Float.parseFloat(tokens[5]) * 1; //seconds

		return seconds;
	}

	public static String getDurationString(int seconds){
		String ret = "";
		int hours =  (int) TimeUnit.SECONDS.toHours(seconds);
		if (hours != 0)
			ret += hours + " hour" +(hours == 1 ? "" : "s")+ " ";

		int minutes =  (int) TimeUnit.SECONDS.toMinutes(seconds);
		if (hours > 0)
			minutes -= 60*hours;
		if (minutes != 0)
			ret += minutes + " minute" +(minutes == 1 ? "" : "s")+ " ";
		ret += (seconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds))) + " second" +(seconds == 1 ? "" : "s")+ " ";
		return ret;
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
	
	public static TQuiz getTopScore(DBConnection db, int quizID, String username) {
		String query = "SELECT * FROM tQuiz WHERE TakenBy='"+username+ "' And mQuizID=" + quizID + " ORDER BY Score DESC, TimeTaken DESC;";
		ArrayList<TQuiz> topScores = QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
		System.out.println(topScores.size());
		
		if(topScores.size()==0) return null;
		return topScores.get(0);
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
	public static ArrayList<TQuiz> getXHighestScoringtQuizzesLastDay(DBConnection db, int quizID, int limit) {
		String datebefore = DatabaseUtils.getPreviousTime(1);
		String query = "SELECT * FROM tQuiz WHERE mQuizID=" +quizID + " AND TimeTaken > '" + datebefore +"' ORDER BY Score DESC, Duration LIMIT " + limit+";";
		return QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
	}
	public static ArrayList<TQuiz> getXHighestScoringtQuizzes(DBConnection db, int quizID, int limit) {
		String query = "SELECT * FROM tQuiz WHERE mQuizID=" +quizID + " ORDER BY Score DESC, Duration LIMIT " + limit+";";
		return QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
	}
	public static ArrayList<TQuiz> getXRecenttQuizzes(DBConnection db, int quizID, int limit) {
		String query = "SELECT * FROM tQuiz WHERE mQuizID=" + quizID + " ORDER BY TimeTaken DESC LIMIT " + limit+";";
		return QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
	}

	public static ArrayList<TQuiz> getXRecenttQuizzesTakenByUser(DBConnection db, int quizID, int limit, String userName) {
		String query = "SELECT * FROM tQuiz WHERE mQuizID=" + quizID + " and TakenBy = '"+userName+"' ORDER BY TimeTaken DESC LIMIT " + limit+";";
		return QuizUtils.getTQuizzesFromDatabaseWithQuery(db, query);
	}

	
	public static String getQuizSummary(DBConnection db, int quizID) {
		String query = "SELECT COUNT(*),AVG(Score) FROM tQuiz WHERE mQuizID=" + quizID+";";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);

		String ans = "";
		try {
			r.first();
			ans += "This quiz has been taken " + r.getInt(1) + " times with an average score of " + r.getDouble(2) + " points";
		}
		catch (Exception e) {System.out.println("error");}
		return ans;
	}
	public static ArrayList<Quiz> getXMostRecentQuizzesTakenByUser(DBConnection db, String user, int howManyToGet) {
		String query = "select * from tQuiz b where b.TakenBy = '"+user+"' and b.TimeTaken = (select MAX(TimeTaken) from tQuiz  a where a.TakenBy = '"+user+"' and a.mQuizID = b.mQuizID) ORDER by TimeTaken DESC LIMIT "+howManyToGet+";";
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		ArrayList<TQuiz> tQuizzes = getTQuizzesFromDatabaseWithQuery(db, query);
		for (int i = 0 ; i < tQuizzes.size(); i++){
			Quiz quizToAdd = getQuizByID(db, tQuizzes.get(i).getmQuizID());
			if (quizToAdd!=null){
				quizToAdd.dateIssued = tQuizzes.get(i).getTimeTaken();
				quizzes.add(quizToAdd);
			}
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
	public static int getNumTimesTaken(DBConnection db, int quizID) {
		String query = "SELECT COUNT(*) FROM tQuiz WHERE mQuizID=" + quizID +";";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		int n = 0;
		try {
			r.first();
			n = r.getInt(1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

}
