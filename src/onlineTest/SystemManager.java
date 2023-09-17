package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SystemManager implements Manager, Serializable {

	private static final long serialVersionUID = 1L;

	String title = "", tfQ = "TFQ", mcQ = "MCQ", fbQ = "FBQ";
	private ArrayList<Integer> examId = new ArrayList<>();
	private HashMap<Integer, ArrayList<String>> database = new HashMap<>();
	ArrayList<String> typeList;
	private TFQuestion qtnTF = new TFQuestion();
	private MultipleChoiceQuestion qtnMC = new MultipleChoiceQuestion();
	private FillBlanksQuestion qtnFB = new FillBlanksQuestion();
	private ArrayList<String> studentNames = new ArrayList<>();
	private HashMap<Double, String> courseLetterGrade = new HashMap<>();
	private double[] cutoffs;
	private Map<Integer, Map<String, Double>> minMaxScore = new HashMap<>();
	private Map<Integer, Double> finalMaxScore = new HashMap<>();

	@Override
	public boolean addExam(int examId, String title) {
		this.title = title;
		this.examId.add(examId);
		typeList = new ArrayList<>();

		if (database.containsKey(examId)) {
			return false;
		}

		minMaxScore.put(examId, null);
		database.put(examId, null);
		return true;
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber,
			String text, double points, boolean answer) {

		if (database.containsKey(examId)) {

			typeList.add(qtnTF.addTrueFalseQuestion(examId, questionNumber,
					text, points, answer));

			database.replace(examId, typeList);
		}
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber,
			String text, double points, String[] answer) {

		if (database.containsKey(examId)) {

			typeList.add(qtnMC.addMultipleChoiceQuestion(examId, questionNumber,
					text, points, answer));

			database.replace(examId, typeList);
		}
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber,
			String text, double points, String[] answer) {

		if (database.containsKey(examId)) {

			typeList.add(qtnFB.addFillInTheBlanksQuestion(examId,
					questionNumber, text, points, answer));

			database.replace(examId, typeList);
		}
	}

	@Override
	public String getKey(int examId) {
		int i = 0;
		String answer = "";

		if (database.containsKey(examId)) {
			while (i < database.get(examId).size()) {

				if (database.get(examId).get(i) == tfQ) {
					answer += qtnTF.getInfo(examId, (i + 1));
				} else if (database.get(examId).get(i) == mcQ) {
					answer += qtnMC.getInfo(examId, (i + 1));
				} else {
					answer += qtnFB.getInfo(examId, (i + 1));
				}
				i++;
			}
		}

		return answer;
	}

	@Override
	public boolean addStudent(String name) {

		if (studentNames.contains(name)) {
			return false;
		}

		studentNames.add(name);
		return true;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId,
			int questionNumber, boolean answer) {
		qtnTF.answerTrueFalseQuestion(studentName, examId, questionNumber,
				answer);
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId,
			int questionNumber, String[] answer) {
		qtnMC.answerMultipleChoiceQuestion(studentName, examId, questionNumber,
				answer);
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId,
			int questionNumber, String[] answer) {
		qtnFB.answerFillInTheBlanksQuestion(studentName, examId, questionNumber,
				answer);
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		double answer = 0.0;

		answer += qtnTF.getExamScore(studentName, examId)
				+ qtnMC.getExamScore(studentName, examId)
				+ qtnFB.getExamScore(studentName, examId);

		return answer;
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		String answer = "";

		answer += qtnTF.getGradingReport(studentName, examId,
				database.get(examId).size())
				+ qtnMC.getGradingReport(studentName, examId,
						database.get(examId).size())
				+ qtnFB.getGradingReport(studentName, examId,
						database.get(examId).size());

		Double tempSOS = 0.0;
		Double examScore = getExamScore(studentName, examId);

		if (database.get(examId).contains("TFQ")) {
			tempSOS += qtnTF.getSumOfScores();
		}
		if (database.get(examId).contains("MCQ")) {
			tempSOS += qtnMC.getSumOfScores();
		}
		if (database.get(examId).contains("FBQ")) {
			tempSOS += qtnFB.getSumOfScores();
		}

		answer += "Final Score: " + examScore + " out of " + tempSOS;

		finalMaxScore.put(examId, tempSOS);

		return answer;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades,
			double[] cutoffs) {
		this.cutoffs = cutoffs;
		for (int i = 0; i < letterGrades.length; i++) {
			courseLetterGrade.put(cutoffs[i], letterGrades[i]);
		}
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double soln = 0.0;
		double answer = 0.0;
		double tempSOS = 0.0;

		for (int i = 0; i < examId.size(); i++) {
			answer = getExamScore(studentName, examId.get(i));
			getGradingReport(studentName, examId.get(i));
			tempSOS = finalMaxScore.get(examId.get(i));
			soln += (answer / tempSOS);
		}

		return (soln / examId.size()) * 100;
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		String answer = "";

		for (int i = cutoffs.length - 1; i >= 0; i--) {
			if (getCourseNumericGrade(studentName) >= cutoffs[i]) {
				answer = courseLetterGrade.get(cutoffs[i]);
			}
		}

		System.out.println(getCourseNumericGrade(studentName));
		return answer;
	}

	@Override
	public String getCourseGrades() {
		String answer = "";

		Collections.sort(studentNames);

		for (int i = 0; i < studentNames.size(); i++) {
			String name = studentNames.get(i);
			answer += name + " " + getCourseNumericGrade(name) + " "
					+ getCourseLetterGrade(name) + "\n";
		}

		return answer;
	}

	@Override
	public double getMaxScore(int examId) {
		Double answer = 0.0;
		Double temp = 0.0;

		for (int i = 0; i < studentNames.size(); i++) {

			temp = qtnTF.getExamScore(studentNames.get(i), examId)
					+ qtnMC.getExamScore(studentNames.get(i), examId)
					+ qtnFB.getExamScore(studentNames.get(i), examId);

			if (temp > answer) {
				answer = temp;
			}
		}

		return answer;
	}

	@Override
	public double getMinScore(int examId) {
		Double answer = Double.MAX_VALUE;
		Double temp = 0.0;

		for (int i = 0; i < studentNames.size(); i++) {

			temp = qtnTF.getExamScore(studentNames.get(i), examId)
					+ qtnMC.getExamScore(studentNames.get(i), examId)
					+ qtnFB.getExamScore(studentNames.get(i), examId);

			if (temp < answer) {
				answer = temp;
			}
		}

		return answer;
	}

	@Override
	public double getAverageScore(int examId) {
		Double answer = 0.0;

		for (int i = 0; i < studentNames.size(); i++) {

			answer += qtnTF.getExamScore(studentNames.get(i), examId)
					+ qtnMC.getExamScore(studentNames.get(i), examId)
					+ qtnFB.getExamScore(studentNames.get(i), examId);
		}

		return answer / studentNames.size();

	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		File file = new File(fileName);

		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Manager restoreManager(String fileName) {
		File file = new File(fileName);

		try {
			if (!file.exists()) {
				return new SystemManager();
			} else {

				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(file));
				Manager phonebook = (SystemManager) input.readObject();
				input.close();
				return phonebook;

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
