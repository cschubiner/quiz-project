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
	private int score;
	private int ordering;
	private int paging;
	private int grading;
	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int AUTHOR = 3;
	private static final int MODIFIED = 4;
	private static final int TIMESTAKEN = 5;
	private static final int ORDERING = 6;
	private static final int PAGING = 7;
	private static final int GRADING = 8;
	private int numTimesTaken;
	public Quiz(String a) {
		author = a;
		name = "";
		quizID = 0;
		mQuestions = new ArrayList<Question>();
		numTimesTaken = 0;
	}

	public Quiz(ResultSet r) {
		mQuestions = new ArrayList<Question>();
		try {
			quizID = r.getInt(ID);
			name = r.getString(NAME);
			author = r.getString(AUTHOR);
			lastModified = r.getString(MODIFIED);
			numTimesTaken = r.getInt(TIMESTAKEN);
			ordering = r.getInt(ORDERING);
			paging = r.getInt(PAGING);
			grading = r.getInt(GRADING);
		} catch (SQLException e) {
			System.out.println("error constructing quiz");
		}
	}
	public void getAllQuestions(DBConnection db) {
		for (int type = 0; type < Question.QUESTION_TABLES.length; type++) {
			String query = "SELECT * FROM " + Question.QUESTION_TABLES[type] + " WHERE mQuizID=" + quizID + ";";
			//System.out.println(query);
			addQuestions(DatabaseUtils.getResultSetFromDatabase(db, query), type);
		}
	}
	private void addQuestions(ResultSet r, int type) {
		
		try {
			r.beforeFirst();
			while (r.next()) {
				Question toAdd = QuestionFactory.getDatabaseQuestion(r, type);
				int i = 0;
				for (i = 0;  i < mQuestions.size() && mQuestions.get(i).getOrder() < toAdd.getOrder(); i++);
				mQuestions.add(i, toAdd);
			}
		}
		catch(SQLException e) {
			System.out.println("error creating question");
		}
	}
	public void randomizeQuestions() {
		ArrayList<Question> temp = new ArrayList<Question>();
		while (mQuestions.size() > 0) {
			int i = (int)( Math.random() * mQuestions.size());
			temp.add(mQuestions.remove(i));
		}
		mQuestions = temp;
	}
	public boolean removeQuizFromDatabase(DBConnection db ) {
		String query = "DELETE FROM mQuiz WHERE mQuizID=" + quizID + ";";
		return (DatabaseUtils.updateDatabase(db, query) > 0);
	}
	public void storeToDatabase(DBConnection db) {
		if (!removeQuizFromDatabase(db)) {
			this.quizID = QuizUtils.getNextQuizID(db);
		}
		//		insert
		String datetime = DatabaseUtils.getTimestamp();
		String query = "INSERT INTO mQuiz VALUES (" + this.quizID +",\"" + name + "\",\"" + author + "\",'" + datetime + "'," + numTimesTaken + ","
						+ ordering + "," + paging + "," + grading +");";
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
	public String getHTMLOptions() {
		String o1,o2,p1,p2,g1,g2;
		if (ordering == ORDER_IN_ORDER) {o1 = "checked"; o2 = "";}
		else {o1 = ""; o2 = "checked";}
		if (paging == PAGING_SINGLE_PAGE) {p1 = "checked"; p2 = "";}
		else {p1 = ""; p2 = "checked";}
		if (grading == GRADING_ALL_AT_END) {g1 = "checked"; g2 = "";}
		else {g1 = ""; g2 = "checked";}
		
		String ops = "" +
		"Ordering:&nbsp;&nbsp;<input type='radio' name='ordering' value='" + Quiz.ORDER_IN_ORDER +"'" + o1 +">In Order" +
				"&nbsp;&nbsp;<input type='radio' name='ordering' value='" + Quiz.ORDER_RANDOM_ORDER +"'" + o2 +">Random<br>" +
		"Paging:&nbsp;&nbsp;<input type='radio' name='paging' value='"+ Quiz.PAGING_SINGLE_PAGE +"'" + p1 +">Single" +
				"&nbsp;&nbsp;<input type='radio' name='paging' value='" + Quiz.PAGING_MULTI_PAGE+ "'" + p2 +">Multiple<br>" +
		"Grading:&nbsp;&nbsp;<input type='radio' name='grading' value='" + Quiz.GRADING_ALL_AT_END+ "'" + g1 + ">All at End" +
				"&nbsp;&nbsp;<input type='radio' name='grading' value='" + Quiz.GRADING_IMMEDIATE+ "'" + g2 +">Immediate";
		
		return ops;
	}
	public void updateFromHTML(HttpServletRequest request) {
		name = request.getParameter("qname");
		ordering = Integer.parseInt(request.getParameter("ordering").toString());
		paging = Integer.parseInt(request.getParameter("paging").toString());
		grading = Integer.parseInt(request.getParameter("grading").toString());
		for (Question q : mQuestions) {
			System.out.println(q.getID());
			q.storeHTMLPost(request);
		}
	}
//	public boolean evaluateAnswer(HttpServletRequest request, int id) {
//		for (int i = 0; i < mQuestions.size(); i++) {
//			if (mQuestions.get(i).getID() == id) {
//				if( mQuestions.get(i).checkAnswer(request)) {
//					score += 1;
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	public boolean evaluateAnswer(HttpServletRequest request, int i) {
		boolean result;
		if (result = mQuestions.get(i).checkAnswer(request)) {
			score++;
		}
		return result;
	}
	public int evaluateAllAnswers(HttpServletRequest request) {
		int numCorrect = 0;
		for (Question q: mQuestions) {
			if (q.checkAnswer(request)) {
				numCorrect += 1;
			}
		}
		score = numCorrect;
		return numCorrect;
	}
	public boolean recordTQuiz(DBConnection db, String takenBy) {
		TQuiz tq = new TQuiz(quizID,takenBy, DatabaseUtils.getTimestamp(), score);
		return tq.record(db);
	}
	public String getScoreString() {
		return score + " / " + mQuestions.size();
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
	public int getPaging() {
		return paging;
	}
	public int getOrdering() {
		return ordering;
	}
	public int getGrading() {
		return grading;
	}
	public static final int PAGING_SINGLE_PAGE = 0;
	public static final int PAGING_MULTI_PAGE = 1;
	
	public static final int ORDER_IN_ORDER = 0;
	public static final int ORDER_RANDOM_ORDER = 1;
	
	public static final int GRADING_ALL_AT_END = 0;
	public static final int GRADING_IMMEDIATE = 1;

}
