package Question;

public abstract class Question {
	String name;
	String username;
	
	public Question(String questionID) {
		createQuestion(questionID);
	}
	/*
	 * queries the database to fill in data
	 */
	abstract void createQuestion(String questionID);
}
