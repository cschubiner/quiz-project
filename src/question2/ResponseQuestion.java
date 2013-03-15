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
		mTable = "ResponseQuestion";
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
	public String getPromptHTML() {
		return order + ". " + questionText + "<br>";
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
	public void storeHTMLPost(HttpServletRequest r) {
		questionText = r.getParameter(questionID + "questionfield");
		answer = r.getParameter(questionID + "answerfield");
	}
	@Override
	public String getQuestionHTML() {
		return getPromptHTML() + 
			" <input type=\"text\" name='" + questionID + "answer' value=''>";
	}
	@Override
	public String getType() {
		return "Response Question";
	}
	

	@Override
	public void storeToDatabase(DBConnection db, int quizID) {
		if (questionID == -1 || !removeQuestionFromDatabase(db)) {
			questionID = QuizUtils.getNextQuestionID(db);
		}
		String query = "INSERT INTO " + mTable + " VALUES (" + questionID + "," + quizID + "," + order +",\"" + questionText + "\",\"" + answer +"\");";
		//System.out.println("response query:" + query);
		DatabaseUtils.updateDatabase(db,query);
	}
	@Override
	public boolean checkUserAnswer(HttpServletRequest request) {
		return (userAnswer.compareToIgnoreCase(answer) == 0);
		
	}
	@Override
	public String getAnswerHTML() {
		return answer;
	}
	@Override
	protected void storeUserAnswer(HttpServletRequest request) {
		userAnswer = request.getParameter(questionID + "answer").toString();
	}
	static final int TEXT_TABLE_INDEX = 4;
	static final int ANSWER_TABLE_INDEX = 5;

}
