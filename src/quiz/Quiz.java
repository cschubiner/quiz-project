package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import question.Question;

public class Quiz {
	ArrayList<Question> mQuestions;
	private String name;
	private int quizID;
	private String author;
	private String lastModified;
	private String description;
	private int question_id_seed = 0;
	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int AUTHOR = 3;
	private static final int MODIFIED = 4;
	private static final int TIMESTAKEN = 5;
	private int numTimesTaken;
	public Quiz(String a) {
		author = a;
		name = "";
		quizID = 0;
		mQuestions = new ArrayList<Question>();
		numTimesTaken = 0;
	}
	public Quiz(ResultSet r) {
		try {
			quizID = r.getInt(ID);
			name = r.getString(NAME);
			author = r.getString(AUTHOR);
			lastModified = r.getString(MODIFIED);
			numTimesTaken = r.getInt(TIMESTAKEN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getNumTimesTaken(){
		return numTimesTaken;
	}

	public int getID (){
		return quizID;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return description;
	}

	public String getAuthor(){
		return author;
	}
	
	public String getLastModified(){
		return lastModified;
	}
	
	public void addQuestion(Question q) {
		mQuestions.add(q);
	}
	public void removeQuestion(int id) {
		for (int i = 0; i < mQuestions.size(); i++) {
			if (mQuestions.get(i).getID() == id){
				mQuestions.remove(i);
			}
		}
	}
	public ArrayList<Question> getQuestions(){
		return mQuestions;
	}
	public void updateFromHTML(HttpServletRequest request) {
		name = request.getParameter("qname");
		for (Question q : mQuestions) {
			q.storeHTMLPost(request);
		}
	}
	public int getNextQuestionID() {
		return question_id_seed++;
	}
}
