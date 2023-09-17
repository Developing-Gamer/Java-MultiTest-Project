package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultipleChoiceQuestion implements QuestionInt, Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<Integer, HashMap<Integer, ArrayList<Object>>> queId = new HashMap<>();
	private HashMap<String, HashMap<Integer, Double>> studentScore = new HashMap<>();
	private Map<Integer, Map<String, Double>> studentScoreExtra = new HashMap<>();
	private HashMap<Integer, ArrayList<Object>> allQuestions = new HashMap<>();
	private HashMap<String, Double> score = new HashMap<>();
	private double sumOfScores = 0.0;
	private int currentExamId = 0;
	private HashMap<Integer, Double> tempMap = new HashMap<>();

	@Override
	public String addMultipleChoiceQuestion(int examId, int questionNumber,
			String text, double points, String[] answer) {

		ArrayList<Object> otherInfo = new ArrayList<>();

		HashMap<Integer, Object> question = new HashMap<>(),
				pointsMap = new HashMap<>(), answersMap = new HashMap<>();

		if (currentExamId != 0 && currentExamId != examId) {
			allQuestions = new HashMap<>();
		}

		Arrays.sort(answer);

		question.put(questionNumber, text);
		pointsMap.put(questionNumber, points);
		answersMap.put(questionNumber, answer);

		otherInfo.add(question.get(questionNumber));
		otherInfo.add(pointsMap.get(questionNumber));
		otherInfo.add(answersMap.get(questionNumber));

		allQuestions.put(questionNumber, otherInfo);
		queId.put(examId, allQuestions);

		currentExamId = examId;

		return "MCQ";
	}

	public String getInfo(int examId, int questionNumber) {

		String answer = "";
		ArrayList<Object> infoList = queId.get(examId).get(questionNumber);

		answer += "Question Text: " + infoList.get(0) + "\n";
		answer += "Points: " + infoList.get(1) + "\n";
		answer += "Correct Answer: "
				+ Arrays.toString((String[]) infoList.get(2)) + "\n";

		return answer;
	}

	int prevExamId = 0;
	private HashMap<Integer, HashMap<String, ArrayList<Double>>> examData = new HashMap<>();

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId,
			int questionNumber, String[] answer) {

		ArrayList<Object> infoList = queId.get(examId).get(questionNumber);

		Arrays.sort(answer);

		if (queId.containsKey(examId)
				&& queId.get(examId).containsKey(questionNumber)) {

			if (!studentScore.containsKey(studentName)
					|| !studentScore.get(studentName).containsKey(examId)) {

				score = new HashMap<>();
				studentScore = new HashMap<>();
				tempMap.put(examId, 0.0);
				studentScore.put(studentName, tempMap);
			}

			Double value = 0.0;

			if (infoList != null
					&& Arrays.equals((String[]) infoList.get(2), answer)) {
				if (studentScore.get(studentName).containsKey(examId)) {
					score.put(studentName,
							studentScore.get(studentName).get(examId)
									+ (double) infoList.get(1));
				}

				studentScore.get(studentName).replace(examId,
						score.get(studentName));

				if (prevExamId == 0 || prevExamId != examId) {
					studentScoreExtra.put(examId, new HashMap<>());
				}

				studentScoreExtra.get(examId).put(studentName,
						studentScore.get(studentName).get(examId));

				prevExamId = examId;

				value = (double) infoList.get(1);

				if (!examData.containsKey(examId)) {
					examData.put(examId, new HashMap<>());
				}

				HashMap<String, ArrayList<Double>> examAnswers = examData
						.get(examId);

				if (!examAnswers.containsKey(studentName)) {
					examAnswers.put(studentName, new ArrayList<>());
				}

				ArrayList<Double> studentAnswers = examAnswers.get(studentName);
				studentAnswers.add(value);

			} else {

				if (!examData.containsKey(examId)) {
					examData.put(examId, new HashMap<>());
				}

				HashMap<String, ArrayList<Double>> examAnswers = examData
						.get(examId);

				if (!examAnswers.containsKey(studentName)) {
					examAnswers.put(studentName, new ArrayList<>());
				}

				ArrayList<Double> studentAnswers = examAnswers.get(studentName);
				studentAnswers.add(value);
			}
		}
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		try {
			if (studentScoreExtra.get(examId).containsKey(studentName)) {
				double answer = 0.0;

				answer += studentScoreExtra.get(examId).containsKey(studentName)
						? studentScoreExtra.get(examId).get(studentName)
						: 0.0;

				return answer;
			}
			return 0.0;
		} catch (Exception e) {
			return 0.0;
		}
	}

	@Override
	public double getSumOfScores() {
		return sumOfScores;
	}

	@Override
	public String getGradingReport(String studentName, int examId, int size) {
		String answer = "";

		try {
			HashMap<String, ArrayList<Double>> examAnswers = examData
					.get(examId);
			ArrayList<Double> studentAnswers = examAnswers.get(studentName);
			sumOfScores = 0.0;
			int count = 0;

			for (int i = 1; i <= size; i++) {
				if (queId.get(examId).get(i) != null) {
					ArrayList<Object> infoList = queId.get(examId).get(i);
					sumOfScores += (double) infoList.get(1);

					while (count < studentAnswers.size()) {
						if (studentAnswers.get(count) != null) {
							answer += "Question #" + i + " "
									+ studentAnswers.get(count)
									+ " points out of " + infoList.get(1)
									+ "\n";
							count++;
							break;
						}
					}
				}
			}
			return answer;
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String addTrueFalseQuestion(int examId, int questionNumber,
			String text, double points, boolean answer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId,
			int questionNumber, boolean answer) {
		// TODO Auto-generated method stub

	}

	@Override
	public String addFillInTheBlanksQuestion(int examId, int questionNumber,
			String text, double points, String[] answer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId,
			int questionNumber, String[] answer) {
		// TODO Auto-generated method stub

	}
}
