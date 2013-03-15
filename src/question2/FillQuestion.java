package question2;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import quiz.QuizUtils;

import database.DBConnection;
import database.DatabaseUtils;

public class FillQuestion extends Question{
	private String text;
	private String answerString;
	private String[] answers;
	public FillQuestion(int qID, int mQID, int o) {
		super(qID, mQID, o);
		mTable = "FillQuestion";
		text = "One of President Lincoln's most famous speeches was the ____________ address";
		answerString = "";
		answers = QuestionUtils.parseAnswers(answerString);
	}

	public FillQuestion(ResultSet r) {
		super(r);
		mTable = "FillQuestion";
		try {
			answerString = r.getString(FILL_TABLE_INDEX);
			answers = QuestionUtils.parseAnswers(answerString);
			text = r.getString(TEXT_TABLE_INDEX);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getCreateHTML() {
		String ops = order + 1 + ". Fill Question:" +
			getCreateHTMLHeader() +
			"Text: <br><textarea name='" + questionID + "textfield" + "'rows='5' cols='70'>" + text + "</textarea><br>" +
			"Answers (comma separated): <input type=\"text\" name='" + questionID + "fillfield' value ='" + answerString + "'><br> ";

		return ops;
	}
	
	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		storeTimeLimit(r);
		text = r.getParameter(questionID + "textfield");
		answerString = r.getParameter(questionID + "fillfield");
	}
	
	@Override 
	public String getPromptHTML() {
		return text + "<br>";
	}
	@Override
	public String getQuestionHTML() {
		return getPromptHTML() + 
		" <input type=\"text\" name='" + questionID + "answer'>";
	}
	@Override
	public String getType() {return "Fill Question";}
	static final int TEXT_TABLE_INDEX = 5;
	static final int FILL_TABLE_INDEX = 6;
	@Override
	public void storeToDatabase(DBConnection db, int qID) {
		if (!removeQuestionFromDatabase(db)) {
			questionID = QuizUtils.getNextQuestionID(db);
		}
		String query = "INSERT INTO " + mTable + " VALUES (" + questionID +","  + qID + "," + order + "," + timelimit_seconds + "\"" + text + "\",\"" + answerString +"\");";
		//System.out.println("fill query:" + query);
		DatabaseUtils.updateDatabase(db,query);	
	}

	@Override
	public boolean checkUserAnswer(HttpServletRequest request) {
		for (String a : answers) {
			if (a.compareToIgnoreCase(userAnswer) == 0)
				return true;
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

}
