package question2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import database.DBConnection;
import database.DatabaseUtils;

public abstract class Question {
	protected int questionID;
	protected String QuestionType;
	protected String mTable;
	protected int order;
	protected int mQuizID;
	public boolean userCorrect;
	public String userAnswer;
	public Question(int qID, int mQID, int o) {
		questionID = qID;
		order = o;
		mQuizID = mQID;
	}
	public Question(ResultSet r) {
		try {
			questionID = r.getInt(Question.ID_TABLE_INDEX);
			mQuizID = r.getInt(Question.QUIZ_ID_TABLE_INDEX);
			order = r.getInt(Question.ORDER_TABLE_INDEX);
			
		}
		catch (Exception e) {

		}
	}
	
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
	public void setOrder(int o) {
		order = o;
	}
	public String getDeleteButtonHTML() {
		return "&nbsp;&nbsp;&nbsp;<button name='delete' value='" + questionID + "' type='submit'><img src='images/delete.png' height='20' width='20'></button><br>";
	}
	/*
	 * attempts to remove this question from the database, by ID
	 * returns whether or not the question was there
	 */
	public boolean removeQuestionFromDatabase(DBConnection db) {
		String query = "DELETE FROM " + mTable + " WHERE QuestionID=" + questionID +";";
		return (DatabaseUtils.updateDatabase(db, query) > 0);
	}
	public int getOrder() {return order;}
	public abstract String getCreateHTML();
	public abstract void storeHTMLPost(HttpServletRequest r);
	public abstract void storeToDatabase(DBConnection db, int qID);
	public abstract String getPromptHTML();
	public abstract String getQuestionHTML();
	public abstract String getType();
	protected abstract boolean checkUserAnswer(HttpServletRequest request);
	protected abstract void storeUserAnswer(HttpServletRequest request);
	public boolean checkAnswer(HttpServletRequest request) {
		storeUserAnswer(request);
		userCorrect = checkUserAnswer(request);
		return userCorrect;
	}
	public String getUserCompareHTML() {
		String u = (userCorrect) ? "Correct" : "Incorrect";
		return order + ". Your answer: " + userAnswer + ", Correct Answer:" + getAnswerHTML() + "---" + u;
	}
	public abstract String getAnswerHTML();
	//TYPE CONSTANTS
	public static final int ID_TABLE_INDEX = 1;
	public static final int QUIZ_ID_TABLE_INDEX = 2;
	public static final int ORDER_TABLE_INDEX = 3;


	public static final int RESPONSE_QUESTION = 0;
	public static final int FILL_QUESTION = 1;
	public static final int MULTIPLE_CHOICE_QUESTION = 2;
	public static final int PICTURE_QUESTION = 3;
	public static String[] QUESTION_TABLES = {"ResponseQuestion", "FillQuestion","MCQuestion", "PictureQuestion"};
}
