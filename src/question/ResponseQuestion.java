package question;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;

public final class ResponseQuestion extends Question{

	public ResponseQuestion(DBConnection db, String qID) {
		super(db, qID);
	}
	private String questionText;
	private String answer;
	
	@Override
	void createQuestion(DBConnection db,String questionID) {
		ResultSet r = getQuestionData(db, questionID);
		try {
			answer = r.getString(ANSWER_TABLE_INDEX);
			questionText = (r.getString(TEXT_TABLE_INDEX));
			//questionText =
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	String getQuestionTable() {
		return "responseQuestions";
	}
	
	public String getAnswer() {
		return answer;
	}

	public String getQuestionText() {
		return questionText;
	}
	static final int TEXT_TABLE_INDEX = 0;
	static final int ANSWER_TABLE_INDEX = 1;
	
}
