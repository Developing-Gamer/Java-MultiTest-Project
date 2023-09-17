package onlineTest;

public interface QuestionInt {

	public String addTrueFalseQuestion(int examId, int questionNumber,
			String text, double points, boolean answer);

	public String addMultipleChoiceQuestion(int examId, int questionNumber,
			String text, double points, String[] answer);

	public String addFillInTheBlanksQuestion(int examId, int questionNumber,
			String text, double points, String[] answer);

	public String getInfo(int examId, int questionNumber);

	public double getSumOfScores();

	public void answerTrueFalseQuestion(String studentName, int examId,
			int questionNumber, boolean answer);

	public void answerMultipleChoiceQuestion(String studentName, int examId,
			int questionNumber, String[] answer);

	public void answerFillInTheBlanksQuestion(String studentName, int examId,
			int questionNumber, String[] answer);

	public double getExamScore(String studentName, int examId);

	public String getGradingReport(String studentName, int examId, int size);
}
