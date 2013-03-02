package question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public abstract class Question {
	String questionID;
	String QuestionType;
	public Question(DBConnection db, String qID) {
		questionID = qID;
		createQuestion(db, qID);
	}
	
	/*
	 * queries the database to fill in data
	 */
	abstract void createQuestion(DBConnection db, String questionID);
	abstract String getQuestionTable();
	protected ResultSet getQuestionData(DBConnection db,String questionID) {
		Statement stmt = db.getStatement();
		String query = "SELECT * FROM " + getQuestionTable() + ";";
		ResultSet r = null;
		try {
			r = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	
	
}
