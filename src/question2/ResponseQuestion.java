package question2;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import quiz.QuizUtils;

import database.DBConnection;
import database.DatabaseUtils;

public class ResponseQuestion extends Question{
	private String questionText;
	private String answer;
	public ResponseQuestion(int qID, int mQID, int order) {
		this(qID, mQID, "", "", order);
	}
	public ResponseQuestion(int qID, int mQID, String qt, String a, int order) {
		super( qID, mQID, order);
		mTable = "ResponseQuestion";
		questionText = qt;
		answer = a;	
	}
	public ResponseQuestion(ResultSet r) {
		super(r);
		try {
			answer = r.getString(ResponseQuestion.ANSWER_TABLE_INDEX);
			questionText = (r.getString(ResponseQuestion.TEXT_TABLE_INDEX));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public String getAnswer() {
		return answer;
	}

	public String getQuestionText() {
		return questionText;
	}
	public String getCreateHTML() {
		String ops = order + 1 + ". Response Question:" +
		getDeleteButtonHTML() + 
		"Question: <br><textarea name='" + questionID + "questionfield" + "'rows='5' cols='70'>" + questionText + "</textarea><br>" +
		"Response: <input type=\"text\" name='" + questionID + "answerfield' value ='" + answer + "'></br> " +
		"";

		return ops;
	}
	@Override
	public String getQuestionHTML() {
		return questionText;
	}
	@Override
	public String getType() {
		return "Response Question";
	}
	static final int TEXT_TABLE_INDEX = 4;
	static final int ANSWER_TABLE_INDEX = 5;

	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		questionText = r.getParameter(questionID + "questionfield");
		answer = r.getParameter(questionID + "answerfield");
	}

	@Override
	public void storeToDatabase(DBConnection db, int qID) {
		if (!removeQuestionFromDatabase(db)) {
			questionID = QuizUtils.getNextQuestionID(db, mTable);
		}
		String query = "INSERT INTO " + mTable + " VALUES (" + questionID + "," + qID + "," + order +",\"" + questionText + "\",\"" + answer +"\");";
		System.out.println("response query:" + query);
		DatabaseUtils.updateDatabase(db,query);
	}



}
