package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

import user.UserUtils;

import database.DBConnection;
import database.DatabaseUtils;

public class TQuiz {
	private int tQuizID;
	private int mQuizID;
	private String takenBy;
	private String timeTaken;
	private int duration_seconds;
	private int score;

	public TQuiz(ResultSet r) {
		try {
			tQuizID = r.getInt(1);
			mQuizID = r.getInt(2);
			takenBy = r.getString(3);
			timeTaken = r.getString(4);
			score = r.getInt(5);
			duration_seconds = r.getInt(6);
		} catch (SQLException e) {
			System.out.println("error constructing tQuiz");
			
		}
	}
	public int getDuration_seconds() {
		return duration_seconds;
	}
	public TQuiz(int qID, String user, String time, int s, int d) {
		mQuizID = qID;
		takenBy = user;
		timeTaken = time;
		score = s;
		duration_seconds = d;
	}
	public boolean record(DBConnection db) {
		String query = "INSERT INTO tQuiz (mQuizID, TakenBy, TimeTaken,Score, Duration) VALUES (" + mQuizID +",'" + takenBy +"','" + timeTaken +"'," + score +"," + duration_seconds+");";
		int r = DatabaseUtils.updateDatabase(db, query);
		return r != 0;
	}
	public String toString() {
		return "Score: " + score +" by " + UserUtils.getUserLinkString(takenBy) + " - Duration: " + QuizUtils.getDurationString(duration_seconds);
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
