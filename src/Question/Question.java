package question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public abstract class Question {
	private String questionID;
	private String QuestionType;
	protected String mTable;
	public Question(DBConnection db, String qID) {
		questionID = qID;
		createQuestion(db, qID);
	}
	
	/*
	 * queries the database to fill in data
	 */
	abstract void createQuestion(DBConnection db, String questionID);

	protected ResultSet getQuestionData(DBConnection db,String questionID) {
		Statement stmt = db.getStatement();
		String query = "SELECT * FROM " + mTable + ";";
		ResultSet r = null;
		try {
			r = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
}
