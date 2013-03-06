package question;

import database.DBConnection;

public class QuestionFactory {
	public static Question CreateQuestion(DBConnection db, int id, int order, int type) {
		if (type == Question.RESPONSE_QUESTION) {
			return new ResponseQuestion(db,id,order);
		}
		if (type == Question.FILL_QUESTION) {
			return new FillQuestion(db, id, order);
		}
		return null;
	}
}
