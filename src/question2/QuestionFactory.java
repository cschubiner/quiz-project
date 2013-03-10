package question2;

import java.sql.ResultSet;

public class QuestionFactory {
	public static Question CreateDefaultQuestion(int id, int mQID, int order, int type) {
		if (type == Question.RESPONSE_QUESTION) {
			return new ResponseQuestion(id, mQID, order);
		}
		if (type == Question.FILL_QUESTION) {
			return new FillQuestion(id, mQID, order);
		}
		return null;
	}
	public static Question getDatabaseQuestion(ResultSet r, int type) {
		switch (type) {
		case Question.RESPONSE_QUESTION:
			return new ResponseQuestion(r);
			
		case Question.FILL_QUESTION:
			return new FillQuestion(r);
		}
		
		return null;
	}
	

}
