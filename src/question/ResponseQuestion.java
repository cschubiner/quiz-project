package question;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import database.DBConnection;

public class ResponseQuestion extends Question{

	public ResponseQuestion(DBConnection db, int qID, int order) {
		super(db, qID, order);
		mTable = "ResponseQuestions";
	}
	private String questionText = "";
	private String answer = "";
	
	@Override
	void createQuestion(DBConnection db,int questionID) {
		ResultSet r = getQuestionData(db, questionID);
		try {
			answer = r.getString(ANSWER_TABLE_INDEX);
			questionText = (r.getString(TEXT_TABLE_INDEX));
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
	public String getHTML() {
		String ops = order + ". Response Question:<br> " +
				"Question: <input type=\"text\" name='" + questionID + "questionfield' value='" + questionText + "'></br> " +
				"Response: <input type=\"text\" name='" + questionID + "answerfield' value ='" + answer + "'></br> " +
				"<input type=\"submit\" name='action' value=\"Delete Question " + questionID + "\"></br>";
		
		return ops;
	}
	static final int TEXT_TABLE_INDEX = 0;
	static final int ANSWER_TABLE_INDEX = 1;

	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		questionText = r.getParameter(questionID + "questionfield");
		answer = r.getParameter(questionID + "answerfield");
	}
	
}
