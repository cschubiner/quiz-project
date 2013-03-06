package question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import database.DBConnection;

public abstract class Question {
	protected int questionID;
	protected String QuestionType;
	protected String mTable;
	protected int order;
	public Question(DBConnection db, int qID, int o) {
		questionID = qID;
		order = o;
		//createQuestion(db, qID);
	}
	
	/*
	 * queries the database to fill in data
	 */
	abstract void createQuestion(DBConnection db, int questionID);
	public abstract void storeHTMLPost(HttpServletRequest r);
	public int getID() {
		return questionID;
	}
	protected ResultSet getQuestionData(DBConnection db,int questionID) {
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
	public abstract String getCreateHTML();
	public abstract String getQuestionHTML();
	public abstract String getType();
	//TYPE CONSTANTS
	public static int RESPONSE_QUESTION = 0;
	public static int FILL_QUESTION = 1;
}
