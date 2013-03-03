package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import question.Question;

public class Quiz {
	ArrayList<Question> mQuestions;
	private String name;
	String author;
	
	public Quiz(ResultSet r) {
		try {
			name = r.getString(2);
			author = r.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public String getName() {
		return name;
	}
}
