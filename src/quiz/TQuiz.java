package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import database.DatabaseUtils;

public class TQuiz {
	private int tQuizID;
	private int mQuizID;
	private String takenBy;
	private String timeTaken;
	private int score;

	public TQuiz(ResultSet r) {
		try {
			tQuizID = r.getInt(1);
			mQuizID = r.getInt(2);
			takenBy = r.getString(3);
			timeTaken = r.getString(4);
			score = r.getInt(5);
		} catch (SQLException e) {
			System.out.println("error constructing tQuiz");
			
		}
	}
	public TQuiz(int qID, String user, String time, int s) {
		mQuizID = qID;
		takenBy = user;
		timeTaken = time;
		score = s;
	}
	public boolean record(DBConnection db) {
		String query = "INSERT INTO tQuiz (mQuizID, TakenBy, TimeTaken,Score) VALUES (" + mQuizID +",'" + takenBy +"','" + timeTaken +"'," + score +");";
		int r = DatabaseUtils.updateDatabase(db, query);
		return r != 0;
	}
	public int gettQuizID() {
		return tQuizID;
	}

	public int getmQuizID() {
		return mQuizID;
	}

	public String getTakenBy() {
		return takenBy;
	}

	public String getTimeTaken() {
		return timeTaken;
	}


	public int getScore() {
		return score;
	}

}
