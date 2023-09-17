package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import onlineTest.SystemManager;

/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {

	@Test
	public void pub10MultipleExamsStudents() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		StringBuffer answer = new StringBuffer();
		SystemManager manager = new SystemManager();
		String laura = "Peterson,Laura";
		String mike = "Sanders,Mike";
		String john = "Costas,John";

		/* Adding students */
		manager.addStudent(laura);
		manager.addStudent(mike);
		manager.addStudent(john);

		/* First Exam */
		int examId = 1;
		manager.addExam(examId, "Midterm #1");

		manager.addTrueFalseQuestion(examId, 1,
				"Java methods are examples of procedural abstractions.", 2,
				true);

		manager.addTrueFalseQuestion(examId, 2,
				"An inner class can only access public variables and methods of the enclosing class.",
				2, false);

		String questionText = "Which of the following allow us to define an abstract class?\n";
		questionText += "A: abstract   B: equals   C: class   D: final ";
		double points = 4;
		manager.addMultipleChoiceQuestion(examId, 3, questionText, points,
				new String[] { "A" });

		questionText = "Name three access specifiers";
		points = 6;
		manager.addFillInTheBlanksQuestion(examId, 4, questionText, points,
				new String[] { "public", "private", "protected" });

		/* Answers */
		examId = 1;
		manager.answerTrueFalseQuestion(laura, examId, 1, true);
		manager.answerTrueFalseQuestion(laura, examId, 2, true);
		manager.answerMultipleChoiceQuestion(laura, examId, 3,
				new String[] { "A" });
		manager.answerFillInTheBlanksQuestion(laura, examId, 4,
				new String[] { "private", "public", "protected" });

		manager.answerTrueFalseQuestion(mike, examId, 1, true);
		manager.answerTrueFalseQuestion(mike, examId, 2, false);
		manager.answerMultipleChoiceQuestion(mike, examId, 3,
				new String[] { "A" });
		manager.answerFillInTheBlanksQuestion(mike, examId, 4,
				new String[] { "private" });

		manager.answerTrueFalseQuestion(john, examId, 1, true);
		manager.answerTrueFalseQuestion(john, examId, 2, false);
		manager.answerMultipleChoiceQuestion(john, examId, 3,
				new String[] { "A", "B", "C" });
		manager.answerFillInTheBlanksQuestion(john, examId, 4,
				new String[] { "private", "while" });

		/* Second Exam */
		examId = 2;
		manager.addExam(examId, "Midterm #2");
		manager.addTrueFalseQuestion(examId, 1,
				"The Comparable interface specifies a method called compareTo",
				2, true);
		manager.addTrueFalseQuestion(examId, 2,
				"The Comparator interface specifies a method called compare", 2,
				true);
		manager.addTrueFalseQuestion(examId, 3,
				"A static initialization block is executed when each object is created",
				4, false);

		questionText = "Which of the following allow us to access a super class method?\n";
		questionText += "A: abstract   B: equals   C: super   D: final ";
		points = 8;
		manager.addMultipleChoiceQuestion(examId, 4, questionText, points,
				new String[] { "C" });

		questionText = "Which of the following are methods of the Object class?\n";
		questionText += "A: hashCode   B: equals   C: super   D: final ";
		points = 6;
		manager.addMultipleChoiceQuestion(examId, 5, questionText, points,
				new String[] { "A", "B" });

		/* Answers */
		examId = 2;
		manager.answerTrueFalseQuestion(laura, examId, 1, true);
		manager.answerTrueFalseQuestion(laura, examId, 2, true);
		manager.answerTrueFalseQuestion(laura, examId, 3, true);
		manager.answerMultipleChoiceQuestion(laura, examId, 4,
				new String[] { "C" });
		manager.answerMultipleChoiceQuestion(laura, examId, 5,
				new String[] { "A", "C" });

		manager.answerTrueFalseQuestion(mike, examId, 1, true);
		manager.answerTrueFalseQuestion(mike, examId, 2, true);
		manager.answerTrueFalseQuestion(mike, examId, 3, true);
		manager.answerMultipleChoiceQuestion(mike, examId, 4,
				new String[] { "C" });
		manager.answerMultipleChoiceQuestion(mike, examId, 5,
				new String[] { "A", "B" });

		manager.answerTrueFalseQuestion(john, examId, 1, false);
		manager.answerTrueFalseQuestion(john, examId, 2, true);
		manager.answerTrueFalseQuestion(john, examId, 3, false);
		manager.answerMultipleChoiceQuestion(john, examId, 4,
				new String[] { "C" });
		manager.answerMultipleChoiceQuestion(john, examId, 5,
				new String[] { "A", "B" });

		/* Third Exam */
		examId = 3;
		manager.addExam(examId, "Midterm #3");
		manager.addTrueFalseQuestion(examId, 1,
				"There are two types of exceptions: checked and unchecked.", 4,
				true);
		manager.addTrueFalseQuestion(examId, 2,
				"The traveling salesman problem is an example of an NP problem.",
				4, true);
		manager.addTrueFalseQuestion(examId, 3,
				"Array indexing takes O(n) time", 4, false);

		questionText = "Name two properties of a good hash function.";
		points = 6;
		manager.addFillInTheBlanksQuestion(examId, 4, questionText, points,
				new String[] { "not expensive", "distributes values well" });

		/* Answers */
		examId = 3;
		manager.answerTrueFalseQuestion(laura, examId, 1, true);
		manager.answerTrueFalseQuestion(laura, examId, 2, true);
		manager.answerTrueFalseQuestion(laura, examId, 3, false);
		manager.answerFillInTheBlanksQuestion(laura, examId, 4,
				new String[] { "not expensive", "distributes values well" });

		manager.answerTrueFalseQuestion(mike, examId, 1, false);
		manager.answerTrueFalseQuestion(mike, examId, 2, true);
		manager.answerTrueFalseQuestion(mike, examId, 3, false);
		manager.answerFillInTheBlanksQuestion(mike, examId, 4,
				new String[] { "polynomial", "distributes values well" });

		manager.answerTrueFalseQuestion(john, examId, 1, false);
		manager.answerTrueFalseQuestion(john, examId, 2, false);
		manager.answerTrueFalseQuestion(john, examId, 3, false);
		manager.answerFillInTheBlanksQuestion(john, examId, 4,
				new String[] { "distributes values well" });

		ArrayList<String> list = new ArrayList<String>();
		list.add(laura);
		list.add(mike);
		list.add(john);
		for (examId = 1; examId <= 3; examId++) {
			for (String student : list) {
				answer.append("Report for " + student + " Exam # " + examId
						+ "\n" + manager.getGradingReport(student, examId)
						+ "\n\n");
			}
		}

		for (examId = 1; examId <= 3; examId++) {
			answer.append("Minimum for Exam # " + examId + " "
					+ manager.getMinScore(examId) + "\n");
			answer.append("Maximum for Exam # " + examId + " "
					+ manager.getMaxScore(examId) + "\n");
			answer.append("Average for Exam # " + examId + " "
					+ (int) manager.getAverageScore(examId) + "\n");
		}

		manager.setLetterGradesCutoffs(
				new String[] { "A+", "A", "B+", "B", "C", "D", "F" },
				new double[] { 95, 90, 85, 80, 70, 60, 0 });

		for (String student : list)
			answer.append("Letter Grade for " + student + " "
					+ manager.getCourseLetterGrade(student) + "\n");

		System.out.println(answer);
	}

}
