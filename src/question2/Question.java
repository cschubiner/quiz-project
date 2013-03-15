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
	public int times_answered_correctly;
	public boolean isTimed;
	public int timelimit_seconds;
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
			timelimit_seconds = r.getInt(Question.TIMELIMIT_TABLE_INDEX);
			
			isTimed = (timelimit_seconds != 0);
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
	public String getCreateHTMLHeader() {
		String checked = isTimed ? "checked" : "";
		return "<input type='checkbox' name='" + questionID +"istimed' value='timed'" + checked + ">Timed?" + 
			"<input type=\"text\" name='" + questionID +"timelimit' value ='" + timelimit_seconds +"' size='1'>" + 	
		"<button name='delete' value='" + questionID + "' type='submit'><img src='images/delete.png' height='20' width='20'></button><br>";
	}
	/*
	 * attempts to remove this question from the database, by ID
	 * returns whether or not the question was there
	 */
	public boolean removeQuestionFromDatabase(DBConnection db) {
		String query = "DELETE FROM " + mTable + " WHERE QuestionID=" + questionID +";";
		return (DatabaseUtils.updateDatabase(db, query) > 0);
	}
	protected void storeTimeLimit(HttpServletRequest request) {
		isTimed = request.getParameter(questionID + "istimed") != null;
		timelimit_seconds = isTimed ? Integer.parseInt(request.getParameter(questionID + "timelimit")) : 0;
	}
	public String getUserAnswer(){
		return userAnswer;
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
		String u = (userCorrect) ? "<FONT COLOR=\"7FFF00\">Correct!</FONT>" : "<FONT COLOR=\"FF0000\">Incorrect</FONT>";
		return "<h4>Question "+ (order +1 ) +"</h4>"+ "<ul><li>Your answer: " + getUserAnswer() + "</li><li>Correct Answer: " + getAnswerHTML() + "</li><li>" + u+"</li></ul>";
	}
	public abstract String getAnswerHTML();
	//TYPE CONSTANTS
	public static final int ID_TABLE_INDEX = 1;
	public static final int QUIZ_ID_TABLE_INDEX = 2;
	public static final int ORDER_TABLE_INDEX = 3;
	public static final int TIMELIMIT_TABLE_INDEX = 4;

	public static final int RESPONSE_QUESTION = 0;
	public static final int FILL_QUESTION = 1;
	public static final int MULTIPLE_CHOICE_QUESTION = 2;
	public static final int PICTURE_QUESTION = 3;
	public static String[] QUESTION_TABLES = {"ResponseQuestion", "FillQuestion","MCQuestion", "PictureQuestion"};
}
