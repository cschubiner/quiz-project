package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import question.Question;

public class Quiz {
	ArrayList<Question> mQuestions;
	private String name;
	private int quizID;
	private String author;
	private String lastModified;

	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int AUTHOR = 3;
	private static final int MODIFIED = 4;

	public Quiz(ResultSet r) {
		try {
			quizID = r.getInt(ID);
			name = r.getString(NAME);
			author = r.getString(AUTHOR);
			lastModified = r.getString(MODIFIED);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getID (){
		return quizID;
	}

	public String getName() {
		return name;
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
}
