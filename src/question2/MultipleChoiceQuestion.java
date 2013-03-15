package question2;

import java.sql.ResultSet;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import quiz.Quiz;
import quiz.QuizUtils;
import database.DBConnection;
import database.DatabaseUtils;

public class MultipleChoiceQuestion extends Question{
	String[] answers;
	int answer;
	String questionText;
	public MultipleChoiceQuestion(int qID, int mQID, int o) {
		super(qID, mQID, o);
		mTable = "MCQuestion";
		questionText = "";
		answer = 1;
		answers = new String[4];
		for (int i = 0; i < answers.length; i++) {
			answers[i] = "";
		}
	}
	public MultipleChoiceQuestion(int qID, int mQID, int o,String qt,  int ans, String[] a) {
		super(qID, mQID, o);
		mTable = "MCQuestion";
		questionText = qt;
		answer = ans;
		answers = Arrays.copyOf(a, 4);
	}
	public MultipleChoiceQuestion(ResultSet r) {
		super(r);
		answers = new String[4];
		mTable = "MCQuestion";
		try {
			questionText = r.getString(MultipleChoiceQuestion.QUESTIONTEXT_TABLE_INDEX);
			answers[0] = r.getString(MultipleChoiceQuestion.OPTION1_TABLE_INDEX);
			answers[1] = r.getString(MultipleChoiceQuestion.OPTION2_TABLE_INDEX);
			answers[2] = r.getString(MultipleChoiceQuestion.OPTION3_TABLE_INDEX);
			answers[3] = r.getString(MultipleChoiceQuestion.OPTION4_TABLE_INDEX);
			answer = r.getInt(CORECT_OPTION_TABLE_INDEX);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void storeToDatabase(DBConnection db, int qID) {
		if (questionID == -1 || !removeQuestionFromDatabase(db)) {
			questionID = QuizUtils.getNextQuestionID(db);
		}
		String query = "INSERT INTO MCQuestion VALUES (" + questionID +"," + qID + "," + order + "," + timelimit_seconds + "\"" + questionText + "\",\"" +  
			answers[0] + "\",\"" + answers[1] + "\",\"" + answers[2] + "\",\"" + answers[3] + "\"," + answer + ");";
		System.out.println(query);
		DatabaseUtils.updateDatabase(db, query);
	}

	@Override
	public String getPromptHTML() {
		return questionText + "<br>";
	}

	@Override
	public String getCreateHTML() {
		String ops = order + 1 + ". Response Question:" +
		getCreateHTMLHeader() + 
		"Question: <br><textarea name='" + questionID + "questionfield" + "'rows='5' cols='70'>" + questionText + "</textarea><br>" +
		"Option1: <input type=\"text\" name='" + questionID + "option1' value ='" + answers[0] + "'></br> " +
		"Option2: <input type=\"text\" name='" + questionID + "option2' value ='" + answers[1] + "'></br> " +
		"Option3: <input type=\"text\" name='" + questionID + "option3' value ='" + answers[2] + "'></br> " +
		"Option4: <input type=\"text\" name='" + questionID + "option4' value ='" + answers[3] + "'></br> " +
		"Correct Option (number): <input type=\"text\" name='" + questionID + "correct' value ='" + answer + "'></br> " +
		"";
		return ops;
	}
	
	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		storeTimeLimit(r);
		questionText = r.getParameter(questionID + "questionfield");
		answers[0] = r.getParameter(questionID + "option1");
		answers[1] = r.getParameter(questionID + "option2");
		answers[2] = r.getParameter(questionID + "option3");
		answers[3] = r.getParameter(questionID + "option4");
		answer = Integer.parseInt(r.getParameter(questionID + "correct"));
	}
	@Override
	public String getQuestionHTML() {
		String q = getPromptHTML() + 
		"<input type='radio' name='" + questionID +"options' value=1 >" + answers[0] + "<br>" +
		"<input type='radio' name='" + questionID +"options' value=2 >" + answers[1] +"<br>" +
		"<input type='radio' name='" + questionID +"options' value=3 >" + answers[2] +"<br>" +
		"<input type='radio' name='" + questionID +"options' value=4 >" + answers[3];
		return q;
	}

	@Override
	public String getType() {
		return "MCQuestion";
	}

	@Override
	protected boolean checkUserAnswer(HttpServletRequest request) {
		String uAnswer = request.getParameter(questionID + "options");
		return ("" + answer).equals(uAnswer);
	}

	@Override
	protected void storeUserAnswer(HttpServletRequest request) {
		String uAnswer = request.getParameter(questionID + "options");
		userAnswer = uAnswer;
		
	}

	@Override
	public String getAnswerHTML() {
		return answers[answer - 1];
	}
	public static final int QUESTIONTEXT_TABLE_INDEX = 5;
	public static final int OPTION1_TABLE_INDEX = 6;
	public static final int OPTION2_TABLE_INDEX = 7;
	public static final int OPTION3_TABLE_INDEX = 8;
	public static final int OPTION4_TABLE_INDEX = 9;
	public static final int CORECT_OPTION_TABLE_INDEX = 10;
}
