package question2;

import java.util.List;
import java.io.File;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import quiz.QuizUtils;

import database.DBConnection;
import database.DatabaseUtils;

public class PictureQuestion extends Question{
	private String questionText;
	private String imgPath;
	private String answer;
	
	public PictureQuestion(int qID, int mQID, int o, String q, String p, String a) {
		super(qID, mQID, o);
		questionText = q;
		imgPath = p;
		answer = a;
		mTable = "PictureQuestion";
	}
	public PictureQuestion(int qID, int mQID, int o) {
		this(qID, mQID, o, "", "", "");
	}
	public PictureQuestion(ResultSet r) {
		super(r);
		try {
			questionText = r.getString(QUESTION_TABLE_INDEX);
			imgPath = r.getString(IMGPATH_TABLE_INDEX);
			answer = r.getString(ANSWER_TABLE_INDEX);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		mTable = "PictureQuestion";
	}
	public String getImgHTML() {
		if (imgPath.equals("")) {
			return "";
		}
		return "<img src=\"" + imgPath +"\" style='max-height: 150px; max-width: 150px;'><br>";
	}
	@Override
	public String getQuestionHTML() {
		return getPromptHTML() + getImgHTML() +
			"<input type=\"text\" name='" + questionID + "answer' value=''>";
	}
	@Override
	public String getCreateHTML() {
		String ops = order + 1 + ". Picture Question:" +
		getDeleteButtonHTML() + 
		"Question: <br><textarea name='" + questionID + "questionfield" + "'rows='5' cols='70'>" + questionText + "</textarea><br>" +
		getImgHTML() +		
		"Image Path: <input type=\"text\" name='" + questionID + "imgpathfield' value ='" + imgPath + "'></br> " +
		"Answer: <input type=\"text\" name='" + questionID + "answerfield' value ='" + answer + "'></br> " +
			"";
		return ops;
	}
	@Override
	public void storeHTMLPost(HttpServletRequest r) {
		questionText = r.getParameter(questionID + "questionfield");
		imgPath = r.getParameter(questionID + "imgpathfield");
		answer = r.getParameter(questionID + "answerfield");
		
	}
	
	@Override
	public void storeToDatabase(DBConnection db, int qID) {
		if (!removeQuestionFromDatabase(db)) {
			questionID = QuizUtils.getNextQuestionID(db);
		}
		String query = "INSERT INTO PictureQuestion VALUES (" + questionID + "," + qID + "," + order + ",\"" + questionText + "\",\"" + 
		imgPath + "\",\"" + answer + "\");";
		System.out.println("store picture: " + query);
		DatabaseUtils.updateDatabase(db, query);
		
	}

	@Override
	public String getPromptHTML() {
		return order + ". " + questionText + "<br>";
	}

	@Override
	public String getType() {
		return "PictureQuestion";
	}

	@Override
	protected boolean checkUserAnswer(HttpServletRequest request) {
		return answer.equals(userAnswer);
	}

	@Override
	protected void storeUserAnswer(HttpServletRequest request) {
		userAnswer = request.getParameter(questionID+ "answer").toString();
	}

	@Override
	public String getAnswerHTML() {
		return answer;
	}
	public static final int QUESTION_TABLE_INDEX = 4;
	public static final int IMGPATH_TABLE_INDEX = 5;
	public static final int ANSWER_TABLE_INDEX = 6;
}
