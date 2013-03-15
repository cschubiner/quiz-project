package question2;

public class QuestionUtils {
	public static String[] parseAnswers(String a) {
		String[] tokens = a.split(",");
		String[] answers = new String[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			answers[i] = tokens[i].trim();
		}
		return answers;
	}
}
