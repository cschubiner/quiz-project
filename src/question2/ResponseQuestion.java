package question2;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import quiz.QuizUtils;

import database.DBConnection;
import database.DatabaseUtils;

public class ResponseQuestion extends Question{
	private String questionText;
	private String[] answers;
	private String answerString;
	
	public ResponseQuestion(int qID, int mQID, int order) {
		this(qID, mQID, "", "", order);
	}
	public ResponseQuestion(int qID, int mQID, String qt, String a, int order) {
		super( qID, mQID, order);
		mTable = "ResponseQuestion";
		questionText = qt;
		answerString = a;
		answers = QuestionUtils.parseAnswers(a);
	}
	public ResponseQuestion(ResultSet r) {
		super(r);
		mTable = "ResponseQuestion";
		try {
			answerString = r.getString(ResponseQuestion.ANSWER_TABLE_INDEX);
			answers = QuestionUtils.parseAnswers(answerString);
			questionText = (r.getString(ResponseQuestion.TEXT_TABLE_INDEX));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getQuestionText() {
		return questionText;
	}
	public String getPromptHTML() {
		return questionText + "<br>";
	}
	public String getCreateHTML() {
		String ops = order + 1 + ". Response Question:" +
		getCreateHTMLHeader() + 
		"Question: <br><textarea name='" + questionID + "questionfield" + "'rows='5' cols='70'>" + questionText + "</textarea><br>" +
		"Responses (comma separated): <input type=\"text\" name='" + questionID + "answerfield' value ='" + answerString + "'></br> " +
		"";

		return ops;
	}
	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		storeTimeLimit(r);
		questionText = r.getParameter(questionID + "questionfield");
		answerString = r.getParameter(questionID + "answerfield");
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
		String query = "INSERT INTO " + mTable + " VALUES (" + questionID + "," + quizID + "," + order +"," + timelimit_seconds + ",\"" + questionText + "\",\"" + answerString +"\");";
		//System.out.println("response query:" + query);
		DatabaseUtils.updateDatabase(db,query);
	}
	@Override
	public boolean checkUserAnswer(HttpServletRequest request) {
		for (String a : answers) {
			if (a.compareToIgnoreCase(userAnswer) == 0) {
				return true;
			}
		}
		return false;
		
	}
	@Override
	public String getAnswerHTML() {
		return answerString;
	}
	@Override
	protected void storeUserAnswer(HttpServletRequest request) {
		userAnswer = request.getParameter(questionID + "answer").toString();
	}
	static final int TEXT_TABLE_INDEX = 5;
	static final int ANSWER_TABLE_INDEX = 6;

}
