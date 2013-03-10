package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import question2.Question;
import question2.QuestionFactory;
import database.DBConnection;
import database.DatabaseUtils;

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
	
	public Quiz (DBConnection db, int id) {
		this(DatabaseUtils.getResultSetFromDatabase(db, "SELECT * FROM mQuiz WHERE mQuizID=" + id + ";"));

	}
	public Quiz(ResultSet r) {
		mQuestions = new ArrayList<Question>();
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
	private void getAllQuestions(DBConnection db) {
		for (int type = 0; type < Question.QUESTION_TABLES.length; type++) {
			String query = "SELECT * FROM" + Question.QUESTION_TABLES[type] + " WHERE QuizID=" + quizID + ";";
			addQuestions(DatabaseUtils.getResultSetFromDatabase(db, query), type);
		}
	}
	private void addQuestions(ResultSet r, int type) {
		try {
			r.beforeFirst();
			while (r.next()) {
				mQuestions.add(QuestionFactory.getDatabaseQuestion(r, type));
			}
		}
		catch(Exception e) {

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
	public void storeToDatabase(DBConnection db) {
		//insert
		int id = QuizUtils.getNextQuizID(db);
		this.quizID = id;
		String datetime = "2013-01-01 12:00:00";
		String query = "INSERT INTO mQuiz VALUES (" + id +",\"" + name + "\",\"" + author + "\",'" + datetime + "'," + numTimesTaken + ");";
		System.out.println("quiz query:" + query);
		DatabaseUtils.updateDatabase(db, query);

		for (Question q : mQuestions) {
			q.storeToDatabase(db, quizID);
		}

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
