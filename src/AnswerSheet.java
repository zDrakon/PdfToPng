import java.util.ArrayList;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	private String[][] answers;

	public AnswerSheet(int numAnswersPerPage, int numPages) {
		answers = new String[numAnswersPerPage][numPages];
	}

	public void addAnswer(String answer, int r, int c) {
		answers[r][c] = answer;
	}

	public String getAnswer(int r, int c) {
		return answers[r][c];
	}

	public String[] getAnswerRow(int r) {
		String[] arr = new String[answers[0].length];
		for (int c = 0; c < answers[0].length; c++) {
			arr[c] = answers[r][c];
		}
		return arr;
	}

	public String[] getAnswerCol(int c) {
		String[] arr = new String[answers.length];
		for (int r = 0; r < answers.length; r++) {
			arr[r] = answers[r][c];
		}
		return arr;
	}

	public void exportCSV() {
		String csv = "";
		for (int r = 0; r < answers.length; r++) {
			for (int c = 0; c < answers[0].length; c++) {
				csv += getAnswer(r, c) + ",";
				csv += "\n";
				FileIO.writeDataToFile("/data.csv", csv);
			}
		}

	}

	public int[] compare(AnswerSheet a) {

		int[] question = new int[answers[0].length];
		for (int r = 0; r < answers.length; r++) {
			for (int c = 0; c < answers[0].length; c++) {
				if (!this.answers[r][c].equals(a.answers[r][c])) {
					question[c]++;
				}
			}
		}
		return question;
	}
}
