package question2;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import quiz.QuizUtils;

import database.DBConnection;
import database.DatabaseUtils;

public class FillQuestion extends Question{
	private String text;
	private String fill;
	public FillQuestion(int qID, int mQID, int o) {
		super(qID, mQID, o);
		mTable = "FillQuestion";
		text = "One of President Lincoln's most famous speeches was the ____________ address";
		fill = "";
	}

	public FillQuestion(ResultSet r) {
		super(r);
		try {
			fill = r.getString(FILL_TABLE_INDEX);
			text = r.getString(TEXT_TABLE_INDEX);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		text = r.getParameter(questionID + "textfield");
		fill = r.getParameter(questionID + "fillfield");
	}

	@Override
	public String getCreateHTML() {
		String ops = order + 1 + ". Fill Question:" +
			getDeleteButtonHTML() +
			"Text: <br><textarea name='" + questionID + "textfield" + "'rows='5' cols='70'>" + text + "</textarea><br>" +
			"Answer: <input type=\"text\" name='" + questionID + "fillfield' value ='" + fill + "'><br> ";

		return ops;
	}
	@Override
	public String getQuestionHTML() {
		return text;
	}
	@Override
	public String getType() {return "Fill Question";}
	static final int TEXT_TABLE_INDEX = 0;
	static final int FILL_TABLE_INDEX = 1;
	@Override
	public void storeToDatabase(DBConnection db, int qID) {
		if (!removeQuestionFromDatabase(db)) {
			questionID = QuizUtils.getNextQuestionID(db, mTable);
		}
		String query = "INSERT INTO " + mTable + " VALUES (" + questionID +","  + qID + ",\"" + text + "\",\"" + fill +"\");";
		System.out.println("fill query:" + query);
		DatabaseUtils.updateDatabase(db,query);
		
	}

}
