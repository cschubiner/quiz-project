package question2;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import database.DBConnection;

public class ResponseQuestion extends Question{
	private String questionText;
	private String answer;
	public ResponseQuestion(DBConnection db, int qID, int order) {
		super(db, qID, order);
		mTable = "ResponseQuestions";
		questionText = "";
		answer = "";
	}

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
	static final int TEXT_TABLE_INDEX = 0;
	static final int ANSWER_TABLE_INDEX = 1;

	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		questionText = r.getParameter(questionID + "questionfield");
		answer = r.getParameter(questionID + "answerfield");
	}

}
