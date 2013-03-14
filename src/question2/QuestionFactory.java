package question2;

import java.sql.ResultSet;

import database.DBConnection;

public class QuestionFactory {
	public static Question CreateDefaultQuestion(int mQID, int order, int type) {
		if (type == Question.RESPONSE_QUESTION) {
			return new ResponseQuestion(-1, mQID, order);
		}
		if (type == Question.FILL_QUESTION) {
			return new FillQuestion(-1, mQID, order);
		}
		if (type == Question.MULTIPLE_CHOICE_QUESTION) {
			return new MultipleChoiceQuestion(-1,mQID, order);
		}
		return null;
	}
	public static Question getDatabaseQuestion(ResultSet r, int type) {
		switch (type) {
		case Question.RESPONSE_QUESTION:
			return new ResponseQuestion(r);

		case Question.FILL_QUESTION:
			return new FillQuestion(r);

		case Question.MULTIPLE_CHOICE_QUESTION:
			return new MultipleChoiceQuestion(r);
		}
		return null;
	}


}
